package com.senla.postprocessor.impl;

import com.senla.annotations.Value;
import com.senla.ctx.ApplicationContext;
import com.senla.postprocessor.ComponentPostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ValueDefaultPostProcessor implements ComponentPostProcessor {

    private ApplicationContext ctx;

    private ValueDefaultPostProcessor(ApplicationContext ctx){
        this.ctx = ctx;
    }

    @Override
    public void postProcessBeforeInitialization(Object component, Class<?> cmpClass) {
        Field[] fields = cmpClass.getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Value.class)).forEach(field -> {
            String propValue = ctx.getPropertyByKey(field.getAnnotation(Value.class).value());
            if(propValue == null) throw new RuntimeException("Value couldn't be found in props file");
            field.setAccessible(true);
            try{
                field.set(component, propValue);
            }catch (Throwable e){
                throw new RuntimeException("Couldn't set field");
            }
        });
    }
}
