package com.laboratorio.telegramapiinterface.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 10/07/2024
 * @updated 23/08/2024
 */
public class TelegramApiException extends RuntimeException {
    private static final Logger log = LogManager.getLogger(TelegramApiException.class);
    
    public TelegramApiException(String className, String message) {
        super(message);
        log.error(String.format("Error %s: %s", className, message));
    }
}