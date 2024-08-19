package com.senla;

import com.senla.annotations.Component;
import com.senla.postprocessor.ComponentPostProcessor;

public class CustomPostProcessor implements ComponentPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object component, Class<?> componentImpl) {
        System.out.println("Before init: " + component + " " + componentImpl.getSimpleName() );
        return component;
    }

    @Override
    public Object postProcessAfterInitialization(Object component, Class<?> componentImpl) {
        System.out.println("After init: " + component + " " + componentImpl.getSimpleName());
        return component;
    }
}
