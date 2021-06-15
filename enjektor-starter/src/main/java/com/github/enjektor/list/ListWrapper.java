package com.github.enjektor.list;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

import java.util.List;

@Dependency
public class ListWrapper {

    @Inject
    private List<String> values;

    public void invoke() {
        values.forEach(System.out::println);
    }
}
