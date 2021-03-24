package edu.hawking.framework;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 杜皓君 created by 2021/3/22
 * HawkingApplicationContext
 **/
public class HawkingApplicationContext {
    Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    private Map<String, Object> singletonObjects = new HashMap<String, Object>();

    public HawkingApplicationContext(Class clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.configClass = clazz;
        scanBeans(clazz);
        // 扫描单例
        createSingletonObjects();
    }

    private void createSingletonObjects(){

        for (String s : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(s);
            if (beanDefinition.getScope().equals("singleton")) {
                if (!beanDefinition.isLazy()){
                    Object object = createBean(beanDefinition);
                    singletonObjects.put(s, object);
                }
            }

        }
    }

    private Object createBean(BeanDefinition beanDefinition) {

        Class beanClass = beanDefinition.getBeanClass();
        Object instance = null;
        try {
            instance = beanClass.getConstructor().newInstance();
            for (Field field : beanClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object fieldObj = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance, fieldObj);
                }
            }
            //bean生命周期
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void scanBeans(Class configClass) throws ClassNotFoundException {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScan.value();
            path = path.replace(".","/");
            URL resource = configClass.getClassLoader().getResource(path);
            System.out.println(resource.toString());
            File file = new File(resource.getFile());
            for (File f : file.listFiles()) {
                String fPath = f.getAbsolutePath();
                if (fPath.endsWith(".class")) {
                    String classFullName = fPath.substring(fPath.indexOf("edu"), f.getAbsolutePath().lastIndexOf(".class"));
                    classFullName = classFullName.replace("\\", ".");
                    Class clz =configClass.getClassLoader().loadClass(classFullName);
                    if (clz.isAnnotationPresent(Component.class)) {
                        Component componentAnnot = (Component) clz.getAnnotation(Component.class);
                        String beanName = componentAnnot.value();
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setBeanClass(clz);
                        if (clz.isAnnotationPresent(Lazy.class)) {
                            Lazy lazyAnnot = (Lazy) clz.getAnnotation(Lazy.class);
                            beanDefinition.setLazy(lazyAnnot.value());
                        }else {
                            beanDefinition.setLazy(false);
                        }
                        if (clz.isAnnotationPresent(Scope.class)) {
                            Scope scopeAnnot = (Scope) clz.getAnnotation(Scope.class);
                            beanDefinition.setScope(scopeAnnot.value());
                        } else {
                            beanDefinition.setScope("singleton");
                        }
                        beanDefinitionMap.put(beanName,beanDefinition);
                    }
                }
            }
        }
    }

    public Object getBean(String name) {
        if (!beanDefinitionMap.containsKey(name)) {
            throw new NullPointerException();
        }
        Object obj = null;
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition.getScope().equals("singleton")) {
            // 单例池
            obj = singletonObjects.get(name);
        } else if (beanDefinition.getScope().equals("prototype")) {
            // 原型模式
            obj = createBean(beanDefinition);
        }

        return obj;
    }
}
