package com.github.enjektor.core.wrapper;

import java.util.List;

public class IntegerListWrapper {
    private List<Integer> values;

    public IntegerListWrapper(List<Integer> values) {
        this.values = values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public List<Integer> getValues() {
        return values;
    }
}
