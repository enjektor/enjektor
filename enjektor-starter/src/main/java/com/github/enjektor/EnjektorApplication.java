package com.github.enjektor;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.ApplicationContextImpl;

public class EnjektorApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = ApplicationContextImpl.getInstance(EnjektorApplication.class);
    }
}
