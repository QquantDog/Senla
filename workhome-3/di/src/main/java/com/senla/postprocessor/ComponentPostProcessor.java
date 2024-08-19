package com.senla.postprocessor;

public interface ComponentPostProcessor {
    public Object postProcessBeforeInitialization(Object component, Class<?> cmpImpl);
    public Object postProcessAfterInitialization(Object component, Class<?> cmpImpl);
}
