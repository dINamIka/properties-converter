package com.yarmak.converter;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Map;

import static org.hamcrest.Matchers.*;

class YamlReaderTest {

    private final YamlReader classUnderTest = new YamlReader();

    @Test
    void whenProperYamlConfigRead_shouldSucceed() {
        final Map<String, String> actualResult = classUnderTest.readConfigurationFromFile(Paths.get(getClass().getResource("/correct_without_duplicates.yaml").getPath()));
        System.out.println(actualResult);
        final Map<String, String> expectedProperties = Map.of("datasource.routing.master.password", "something_here",
                "datasource.routing.slave.password", "something_there",
                "profile.client", "downloads-registry",
                "profile.env", "staging",
                "profile.secret", "real_secret");
        MatcherAssert.assertThat(actualResult.entrySet(), everyItem(is(in(expectedProperties.entrySet()))));
    }

    @Test
    void whenYamlWithDuplicatesRead_shouldSucceed_and_rerturnPropertiesWithLastDuplicatedOccurrence() {
        final Map<String, String> actualResult = classUnderTest.readConfigurationFromFile(Paths.get(getClass().getResource("/correct_with_duplicates.yaml").getPath()));
        System.out.println(actualResult);
        final Map<String, String> expectedProperties = Map.of("datasource.routing.master.password", "something_here",
                "datasource.routing.slave.password", "something_there_and_it's_duplicated",
                "profile.client", "downloads-registry",
                "profile.env", "staging",
                "profile.secret", "real_secret");
        MatcherAssert.assertThat(actualResult.entrySet(), everyItem(is(in(expectedProperties.entrySet()))));
    }

    @Test
    void whenYamlWithEmptyValuesRead_shouldSucceed_and_ReturnPropertyWithEmptyValues() {
        final Map<String, String> actualResult = classUnderTest.readConfigurationFromFile(Paths.get(getClass().getResource("/correct_with_empty_values.yaml").getPath()));
        System.out.println(actualResult);
        final Map<String, String> expectedProperties = Map.of("datasource.routing.master.password", "something_here",
                "datasource.routing.slave.password", "",
                "profile.client", "downloads-registry",
                "profile.env", "",
                "profile.secret", "real_secret");
        MatcherAssert.assertThat(actualResult.entrySet(), everyItem(is(in(expectedProperties.entrySet()))));
    }

}