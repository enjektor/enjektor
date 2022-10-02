package com.github.enjektor.context;

import com.github.enjektor.context.consumer.BeanInstantiateBiConsumer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.context.handler.DeAllocationHandler;
import com.github.enjektor.context.injection.QualifierInjectionManager;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.core.util.NamingUtils;
import org.reflections.Reflections;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class PrimitiveApplicationContext implements ApplicationContext, DeAllocationHandler {

    private final static byte REQUIRED_COMPONENTS_ZERO_INDEX_REUSABLE_REFLECTIONS_OBJECT = (byte) 0x0;

    private Map<Class<?>, Bean> beans = new WeakHashMap<>();
    private ApplicationContext applicationContext;
    private Object[] requiredComponents;

    public PrimitiveApplicationContext(final Class<?> mainClass,
                                       final List<DependencyInitializer> dependencyInitializers,
                                       final List<Pair> pairs,
                                       final Object[] requiredComponents) {
        this.applicationContext = new DefaultApplicationContext(mainClass, beans, dependencyInitializers, requiredComponents);
        this.requiredComponents = requiredComponents;
        init(pairs);
    }

    @Override
    public void init(final List<Pair> pairs) {
        for (final Pair pair : pairs) beans.put(pair.getType(), pair.getBean());

        final Reflections reflections = (Reflections) requiredComponents[REQUIRED_COMPONENTS_ZERO_INDEX_REUSABLE_REFLECTIONS_OBJECT];
        final QualifierInjectionManager qualifierInjectionManager = new QualifierInjectionManager(applicationContext, reflections);
        final BiConsumer<Class<?>, Bean> beanBiConsumer = new BeanInstantiateBiConsumer(qualifierInjectionManager);
        beans.forEach(beanBiConsumer);
    }

    @Override
    public void destroy() {
        clean();
    }

    @Override
    public final <T> T getBean(final Class<T> classType) {
        final String beanName = NamingUtils.beanCase(classType.getSimpleName());
        return getBean(classType, beanName);
    }

    @Override
    public final <T> T getBean(final Class<T> classType,
                               final String fieldName) {
        final Bean bean = beans.get(classType);
        final Object existObject = bean.getDependency(fieldName);
        return (T) existObject;
    }

    @Override
    public <T> Bean getNativeBean(Class<T> classType) {
        return beans.get(classType);
    }

    @Override
    public void clean() {
        applicationContext.destroy();
        applicationContext = null;
        beans.forEach((k, ignored) -> k = null);
        beans = null;
    }
}
