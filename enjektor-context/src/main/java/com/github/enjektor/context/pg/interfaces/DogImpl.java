package com.github.enjektor.context.pg.interfaces;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

@Dependency
public class DogImpl implements Dog {

    @Inject(qualifier = Sum20.class)
    private Sum sum;

    @Inject(value = "sum10")
    private Sum sum1;

    @Inject(value = "defaultWorker")
    private Worker worker;

    @Inject(value = "sum30")
    private Sum sum30;

    @Override
    public void bark() {
        System.out.println("bark");
//        dog.bark();
        int sumx = sum.sum(1);
        System.out.println("sum1 = " + sumx);

        int sum2 = sum1.sum(15);
        System.out.println("sum2 = " + sum2);

        worker.work();

        int sum3 = sum30.sum(1);
        System.out.println("sum3 = " + sum3);
    }
}
