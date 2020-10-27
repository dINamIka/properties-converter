package com.yarmak.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class YamlReader implements ConfigReader {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

    @Override
    public Map<String, String> readConfigurationFromFile(final Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            @SuppressWarnings("unchecked") final Map<String, Object> config = mapper.readValue(in, LinkedHashMap.class);
            return config
                    .entrySet()
                    .stream()
                    .map(e -> collectProperties(e.getKey(), e.getValue()))
                    .flatMap(m -> m.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, TreeMap::new));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Map<String, String> collectProperties(final String pKey, final Object node) {
        final Map<String, String> collectedProps = new LinkedHashMap<>();
        if (node instanceof Map) {
            @SuppressWarnings("unchecked") final Map<String, Object> innerNode = (Map<String, Object>) node;
            if (!innerNode.isEmpty()) {
                final String nextPKey = innerNode.keySet().iterator().next();
                collectedProps.putAll(collectProperties(pKey + "." + nextPKey, innerNode.remove(nextPKey)));
                collectedProps.putAll(collectProperties(pKey, innerNode));
            }
        } else {
            collectedProps.put(pKey, !Objects.isNull(node) ? node.toString() : "");
        }
        return collectedProps;
    }

}
