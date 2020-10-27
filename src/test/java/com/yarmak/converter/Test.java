package com.yarmak.converter;

import java.io.FileInputStream;

public class Test {

    @org.junit.jupiter.api.Test
    void test() throws Exception {

        final FileInputStream is = new FileInputStream("/home/eugene/Private/properties-converter/src/test/resources/correct_with_duplicates.yaml");
        System.out.println(is.available());
    }
}
