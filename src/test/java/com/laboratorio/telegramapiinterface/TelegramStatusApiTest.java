package com.laboratorio.telegramapiinterface;

import com.laboratorio.telegramapiinterface.impl.TelegramStatusApiImpl;
import com.laboratorio.telegramapiinterface.model.response.TelegramSendMessageResponse;
import com.laboratorio.telegramapiinterface.utils.TelegramApiConfig;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 24/08/2024
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TelegramStatusApiTest {
    private TelegramStatusApi statusApi = null;
    private static int messageId;

    @BeforeEach
    private void initTest() {
        TelegramApiConfig config = TelegramApiConfig.getInstance();
        String accessToken = config.getProperty("access_token_telegram");
        String chatId = config.getProperty("id_grupo_telegram");
        
        this.statusApi = new TelegramStatusApiImpl(accessToken, chatId);
    }
    
    @Test @Order(1)
    public void postStatus() {
        String texto = "Este es un mensaje de prueba enviado desde JUNI5";
        
        TelegramSendMessageResponse response = this.statusApi.postStatus(texto);
        messageId = response.getResult().getMessage_id();
        
        assertTrue(response.isOk());
        assertTrue(response.getResult().getPhoto() == null);
    }
    
    @Test @Order(2)
    public void deleteMessage() {
        boolean result = this.statusApi.deleteStatus(messageId);
        
        assertTrue(result);
    }
    
    @Test @Order(3)
    public void postStatusWithImage() throws Exception {
        String texto = "Este es un mensaje de prueba enviado desde JUNI5 que acompaña la maravillosa foto del Laboratorio de Rafa";
        String filePath = "C:\\Users\\rafa\\Pictures\\Tutoriales\\laboratorio-2024.jpg";
        
        TelegramSendMessageResponse response = this.statusApi.postStatus(texto, filePath);
        messageId = response.getResult().getMessage_id();
        
        assertTrue(response.isOk());
        assertTrue(!response.getResult().getPhoto().isEmpty());
    }
    
    @Test @Order(4)
    public void deleteMessageWithPhoto() {
        boolean result = this.statusApi.deleteStatus(messageId);
        
        assertTrue(result);
    }
}