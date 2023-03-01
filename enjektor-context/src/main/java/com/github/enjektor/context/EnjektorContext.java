package com.github.enjektor.context;

import com.github.enjektor.context.accumulator.DependencyDepthAccumulator;
import com.github.enjektor.context.accumulator.DependencyDepthAccumulatorImpl;
import com.github.enjektor.context.configuration.EnjektorConfiguration;
import com.github.enjektor.context.initializer.DefaultDependencyInitializer;
import com.github.enjektor.context.pg.SampleDependency;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.reflection.ReflectionScanner;
import com.github.enjektor.core.reflection.scanner.AnnotationReflectionScanner;
import gnu.trove.map.TIntObjectMap;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class EnjektorContext {

    public static void main(String[] args) {
        final Class<EnjektorContext> enjektorContextClass = EnjektorContext.class;

        final ReflectionScanner<Annotation> annotationReflectionScanner = new AnnotationReflectionScanner();
        final Set<Class<?>> classesAnnotatedWithDependencyAnnotation = annotationReflectionScanner.scan(enjektorContextClass, Dependency.class);


        final DependencyDepthAccumulator dependencyDepthAccumulator = new DependencyDepthAccumulatorImpl();
        final TIntObjectMap<List<Class<?>>> levels = dependencyDepthAccumulator.findDepth(classesAnnotatedWithDependencyAnnotation);


//        final EnjektorConfiguration configuration = EnjektorConfiguration.builder()
//            .withMainClass(enjektorContextClass)
//            .build();
//
//        final Enjektor enjektor = Enjektor.builder()
//            .configuration(configuration)
//            .addDependencyInitializer(new DefaultDependencyInitializer())
//            .build();
//
//        SampleDependency dependency = enjektor.getDependency(SampleDependency.class);
//        dependency.anyExecutableMethod();
    }

}
