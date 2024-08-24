package com.laboratorio.telegramapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 24/08/2024
 * @updated 24/08/2024
 */

@Getter @Setter @AllArgsConstructor
public class TelegramDeleteMessage {
    private String chat_id;
    private int message_id;
}