package com.senla.postprocessor;

public interface ComponentPostProcessor {
    public void postProcessBeforeInitialization(Object component, Class<?> cmpImpl);
    public default void postProcessAfterInitialization(Object component, Class<?> cmpImpl){};
}
