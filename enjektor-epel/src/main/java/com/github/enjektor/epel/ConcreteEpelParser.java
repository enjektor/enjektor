package com.github.enjektor.epel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConcreteEpelParser implements EpelParser {

    @Override
    public List<String> parse(final String configuration) {
        final List<String> configurations = new ArrayList<>();

        try (Scanner scanner = new Scanner(configuration)) {
            scanner.useDelimiter(System.lineSeparator());
            while (scanner.hasNext()) configurations.add(scanner.next());
        }

        return configurations;
    }
}
