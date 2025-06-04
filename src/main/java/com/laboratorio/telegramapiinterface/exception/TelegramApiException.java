package com.laboratorio.telegramapiinterface.exception;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 10/07/2024
 * @updated 04/05/2025
 */
public class TelegramApiException extends RuntimeException {
    private Throwable causaOriginal = null;
    
    public TelegramApiException(String message) {
        super(message);
    }
    
    public TelegramApiException(String message, Throwable causaOriginal) {
        super(message);
        this.causaOriginal = causaOriginal;
    }
    
    @Override
    public String getMessage() {
        if (this.causaOriginal != null) {
            return super.getMessage() + " | Causa original: " + this.causaOriginal.getMessage();
        }
        
        return super.getMessage();
    }
}