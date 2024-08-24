package com.laboratorio.telegramapiinterface.model.response;

import com.laboratorio.telegramapiinterface.model.TelegramSendResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 23/08/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TelegramSendMessageResponse {
    private boolean ok;
    private TelegramSendResult result;
}