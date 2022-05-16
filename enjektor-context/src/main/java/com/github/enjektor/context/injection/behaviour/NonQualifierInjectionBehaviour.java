package com.github.enjektor.context.injection.behaviour;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.injection.DefaultInjectionStrategy;
import com.github.enjektor.context.injection.InjectionStrategy;
import com.github.enjektor.context.injection.ListInjectionStrategy;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.core.bean.Bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class NonQualifierInjectionBehaviour implements InjectionBehaviour {

    private final Map<Predicate<String>, InjectionStrategy> map = new HashMap<>();
    private final Set<Map.Entry<Predicate<String>, InjectionStrategy>> set;

    {
        init();
    }

    public NonQualifierInjectionBehaviour() {
        this.set = map.entrySet();
    }

    public void init() {
        Predicate<String> listPredicate = canonical -> canonical.startsWith("java.util.List");
        Predicate<String> defaultPredicate = canonical -> !canonical.startsWith("java.util.List");

        map.put(listPredicate, new ListInjectionStrategy());
        map.put(defaultPredicate, new DefaultInjectionStrategy());
    }


    @Override
    public void act(Object object,
                    Field field,
                    ApplicationContext applicationContext,
                    Map<Class<?>, Bean> beans) throws IllegalAccessException, InstantiationException {
        final Inject inject = field.getAnnotation(Inject.class);
        final String value = inject.value();

        final String canonicalName = field.getType().getCanonicalName();
        if (value.isEmpty()) {
            final Set<Map.Entry<Predicate<String>, InjectionStrategy>> entries = map.entrySet();
            for (Map.Entry<Predicate<String>, InjectionStrategy> entry : entries) {
                final Predicate<String> key = entry.getKey();
                final InjectionStrategy val = entry.getValue();

                final boolean test = key.test(canonicalName);
                if (test) val.inject(object, field, null, applicationContext, beans);
            }
        } else {
            final Object bean = Optional.ofNullable(applicationContext.getBean(field.getType(), value))
                .orElseThrow(() -> new IllegalArgumentException("There is no such property ${" + value + "} in your configuration file."));
            field.set(object, bean);
        }
    }
}
