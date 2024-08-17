package com.senla;

import com.senla.annotations.Component;
import com.senla.postprocessor.ComponentPostProcessor;

@Component
public class CustomPostProcessor implements ComponentPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object component, Class<?> beanImpl) {
        System.out.println("Before init 1331");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object component, Class<?> beanImpl) {
        System.out.println("After init 2442");
        return null;
    }
}
