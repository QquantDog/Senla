package com.senla.ctx;

import com.senla.PostProcessModule;
import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.annotations.PostConstruct;
import com.senla.annotations.Value;
import com.senla.exceptions.AmbiguityComponentException;
import com.senla.exceptions.AmbiguityInterfaceException;
import com.senla.exceptions.AutowireFailureException;
import com.senla.postprocessor.ComponentPostProcessor;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.*;

import static org.reflections.scanners.Scanners.*;

public class ApplicationContext {

    private final Map<Class<?>, Object> implToComponentMap = new HashMap<>();

    private final Map<Class<?>, Class<?>> interfToImplMap = new HashMap<>();
    private final Set<Class<?>> isTryingToCycleAutowire = new HashSet<>();
    private final Properties props = new Properties();

//    private final List<ComponentPostProcessor> postProcessorChain = new ArrayList<>();
    private final PostProcessModule postProcessModule;

    private final String packageToScan;
    private final Reflections reflections;

    public ApplicationContext(String packageToScan){
        this.packageToScan = packageToScan;
        this.reflections = new Reflections(packageToScan);
        this.postProcessModule = new PostProcessModule(this, packageToScan);
    }

//    autowired и value - в постпроцессоры
//    логика процессоры
//    void postProcess(bean, Class bean)

//    void postProcess(List<Object> beanList)
//     List(autowire, value)

    public void init(){
        loadProps();
        postProcessModule.scanDefault();
        postProcessModule.scanPackage(packageToScan);
//        postProcessModule.showList();
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

//        scanPostProcessors();

        set.forEach(this::preInstantation);
        set.forEach(this::instantiateComponent);
        postProcessModule.postProcessBefore(set);
        set.forEach(this::postConstruct);
        postProcessModule.postProcessAfter(set);
        debugComponentsMap();
//
//        POstPRocessors - here
//        исключчаем только автовайред конструкторы
//
//        Set<Class<?>>

//        set.forEach(this::injectDependencyInComponent);
//        set.forEach(this::postProcessBefore);
//        set.forEach(this::postConstruct);
//        set.forEach(this::postProcessAfter);
    }
//    private void scanPostProcessors(){
//        Set<Class<?>> set = reflections.get(SubTypes.of(ComponentPostProcessor.class).asClass());
//        set.forEach(this::postProcessRegistration);
//    }
//
//    private void postProcessRegistration(Class<?> cmpClass){
//        Class<?>[] interface_arr = cmpClass.getInterfaces();
//        if (interface_arr.length == 1){
//            Class<?> interfaceClass = interface_arr[0];
//            if(interfaceClass.equals(ComponentPostProcessor.class)){
//                try{
//                    postProcessorChain.add((ComponentPostProcessor) cmpClass.getDeclaredConstructor().newInstance());
//                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
//                         IllegalAccessException e){
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }

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

//    private void injectDependencyInComponent(Class<?> cmpClass){
//
//    }

//    private void postProcessBefore(Class<?> cmpClass){
//        Object cmp = implToComponentMap.get(cmpClass);
//        postProcessorChain.forEach(postProcessor -> {
//            try{
//                postProcessor.postProcessBeforeInitialization(cmp, cmpClass);
//            } catch (Throwable e){
//                throw new RuntimeException(e);
//            }
//        });
//    }
    private void postConstruct(Class<?> cmpClass){
        Object cmp = implToComponentMap.get(cmpClass);
        Method[] methods = cmpClass.getDeclaredMethods();
        int postConstructCounter = 0;
        for(Method method: methods){
            if(method.isAnnotationPresent(PostConstruct.class)) postConstructCounter++;
        }
        if(postConstructCounter > 1) throw new RuntimeException("More than 1 postconstruct");

        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(PostConstruct.class)).forEach(method->{
            try {
                method.setAccessible(true);
                method.invoke(cmp, (new ArrayList<Object>()).toArray());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

//    private void postProcessAfter(Class<?> cmpClass){
//        Object cmp = implToComponentMap.get(cmpClass);
//        postProcessorChain.forEach(postProcessor -> {
//            try{
//                postProcessor.postProcessAfterInitialization(cmp, cmpClass);
//            } catch (Throwable e){
//                throw new RuntimeException(e);
//            }
//        });
//    }

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

    public Class<?> getImplementation(Class<?> dependencyType){
        Class<?> impl = dependencyType;
        if(dependencyType.isInterface()){
            impl = interfToImplMap.get(dependencyType);
            if(impl == null) throw new RuntimeException("No implementation");
        }
        return impl;
    }
    public String getPropertyByKey(String key){
        return props.getProperty(key);
    }
//    public void setImplementation()
}
