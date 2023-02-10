package com.github.enjektor.context.pg;

import com.github.enjektor.context.pg.interfaces.Dog;
import com.github.enjektor.context.pg.interfaces.Sum;
import com.github.enjektor.context.pg.interfaces.Sum10;
import com.github.enjektor.context.pg.interfaces.Sum20;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

@Dependency
public class SampleDependency {

//    @Inject
//    private Dog dog;

    @Inject
    private Sum10 sum10;
//
//    @Inject(value = "sum10")
//    private Sum sum1;

    public void anyExecutableMethod() {
        int sum = sum10.sum(13);
        System.out.println("sum = " + sum);
    }

}
