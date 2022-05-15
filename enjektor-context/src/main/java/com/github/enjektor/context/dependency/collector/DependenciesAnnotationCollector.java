package com.github.enjektor.context.dependency.collector;

import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.util.NamingUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

public class DependenciesAnnotationCollector implements Collector {

    /**
     * for (final Class<?> dependency : classesWithDependenciesAnnotation) {
     * final Method[] declaredMethods = dependency.getDeclaredMethods();
     * for (final Method method : declaredMethods) {
     * boolean isPublic = (method.getModifiers() & Modifier.PUBLIC) != 0;
     * if (method.isAnnotationPresent(Dependency.class) && isPublic) {
     * final Class<?> returnType = method.getReturnType();
     * final Map<String, Object> valuesMap = new HashMap<>();
     * valuesMap.put("name", "");
     * RuntimeAnnotations.putAnnotation(returnType, Dependency.class, valuesMap);
     * //                    beans.add(returnType);
     * }
     * }
     * }
     *
     * @param mainClass
     * @return
     */

    private final BeanBehaviour[] beanBehaviours = new BeanBehaviour[0x2];

    @FunctionalInterface
    interface BeanBehaviour {
        void act(Bean bean, String beanName, Object beanInstance, Map<Class<?>, Bean> context, Class<?> type);
    }

    public DependenciesAnnotationCollector() {
        init();
    }

    private void init() {
        final BeanBehaviour beanNotNullBehaviour = (bean, name, instance, ctx, type) -> {
            bean.register(name, instance);
        };

        final BeanBehaviour beanNullBehaviour = (bean, name, instance, ctx, type) -> {
            Bean newBean = new Bean(type);
            newBean.register(name, instance);
            ctx.put(type, newBean);
        };

        beanBehaviours[0x0] = beanNullBehaviour;
        beanBehaviours[0x1] = beanNotNullBehaviour;
    }


    @Override
    public final Set<Class<?>> collect(final Class<?> mainClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void collect(Class<?> mainClass, Map<Class<?>, Bean> beanMap) {
        final Set<Class<?>> classesWithDependenciesAnnotation = CLASS_SCANNER.scan(mainClass, Dependencies.class);

        for (Class<?> dependenciesClass : classesWithDependenciesAnnotation) {
            final Object dependenciesInstance = newDependenciesInstance(dependenciesClass);
            final Method[] declaredMethods = dependenciesClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                boolean isPublic = (method.getModifiers() & Modifier.PUBLIC) != 0;
                if (isPublic) {
                    final Class<?> returnType = method.getReturnType();
                    try {
                        final Object beanInstance = method.invoke(dependenciesInstance, null);
                        final Class<?> returnTypeOfBean = beanInstance.getClass();
                        final boolean isInterfaceBean = returnType.isInterface();

                        final Class<?> type = isInterfaceBean ? returnType : returnTypeOfBean;
                        final String beanName = isInterfaceBean ? NamingUtils.beanCase(returnTypeOfBean.getSimpleName()) : method.getName();

                        final Bean bean = beanMap.get(type);
                        final int isBeanNull = bean != null ? 1 : 0;

                        if (isInterfaceBean) {
                            beanBehaviours[isBeanNull].act(bean, beanName, beanInstance, beanMap, returnType);
                        } else {
                            beanBehaviours[isBeanNull].act(bean, beanName, beanInstance, beanMap, returnTypeOfBean);
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Object newDependenciesInstance(Class<?> classWithDependenciesAnnotation) {
        try {
            return classWithDependenciesAnnotation.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
