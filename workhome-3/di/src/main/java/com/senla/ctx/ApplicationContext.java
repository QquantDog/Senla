package com.senla.ctx;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.annotations.PostConstruct;
import com.senla.annotations.Value;
import com.senla.exceptions.AmbiguityComponentException;
import com.senla.exceptions.AmbiguityInterfaceException;
import com.senla.exceptions.AutowireFailureException;
import com.senla.postprocessor.ComponentPostProcessor;
//import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.*;

import static org.reflections.scanners.Scanners.*;

public class ApplicationContext {

    private final Map<Class<?>, Object> implToComponentMap = new HashMap<>();

    private final Map<Class<?>, Class<?>> interfToImplMap = new HashMap<>();
    private final Set<Class<?>> isTryingToCycleAutowire = new HashSet<>();
    private final Properties props = new Properties();

    private final List<Object> postProcessorChain = new ArrayList<>();

    private final Reflections reflections;

    public ApplicationContext(String packageToScan){
        this.reflections = new Reflections(packageToScan);
    }

    public void init(){
        loadProps();
        initComponentsMap();
    }

    private void loadProps(){
        try(var stream = this.getClass().getClassLoader().getResourceAsStream("app.props")){
            props.load(stream);
        } catch (Throwable e){
            System.out.println("Property file app.props doesn't exist!!");
        }
    }

    private void initComponentsMap() {
        Set<Class<?>> set = reflections.get(SubTypes.of(TypesAnnotated.with(Component.class)).asClass());

        scanPostProcessors();

        set.forEach(this::preInstantation);
        set.forEach(this::instantiateComponent);
        set.forEach(this::injectDependencyInComponent);
        set.forEach(this::postProcessBefore);
        set.forEach(this::postConstruct);
        set.forEach(this::postProcessAfter);
        debugComponentsMap();
    }
    private void scanPostProcessors(){
        Set<Class<?>> set = reflections.get(SubTypes.of(ComponentPostProcessor.class).asClass());
        set.forEach(this::postProcessRegistration);
    }

    private void postProcessRegistration(Class<?> cmpClass){
        Class<?>[] interface_arr = cmpClass.getInterfaces();
        if (interface_arr.length == 1){
            Class<?> interfaceClass = interface_arr[0];
            if(interfaceClass.equals(ComponentPostProcessor.class)){
                try{
                    postProcessorChain.add(cmpClass.getDeclaredConstructor().newInstance());
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void preInstantation(Class<?> cmpClass){
        Class<?>[] interface_arr = cmpClass.getInterfaces();
        if (interface_arr.length > 1) throw new RuntimeException("More than 1 interface");
        else if (interface_arr.length == 1) {
            Class<?> interfaceClass = interface_arr[0];
            if(interfToImplMap.get(interfaceClass) != null) throw new AmbiguityInterfaceException("More than one component per interface");
            interfToImplMap.put(interfaceClass, cmpClass);
        }
    }

    private Object instantiateComponent(Class<?> cmpClass){
        if(!cmpClass.isAnnotationPresent(Component.class)) throw new RuntimeException("Trying to instantiate NOT a component");
        if(implToComponentMap.get(cmpClass) != null) return implToComponentMap.get(cmpClass);
        try {
            Constructor<?>[] constructors = cmpClass.getDeclaredConstructors();

            int autowireConstructorsCounter = 0;
            Constructor<?> lastAnnotatedConstructor = null;

            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Autowire.class)) {
                    lastAnnotatedConstructor = constructor;
                    autowireConstructorsCounter++;
                }
            }
            if (autowireConstructorsCounter > 1) throw new RuntimeException("More than 1 autowire constructors");
            else if (autowireConstructorsCounter == 1) {

                if(isTryingToCycleAutowire.contains(cmpClass)) throw new AutowireFailureException("Constructor autowire cycle found - change dependency tree");
                isTryingToCycleAutowire.add(cmpClass);

                lastAnnotatedConstructor.setAccessible(true);
                Parameter[] parameter_arr = lastAnnotatedConstructor.getParameters();
                List<Object> comp_arr = new ArrayList<>();
                for (Parameter param : parameter_arr) {
                    Class<?> dependencyType = param.getType();

                    Class<?> impl = dependencyType;
                    if(dependencyType.isInterface()){
                        impl = interfToImplMap.get(dependencyType);
                    }
                    if(implToComponentMap.get(impl) == null){
                        Object paramRealisation = instantiateComponent(impl);
                        comp_arr.add(paramRealisation);
                    }
                    else{
                        comp_arr.add(implToComponentMap.get(impl));
                    }
                }
                Object[] parr = comp_arr.toArray(new Object[0]);
                Object cmp = lastAnnotatedConstructor.newInstance(parr);
                if(implToComponentMap.get(cmpClass) != null) throw new AmbiguityComponentException("More than 1 component implementation");
                implToComponentMap.put(cmpClass, cmp);
                return cmp;
            }
            else{
                Constructor<?> defaultConstructor = cmpClass.getDeclaredConstructor();
                defaultConstructor.setAccessible(true);
                Object cmp = defaultConstructor.newInstance();
                if(implToComponentMap.get(cmpClass) != null) throw new AmbiguityComponentException("More than 1 component implementation");
                implToComponentMap.put(cmpClass, cmp);
                return cmp;
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void injectDependencyInComponent(Class<?> cmpClass){
        Field[] fields = cmpClass.getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Autowire.class)).forEach(field -> {
            field.setAccessible(true);

            Class<?> dependencyType = field.getType();
            Class<?> impl = dependencyType;
            if(dependencyType.isInterface()){
                impl = interfToImplMap.get(dependencyType);
                if(impl == null) throw new RuntimeException("No implementation");
            }
            Object cmp = implToComponentMap.get(impl);
            if(cmp == null) throw new RuntimeException("Somehow component not instantiated");
            try{
                field.set(implToComponentMap.get(cmpClass), cmp);
            } catch (Throwable e){
                throw new RuntimeException(e);
            }
        });
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Value.class)).forEach(field -> {
            String prop_value = props.getProperty(field.getAnnotation(Value.class).value());
            if(prop_value == null) throw new RuntimeException("Value couldn't be found in props file");
            field.setAccessible(true);

            try{
                field.set(implToComponentMap.get(cmpClass), prop_value);
            }catch (Throwable e){
                throw new RuntimeException("Couldn't set field");
            }
        });
        Method[] methods = cmpClass.getDeclaredMethods();
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Autowire.class)).forEach(method -> {
            method.setAccessible(true);

            Parameter[] parameters = method.getParameters();
            List<Object> passedParametersImpl = new ArrayList<>();
            for (Parameter parameter : parameters) {

                Class<?> dependencyType = parameter.getType();
                Class<?> impl = dependencyType;
                if(dependencyType.isInterface()){
                    impl = interfToImplMap.get(dependencyType);
                }
                Object cmp = implToComponentMap.get(impl);
                if(cmp == null) throw new RuntimeException("No component implementation");
                passedParametersImpl.add(cmp);
            }
            try {
                Object[] arr = passedParametersImpl.toArray();
                Object cmp = implToComponentMap.get(cmpClass);
                method.invoke(cmp, arr);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void postProcessBefore(Class<?> cmpClass){
        Object cmp = implToComponentMap.get(cmpClass);
        postProcessorChain.forEach(postProcessor -> {
            try{
                Method beforeInitMethod = postProcessor.getClass().getMethod("postProcessBeforeInitialization", Object.class, Class.class);
                Object[] arr = new Object[]{cmp, cmpClass};
                beforeInitMethod.invoke(postProcessor, Arrays.stream(arr).toArray());
            } catch (Throwable e){
                throw new RuntimeException(e);
            }
        });
    }
    private void postConstruct(Class<?> cmpClass){
        Object cmp = implToComponentMap.get(cmpClass);
        Method[] methods = cmpClass.getDeclaredMethods();
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(PostConstruct.class)).forEach(method->{
            try {
                method.setAccessible(true);
                method.invoke(cmp, (new ArrayList<Object>()).toArray());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void postProcessAfter(Class<?> cmpClass){
        Object cmp = implToComponentMap.get(cmpClass);
        postProcessorChain.forEach(postProcessor -> {
            try{
                Method afterInitMethod = postProcessor.getClass().getMethod("postProcessAfterInitialization", Object.class, Class.class);
                Object[] arr = new Object[]{cmp, cmpClass};
                afterInitMethod.invoke(postProcessor, Arrays.stream(arr).toArray());
            } catch (Throwable e){
                throw new RuntimeException(e);
            }
        });
    }

    public void debugComponentsMap(){
        System.out.println("Start of component list: ");
        implToComponentMap.forEach((name, obj) -> {
            System.out.println("    component_class: " + name);
//            System.out.println("obj class: " + obj);
            System.out.println("    ------------------");
        });
        System.out.println("End of component list");
    }

    private void debugSet(Set<Class<?>> s){
        System.out.println("debug set:");
        s.forEach(v -> {
            System.out.println(v.getSimpleName());
        });
    }

    public Object getComponent(Class<?> cmpClass){
        return implToComponentMap.get(cmpClass);
    }

}
