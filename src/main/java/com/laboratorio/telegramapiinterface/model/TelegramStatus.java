package com.laboratorio.telegramapiinterface.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 23/08/2024
 * @updated 18/10/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TelegramStatus {
    private int message_id;
    private String author_signature;
    private TelegramChat from;
    private TelegramChat chat;
    private long date;
    private List<TelegramPhoto> photo;
    private String text;
    private String caption;
}