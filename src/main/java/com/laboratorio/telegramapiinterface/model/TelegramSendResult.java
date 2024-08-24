package com.laboratorio.telegramapiinterface.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 24/08/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TelegramSendResult {
    private int message_id;
    private String author_signature;
    private TelegramChat sender_chat;
    private TelegramChat chat;
    private long date;
    private List<TelegramPhoto> photo;
    private String text;
}