package com.senla.postprocessor;

public interface ComponentPostProcessor {
    public Object postProcessBeforeInitialization(Object component, Class<?> beanImpl);
    public Object postProcessAfterInitialization(Object component, Class<?> beanImpl);
}
