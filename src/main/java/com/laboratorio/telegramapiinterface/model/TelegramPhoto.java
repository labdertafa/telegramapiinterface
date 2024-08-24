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
 * @updated 24/08/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TelegramPhoto {
    private String file_id;
    private String file_unique_id;
    private int file_size;
    private int width;
    private int height;
}