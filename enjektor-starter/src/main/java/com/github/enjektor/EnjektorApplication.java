package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.ApplicationContextImpl;
import com.github.enjektor.repo.A;

public class EnjektorApplication {

    public static void start(Class<?> mainClass, String[] args) throws InstantiationException, IllegalAccessException {
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContextImpl.getInstance(EnjektorApplication.class);
        final A bean = applicationContext.getBean(A.class);
        final String a = bean.a();
        System.out.println("a = " + a);
    }
}
