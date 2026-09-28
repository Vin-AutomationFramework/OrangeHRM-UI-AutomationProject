package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties properties;

    static {
        try {
            String configFilePath = "src/main/resources/config.properties";
            FileInputStream input = new FileInputStream(configFilePath);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
