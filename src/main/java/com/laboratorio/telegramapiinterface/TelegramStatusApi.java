package com.laboratorio.telegramapiinterface;

import com.laboratorio.telegramapiinterface.model.TelegramStatus;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 23/08/2024
 * @updated 17/10/2024
 */
public interface TelegramStatusApi {
    TelegramStatus postStatus(String text);
    boolean deleteStatus(int messageId);
    
    // Postear un status con imagen
    TelegramStatus postStatus(String text, String filePath) throws Exception;
}