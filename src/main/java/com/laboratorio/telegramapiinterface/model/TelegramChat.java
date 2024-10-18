package com.laboratorio.telegramapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 18/10/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TelegramChat {
    private long id;
    private boolean is_bot;
    private String first_name;
    private String title;
    private String username;
    private String type;
}