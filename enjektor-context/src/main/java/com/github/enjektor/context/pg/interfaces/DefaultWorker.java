package com.github.enjektor.context.pg.interfaces;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

@Dependency
public class DefaultWorker implements Worker {

    @Inject(value = "sum30")
    private Sum sum30;

    @Inject(qualifier = AnotherWorker.class)
    private Worker worker;

    @Override
    public void work() {
        int sum = sum30.sum(1);
        System.out.println("i'm working" + sum);
        worker.work();
    }
}
