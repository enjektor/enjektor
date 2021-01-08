package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.repo.A;

public class EnjektorApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = PrimitiveApplicationContext.getInstance(EnjektorApplication.class);
        final A a4 = applicationContext.getBean(A.class, "a4");
        System.out.println(a4.a());
    }
}
