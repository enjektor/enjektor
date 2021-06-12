package com.github.enjektor.epel.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class YamlConverter {

    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();
        final ClassLoader classLoader = YamlConverter.class.getClassLoader();

        try (InputStream in = classLoader.getResourceAsStream("application.yaml")) {
            TreeMap<String, Map<String, Object>> config = yaml.loadAs(in, TreeMap.class);

            final String s = toProperties(config);

            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(System.lineSeparator());

            while (scanner.hasNext()) {
                final String next = scanner.next();
                System.out.println("next = " + next);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toProperties(TreeMap<String, Map<String, Object>> config) {
        final StringBuilder sb = new StringBuilder();

        for (String key : config.keySet()) sb.append(toString(key, config.get(key)));
        return sb.toString();
    }

    private static String toString(String key, Object mapr) {
        final StringBuilder sb = new StringBuilder();

        if (!(mapr instanceof Map)) {
            sb.append(key + "=" + mapr + "\n");
            return sb.toString();
        }

        final Map<String, Object> map = (Map<String, Object>) mapr;

        for (String mapKey : map.keySet()) {
            if (map.get(mapKey) instanceof Map) {
                sb.append(toString(key + "." + mapKey, map.get(mapKey)));
            } else {
                sb.append(String.format("%s.%s=%s%n", key, mapKey, map.get(mapKey).toString()));
            }
        }
        return sb.toString();
    }

}
