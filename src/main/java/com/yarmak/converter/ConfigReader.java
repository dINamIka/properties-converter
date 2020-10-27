package com.yarmak.converter;

import java.nio.file.Path;
import java.util.Map;

public interface ConfigReader {
    Map<String, String> readConfigurationFromFile(Path path);
}
