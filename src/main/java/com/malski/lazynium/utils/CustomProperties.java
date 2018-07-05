package com.malski.lazynium.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class CustomProperties {

    public static Map<String, String> getProperties(String propertiesFileName) {
        Map<String, String> map = new HashMap<>();
        if (map.isEmpty()) {
            map = readPropertiesFromFile(propertiesFileName);
            map.putAll(readSystemProps());
        }
        return map;
    }

    public static Map<String, String> readPropertiesFromFile(String propertiesFileName) {
        try {
            File f = new File(String.format("src%1$smain%1$sresources%1$s%2$s", File.separator, propertiesFileName));
            InputStream in = new FileInputStream(f);
            Properties props = new Properties();
            props.load(in);

            return propertiesToMap(props);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private static Map<String, String> readSystemProps() {
        return propertiesToMap(System.getProperties());
    }

    private static Map<String, String> propertiesToMap(Properties properties) {
        return properties.entrySet().stream().collect(Collectors.toMap(
                e -> String.valueOf(e.getKey()),
                e -> String.valueOf(e.getValue())));
    }
}