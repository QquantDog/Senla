package com.senla.postprocessor.impl;

import com.senla.annotations.Autowire;
import com.senla.annotations.Value;
import com.senla.ctx.ApplicationContext;
import com.senla.postprocessor.ComponentPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutowireDefaultPostProcessor implements ComponentPostProcessor {

    private ApplicationContext ctx;

    private AutowireDefaultPostProcessor(ApplicationContext ctx){
        this.ctx = ctx;
    }

    @Override
    public void postProcessBeforeInitialization(Object component, Class<?> cmpClass) {
        Field[] fields = cmpClass.getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Autowire.class)).forEach(field -> {
            field.setAccessible(true);

            Class<?> dependencyType = field.getType();
            Object toInject = ctx.getComponent(ctx.getImplementation(dependencyType));

            if(toInject == null) throw new RuntimeException("Somehow component not instantiated");
            try{
                field.set(component, toInject);
            } catch (Throwable e){
                throw new RuntimeException(e);
            }
        });

        Method[] methods = cmpClass.getDeclaredMethods();
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Autowire.class)).forEach(method -> {
            method.setAccessible(true);

            Parameter[] parameters = method.getParameters();
            List<Object> passedParametersToInject = new ArrayList<>();

            for (Parameter parameter : parameters) {
                Class<?> dependencyType = parameter.getType();
                Object toInject = ctx.getComponent(ctx.getImplementation(dependencyType));

                if(toInject == null) throw new RuntimeException("Somehow component not instantiated");
                passedParametersToInject.add(toInject);
            }
            try {
                method.invoke(component, passedParametersToInject.toArray());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
