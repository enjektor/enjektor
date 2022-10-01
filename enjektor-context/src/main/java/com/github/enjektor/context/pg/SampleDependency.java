package com.github.enjektor.context.pg;

import com.github.enjektor.context.pg.interfaces.Dog;
import com.github.enjektor.context.pg.interfaces.Sum;
import com.github.enjektor.context.pg.interfaces.Sum20;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

@Dependency
public class SampleDependency {

    @Inject
    private Dog dog;

    @Inject(qualifier = Sum20.class)
    private Sum sum;

    @Inject(value = "sum10")
    private Sum sum1;

    public void anyExecutableMethod() {
        int x = sum.sum(20);
        System.out.println("x = " + x);
        int y = sum1.sum(10);
        System.out.println("y = " + y);
        dog.bark();
    }

}
