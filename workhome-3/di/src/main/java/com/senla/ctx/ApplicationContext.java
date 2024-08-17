package com.senla.ctx;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.annotations.Value;
import com.senla.exceptions.AmbiguityComponentException;
import com.senla.exceptions.AmbiguityInterfaceException;
import com.senla.exceptions.AutowireFailureException;
import com.senla.postprocessor.ComponentPostProcessor;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.reflections.scanners.Scanners.*;

public class ApplicationContext {
//  связь строго 1 - к - 1 иначе
//  throw new AmbiguityComponentNameException в процессе сканирования
    private final Map<Class<?>, Object> implToComponentMap = new ConcurrentHashMap<>();

//    Class<?> (интерфейсная переменная) -> Class<?> (класс имплементации) -> Оbject (сам компонент)
//    Impl - конечный класс имплементации
    private final Map<Class<?>, Class<?>> interfToImplMap = new ConcurrentHashMap<>();
    private final Set<Class<?>> isTryingToCycleAutowire = new HashSet<>();
    private final Properties props = new Properties();

//    private final List<Method> beforeInit = new ArrayList<>();
//    private final List<Method> afterInit = new ArrayList<>();
    private final List<Object> chain = new ArrayList<>();
    private final Set<Class<?>> componentPostProcessorsClasses = new HashSet<>();

    private String packageToScan;
    private Reflections reflections;

    public ApplicationContext(String packageToScan){
        this.packageToScan = packageToScan;
        this.reflections = new Reflections(packageToScan);
    }

    public void init(){
        loadProps();
        initComponentsMap();
    }

    @SneakyThrows
    private void loadProps(){
        try(var stream = this.getClass().getClassLoader().getResourceAsStream("app.props")){
            props.load(stream);
        } catch (Throwable e){
            System.out.println("Property file app.props doesn't exist!!");
        }
    }

    @SneakyThrows
    private void initComponentsMap() {
        Set<Class<?>> set = reflections.get(SubTypes.of(TypesAnnotated.with(Component.class)).asClass());
        set.forEach(this::postProcessRegistration);
        set.removeAll(componentPostProcessorsClasses);

        set.forEach(this::preInstantation);
        set.forEach(this::instantiateComponent);
        set.forEach(this::initializeComponent);
        set.forEach(this::postProcessBefore);
        set.forEach(this::postProcessAfter);
        debugComponentsMap();
    }

    @SneakyThrows
    private void postProcessRegistration(Class<?> cmpClass){
        Class<?>[] interface_arr = cmpClass.getInterfaces();
        if (interface_arr.length == 1){
            Class<?> interfaceClass = interface_arr[0];
            if(interfaceClass.equals(ComponentPostProcessor.class)){
                try{
                    chain.add(cmpClass.getDeclaredConstructor().newInstance());
                    componentPostProcessorsClasses.add(cmpClass);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SneakyThrows
    private void postProcessBefore(Class<?> cmpClass){
        Object cmp = implToComponentMap.get(cmpClass);
        for(Object postProcessor: chain){
            Method beforeInitMethod = postProcessor.getClass().getMethod("postProcessBeforeInitialization", Object.class, Class.class);
            Object[] arr = new Object[]{cmp, cmpClass};
            beforeInitMethod.invoke(postProcessor, Arrays.stream(arr).toArray());
        }
    }
    @SneakyThrows
    private void postProcessAfter(Class<?> cmpClass){
        Object cmp = implToComponentMap.get(cmpClass);
        for(Object postProcessor: chain){
            Method afterInitMethod = postProcessor.getClass().getMethod("postProcessAfterInitialization", Object.class, Class.class);
            Object[] arr = new Object[]{cmp, cmpClass};
            afterInitMethod.invoke(postProcessor, Arrays.stream(arr).toArray());
        }
    }


    private void preInstantation(Class<?> cmpClass){
        Class<?>[] interface_arr = cmpClass.getInterfaces();
        if (interface_arr.length > 1) throw new RuntimeException("More than 1 interface");
        else if (interface_arr.length == 1) {
            Class<?> interfaceClass = interface_arr[0];
            if(interfToImplMap.get(interface_arr[0]) != null) throw new AmbiguityInterfaceException("More than one component per interface");
            interfToImplMap.put(interface_arr[0], cmpClass);
        }
    }
    private Object instantiateComponent(Class<?> cmpClass){
        if(!cmpClass.isAnnotationPresent(Component.class)) throw new RuntimeException("Trying to instantiate NOT a component");
        if(implToComponentMap.get(cmpClass) != null) return implToComponentMap.get(cmpClass);
        try {
            Constructor<?>[] ctor_arr = cmpClass.getDeclaredConstructors();

            int autowireConstructorsCounter = 0;
            Constructor<?> lastAnnotatedConstructor = null;

            for (Constructor<?> ctor : ctor_arr) {
                if (ctor.isAnnotationPresent(Autowire.class)) {
                    lastAnnotatedConstructor = ctor;
                    autowireConstructorsCounter++;
                }
            }
            if (autowireConstructorsCounter > 1) throw new RuntimeException("More than 1 autowire ctors");
//
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
//                  если dependency отсутствует, то пытаемся ее инстанцировать
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
//
            else if (autowireConstructorsCounter == 0) {
                Constructor<?> defaultConstructor = cmpClass.getDeclaredConstructor();
                defaultConstructor.setAccessible(true);
                Object cmp = defaultConstructor.newInstance();
                if(implToComponentMap.get(cmpClass) != null) throw new AmbiguityComponentException("More than 1 component implementation");
                implToComponentMap.put(cmpClass, cmp);
                return cmp;
            } else {
                throw new RuntimeException("Unreachable code");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

        //todo
//1 поменять beanName STring на Class
//2 заполнить интерфейсы сразу
//3 PostProcessor - интерфейс PostProcess(Object bean) - дефолтные Autowired, Value - дефолтные постпроцессоры
//4 A implements PostProcessor

//                нахождение всех постпроцессоров
//                инстанцирование бинов(2 мап) + рекурсивное инстанцирование Autowired
//                        A -> B -> C -> A
//                прогнать по цепочке ответсвенности
//                        Value с проперти файла

//                у меня заполнены 2 мапы
//                cmpClass.getInterfaces();
//        debugComponentsMap();
//        instantiateComponentsMap(set);
//    @SneakyThrows
    private void initializeComponent(Class<?> cmpClass){
        Field[] fields = cmpClass.getDeclaredFields();
        for (Field field: fields){
            if(field.isAnnotationPresent(Autowire.class)){
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
            }
//            взаимисключающе или @Autowire или @Value
            else if(field.isAnnotationPresent(Value.class)){
                String prop_value = props.getProperty(field.getAnnotation(Value.class).value());
                if(prop_value == null) throw new RuntimeException("Value couldn't be found in props file");
                field.setAccessible(true);
//                по-хорошему добваить исключения для невозможности каста
//                например из Строки в Инт
                try{
                    field.set(implToComponentMap.get(cmpClass), prop_value);
                }catch (Throwable e){
                    throw new RuntimeException("Couldn't set field");
                }
            }
        }
        Method[] methods = cmpClass.getDeclaredMethods();
        for(Method method: methods) {
            if (method.isAnnotationPresent(Autowire.class)) {
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
            }
        }
    }

    public void debugComponentsMap(){
        implToComponentMap.forEach((name, obj) -> {
            System.out.println("beanName: " + name);
            System.out.println("obj class: " + obj.getClass().getName());
            System.out.println("------------------");
        });
    }

    private void debugSet(Set<Class<?>> s){
        System.out.println("debug set:");
        s.forEach(v -> {
            System.out.println(v.getSimpleName());
        });
    }

    public Object getBean(Class<?> cmpClass){
        return implToComponentMap.get(cmpClass);
    }

}
