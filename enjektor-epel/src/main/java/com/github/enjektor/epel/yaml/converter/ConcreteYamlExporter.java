package com.github.enjektor.epel.yaml.converter;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class ConcreteYamlExporter implements YamlConverter {

    @Override
    public String convert() {
        return convert(null);
    }

    @Override
    public String convert(String profileProperty) {
        String profile = (profileProperty != null ? profileProperty : "application") + ".yaml";
        Yaml yaml = new Yaml();
        final Class<? extends ConcreteYamlExporter> klass = getClass();
        final ClassLoader classLoader = klass.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(profile)) {
            TreeMap<String, Map<String, Object>> config = yaml.loadAs(inputStream, TreeMap.class);
            return toProperties(config);
        } catch (IOException e) {
            return null;
        }
    }

    private String toProperties(TreeMap<String, Map<String, Object>> config) {
        final StringBuilder sb = new StringBuilder();

        for (String key : config.keySet()) sb.append(toString(key, config.get(key)));
        return sb.toString();
    }

    private String toString(String key, Object mapr) {
        final StringBuilder sb = new StringBuilder();

        if (!(mapr instanceof Map)) {
            sb.append(key + "=" + mapr + "\n");
            return sb.toString();
        }

        Map<String, Object> map = (Map<String, Object>) mapr;

        for (String mapKey : map.keySet()) {
            if (map.get(mapKey) instanceof Map) {
                sb.append(toString(key + "." + mapKey, map.get(mapKey)));
            } else {
                sb.append(String.format("%s.%s=%s%n", key, mapKey, map.get(mapKey).toString()));
            }
        }

        map = null;
        return sb.toString();
    }

}
