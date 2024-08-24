package com.laboratorio.telegramapiinterface;

import com.laboratorio.telegramapiinterface.model.response.TelegramSendMessageResponse;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 24/08/2024
 */
public interface TelegramStatusApi {
    TelegramSendMessageResponse postStatus(String text);
    boolean deleteStatus(int messageId);
    
    // Postear un status con imagen
    TelegramSendMessageResponse postStatus(String text, String filePath) throws Exception;
}