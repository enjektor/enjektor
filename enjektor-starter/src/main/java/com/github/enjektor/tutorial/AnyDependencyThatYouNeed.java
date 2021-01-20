package com.github.enjektor.tutorial;

import com.github.enjektor.core.annotations.Dependency;

import java.util.Arrays;
import java.util.stream.Stream;

@Dependency
public class AnyDependencyThatYouNeed<T extends Comparable<T>> {

    private void dummyMethod(final T[] t) {
        final Stream<T> sorted = Arrays.stream(t).sorted();
        sorted.forEach(System.out::println);
    }
}


