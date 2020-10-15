package com.searchfight.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SearchPropertyTest {

    @Test
    public void getPropertyValue() throws IOException, IllegalArgException {
        Properties properties = initProperties();
        Assertions.assertEquals(properties.size(), SearchProperty.getProperties().size());
        Assertions.assertEquals(properties.getProperty("google.apihost"), SearchProperty.getPropertyValue("google.apihost"));
    }

    @Test
    public void testPropertyNotPresent() {
        Assertions.assertThrows(IllegalArgException.class, () ->
                SearchProperty.getPropertyValue("thhhhhh")
        );
    }
    @Test
    public void getUnknwnProperty() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new SearchProperty("123.properties")
        );
    }
    private Properties initProperties() throws IOException {
        Properties properties = new Properties();
        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream("SearchConfig.properties");
        properties.load(resourceStream);
        return properties;
    }
}
