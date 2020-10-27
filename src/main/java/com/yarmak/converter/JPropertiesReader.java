package com.yarmak.converter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class JPropertiesReader implements ConfigReader {
    @Override
    public Map<String, String> readConfigurationFromFile(final Path path) {
        Properties properties = new Properties();
        try (final InputStream is = Files.newInputStream(path)) {
            properties.load(is);
            return properties.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue(), (v1, v2) -> v1, TreeMap::new));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
