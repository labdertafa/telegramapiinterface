package com.laboratorio.telegramapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 23/08/2024
 */

@Getter @Setter @AllArgsConstructor
public class TelegramSendMessage {
    private String chat_id;
    private String text;   
}