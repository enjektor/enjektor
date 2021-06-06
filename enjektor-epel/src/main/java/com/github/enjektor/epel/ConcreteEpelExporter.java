package com.github.enjektor.epel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.enjektor.core.bean.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;

public class ConcreteEpelExporter implements EpelExporter {

    @Override
    public Map<Class<?>, Bean> export() {
        final Map<Class<?>, Bean> beans = new WeakHashMap<>();

        Bean bean = new Bean(String.class);
        bean.register("jdbc.query", "select * from memory_metrics");

        beans.put(String.class, bean);

        final Class<? extends ConcreteEpelExporter> klass = getClass();
        final ClassLoader classLoader = klass.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("application.yml")) {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            final Map<String, Object> stringObjectMap = mapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return beans;
    }
}
