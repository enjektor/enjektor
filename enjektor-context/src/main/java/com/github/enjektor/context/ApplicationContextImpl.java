package com.github.enjektor.context;

import com.github.enjektor.core.scanner.BaseScanner;
import com.github.enjektor.core.scanner.InterfaceScanner;
import com.github.enjektor.core.scanner.InterfaceScannerImpl;
import com.github.enjektor.core.scanner.Scanner;

public class ApplicationContextImpl implements ApplicationContext {

    private static Scanner scanner = BaseScanner.getInstance();
    private static InterfaceScanner interfaceScanner = InterfaceScannerImpl.getInstance();

    @Override
    public <T> T getBean(Class<T> classType) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> classType, String fieldName) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
