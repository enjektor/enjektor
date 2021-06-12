package com.github.enjektor.epel.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.enjektor.core.bean.Bean;
import com.github.enjektor.epel.EpelExporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;

public class YamlEpelExporter implements EpelExporter {

    @Override
    public Map<Class<?>, Bean> export(String profileProperty) {
        final Map<Class<?>, Bean> beans = new WeakHashMap<>();

        Bean bean = new Bean(String.class);
        bean.register("jdbc.query", "select * from memory_metrics");
        bean.register("jdbc.pes", "pes");

        beans.put(String.class, bean);

        final Class<? extends YamlEpelExporter> klass = getClass();
        final ClassLoader classLoader = klass.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("application.yml")) {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return beans;
    }

    @Override
    public Map<Class<?>, Bean> export() {
        return null;
    }
}
