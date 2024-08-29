package com.senla;

import com.senla.ctx.ApplicationContext;
import com.senla.postprocessor.ComponentPostProcessor;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class PostProcessModule {
    private final List<ComponentPostProcessor> postProcessorChain = new ArrayList<>();
    private final Reflections defaultScanner = new Reflections("com.senla.postprocessor");
    private final Reflections customScanner;
    private final ApplicationContext ctx;

    public PostProcessModule(ApplicationContext ctx, String packageToScan){
        this.ctx = ctx;
        this.customScanner = new Reflections(packageToScan);
    }

    public void scanDefault(){
        Set<Class<?>> postProcessorsSet = defaultScanner.get(SubTypes.of(ComponentPostProcessor.class).asClass());
        postProcessorsSet.forEach(this::postProcessRegistration);
    }
    public void scanPackage(String packageToScan) {
        Set<Class<?>> postProcessorsSet = customScanner.get(SubTypes.of(ComponentPostProcessor.class).asClass());
        postProcessorsSet.forEach(this::postProcessRegistration);
    }

    private void postProcessRegistration(Class<?> postProcessorClass){
        Class<?>[] interface_arr = postProcessorClass.getInterfaces();
        if (interface_arr.length == 1){
            Class<?> interfaceClass = interface_arr[0];
            if(interfaceClass.equals(ComponentPostProcessor.class)){
                try{
                    ComponentPostProcessor postProcessor;
                    try{
                        Constructor<?> constructor = postProcessorClass.getDeclaredConstructor(ApplicationContext.class);
                        constructor.setAccessible(true);
                        postProcessor = (ComponentPostProcessor) constructor.newInstance(ctx);
                    } catch (NoSuchMethodException e){
                        Constructor<?> constructor = postProcessorClass.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        postProcessor = (ComponentPostProcessor)constructor.newInstance();
                    }
                    postProcessorChain.add(postProcessor);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void postProcessBefore(Set<Class<?>> set){
        set.forEach(this::postProcessComponentBefore);
    }
    public void postProcessAfter(Set<Class<?>> set){
        set.forEach(this::postProcessComponentAfter);
    }

    private void postProcessComponentBefore(Class<?> cmpClass){
        Object component = ctx.getComponent(cmpClass);
        postProcessorChain.forEach(postProcessor -> {
            try{
                postProcessor.postProcessBeforeInitialization(component, cmpClass);
            } catch (Throwable e){
                throw new RuntimeException(e);
            }
        });
    }
    private void postProcessComponentAfter(Class<?> cmpClass){
        Object component = ctx.getComponent(cmpClass);
        postProcessorChain.forEach(postProcessor -> {
            try{
                postProcessor.postProcessAfterInitialization(component, cmpClass);
            } catch (Throwable e){
                throw new RuntimeException(e);
            }
        });
    }

    public void showList(){
        postProcessorChain.forEach(System.out::println);
    }

}
