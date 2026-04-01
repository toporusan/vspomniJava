package com.apitests.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton — читает конфиг из config.properties.
 * Если запущено с -Denv=dev — поверх грузится config.dev.properties.
 *
 * Использование:
 *   ConfigManager.getInstance().getBaseUrl()
 *   ConfigManager.getInstance().get("request.timeout")
 */
public class ConfigManager {

    private static volatile ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        loadProperties("config/config.properties");

        String env = System.getProperty("env");
        if (env != null && !env.isBlank()) {
            loadProperties("config/config." + env + ".properties");
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadProperties(String fileName) {
        try (InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName)) {
            if (stream != null) {
                properties.load(stream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить конфиг: " + fileName, e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Ключ не найден в конфиге: " + key);
        }
        return value;
    }

    public String getBaseUrl() {
        return get("base.url");
    }

    public String getUsersBaseUrl() {
        return get("base.url.users");
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}