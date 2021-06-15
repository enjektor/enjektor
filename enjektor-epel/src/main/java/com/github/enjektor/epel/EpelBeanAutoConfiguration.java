package com.github.enjektor.epel;

import com.github.enjektor.core.auto.configuration.BeanAutoConfiguration;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.core.wrapper.StringListWrapper;
import com.github.enjektor.epel.yaml.converter.ConcreteYamlExporter;
import com.github.enjektor.epel.yaml.converter.YamlConverter;

import java.util.List;

public class EpelBeanAutoConfiguration implements BeanAutoConfiguration {

    @Override
    public Pair export() {
        return export(null);
    }

    @Override
    public Pair export(String profileProperty) {
        final YamlConverter yamlConverter = new ConcreteYamlExporter();
        final EpelParser epelParser = new ConcreteEpelParser();

        final String configuration = yamlConverter.convert(profileProperty);
        final List<String> parse = epelParser.parse(configuration);

        final StringListWrapper stringListWrapper = new StringListWrapper(parse);

        final Bean bean = new Bean(StringListWrapper.class);
        bean.register("stringListWrapper", stringListWrapper);

        return Pair.builder()
            .bean(bean)
            .type(StringListWrapper.class)
            .build();
    }
}
