package com.github.enjektor.tutorial;

import java.util.Arrays;
import java.util.stream.Stream;

public class AnyDependencyThatYouDontNeed<T extends Comparable<T>> {

    private void dummyMethod(final T[] t) {
        final Stream<T> sorted = Arrays.stream(t).sorted();
        sorted.forEach(System.out::println);
    }
}


