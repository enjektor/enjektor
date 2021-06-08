package com.github.enjektor.core.annotations;

import com.github.enjektor.core.qualifier.UnsetQualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
    Class<?> qualifier() default UnsetQualifier.class;
    String value() default "";
}
