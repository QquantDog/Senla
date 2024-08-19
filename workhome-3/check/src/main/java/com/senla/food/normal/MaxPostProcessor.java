package com.senla.food.normal;

import com.senla.annotations.Component;
import com.senla.postprocessor.ComponentPostProcessor;

public class MaxPostProcessor implements ComponentPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object component, Class<?> cmpImpl) {
        System.out.println("Before init " + component.toString() + " " + cmpImpl.getSimpleName() );
        return component;
    }

    @Override
    public Object postProcessAfterInitialization(Object component, Class<?> cmpImpl) {
        System.out.println("After init " + component.toString() + " " + cmpImpl.getSimpleName() );
        return component;
    }
}
