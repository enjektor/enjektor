package com.github.enjektor.context.dependency.collector.strategy;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.utils.NamingUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class PublicMethodCollectorStrategy implements CollectorStrategy {

    @FunctionalInterface
    interface BeanBehaviour {
        void act(Bean bean, String beanName, Object beanInstance, Map<Class<?>, Bean> context, Class<?> type);
    }

    private final BeanBehaviour[] beanBehaviours = new BeanBehaviour[0x2];

    public PublicMethodCollectorStrategy() {
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
    public void act(Method method, Map<Class<?>, Bean> context, Object dependenciesInstance) {
        try {
            final Class<?> methodReturnType = method.getReturnType();
            final Object beanInstance = method.invoke(dependenciesInstance, null);
            final Class<?> returnTypeOfBean = beanInstance.getClass();
            final boolean isInterfaceBean = methodReturnType.isInterface();

            final Class<?> type = isInterfaceBean ? methodReturnType : returnTypeOfBean;
            final String beanName = isInterfaceBean ? NamingUtils.beanCase(returnTypeOfBean.getSimpleName()) : method.getName();

            final Bean bean = context.get(type);
            final int isBeanNull = bean != null ? 1 : 0;

            if (isInterfaceBean) beanBehaviours[isBeanNull].act(bean, beanName, beanInstance, context, methodReturnType);
            else beanBehaviours[isBeanNull].act(bean, beanName, beanInstance, context, returnTypeOfBean);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
