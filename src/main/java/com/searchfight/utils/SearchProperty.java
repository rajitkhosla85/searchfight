package com.searchfight.utils;

import com.searchfight.callers.WebSearch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SearchProperty {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSearch.class);
    private static final String PROPERTY_FILE = "SearchConfig.properties";
    static Properties properties = new Properties();

    SearchProperty(String file) throws IOException {
        initProperties(file);
    }

    private static void initProperties(String file) throws IOException {
        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(StringUtils.isNotEmpty(file) ? file : PROPERTY_FILE);
        properties.load(resourceStream);

    }

    public static String getPropertyValue(String key) throws IOException, IllegalArgException {
        if (properties.isEmpty()) {
            initProperties(SearchConstants.EMPTY);
        }
        String propertyValue = properties.getProperty(key);
        if (StringUtils.isEmpty(propertyValue)) {
            LOGGER.error("Propery Value not present. Please make a entry: ");
            throw new IllegalArgException("Propery Value not present. Please make a entry");
        }
        return propertyValue;
    }

    static Properties getProperties() throws IOException {
        if (properties.isEmpty()) {
            initProperties(SearchConstants.EMPTY);
        }
        return properties;
    }
}
