package com.senla.food.normal;

import com.senla.annotations.Component;
import com.senla.postprocessor.ComponentPostProcessor;

@Component
public class MaxPostProcessor implements ComponentPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object component, Class<?> cmpImpl) {
        System.out.println("Before 11 " + component.toString() + " " + cmpImpl.getSimpleName() );
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object component, Class<?> cmpImpl) {
        System.out.println("After 11 " + component.toString() + " " + cmpImpl.getSimpleName() );
        return null;
    }
}
