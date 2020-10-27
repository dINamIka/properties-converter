package com.yarmak.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandRunner {

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private static ConfigReader buildReader(final String cArg) {
        switch (CommandArguments.getCommand(cArg)) {
            case YAML:
                return new YamlReader();
            case PROPERTIES:
                return new JPropertiesReader();
            default:
                throw new IllegalArgumentException("Unknown source file type!");
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                throw new IllegalArgumentException("Usage: prpc -[y] {input_file_path}");
            }
            final Path absFilePath = Paths.get(args[1]);
            if (!Files.exists(absFilePath)) {
                throw new IllegalArgumentException(String.format("\'%s\' doesn't exist!", absFilePath));
            }
            final var configReader = buildReader(args[0]);
            final var json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(configReader.readConfigurationFromFile(absFilePath));
            System.out.println(json);
        } catch (IllegalArgumentException | JsonProcessingException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
