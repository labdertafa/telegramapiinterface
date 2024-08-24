package com.laboratorio.telegramapiinterface.utils;

import java.io.FileReader;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 16/08/2024
 * @updated 23/08/2024
 */
public class TelegramApiConfig {
    private static final Logger log = LogManager.getLogger(TelegramApiConfig.class);
    private static TelegramApiConfig instance;
    private final Properties properties;

    private TelegramApiConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try {
            this.properties.load(new FileReader("config//telegram_api.properties"));
        } catch (Exception e) {
            log.error("Ha ocurrido un error leyendo el fichero de configuración del API de Telegram. Finaliza la aplicación!");
            log.error(String.format("Error: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.error(String.format("Causa: %s", e.getCause().getMessage()));
            }
            System.exit(-1);
        }
    }

    public static TelegramApiConfig getInstance() {
        if (instance == null) {
            synchronized (TelegramApiConfig.class) {
                if (instance == null) {
                    instance = new TelegramApiConfig();
                }
            }
        }
        
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}