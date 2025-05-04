package com.laboratorio.telegramapiinterface;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.telegramapiinterface.impl.TelegramStatusApiImpl;
import com.laboratorio.telegramapiinterface.model.TelegramStatus;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 23/08/2024
 * @updated 04/05/2025
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TelegramStatusApiTest {
    private TelegramStatusApi statusApi = null;
    private static int messageId;

    @BeforeEach
    private void initTest() {
        ReaderConfig config = new ReaderConfig("config//telegram_api.properties");
        String accessToken = config.getProperty("access_token_telegram");
        String chatId = config.getProperty("id_grupo_telegram");
        
        this.statusApi = new TelegramStatusApiImpl(accessToken, chatId);
    }
    
    @Test @Order(1)
    public void postStatus() {
        String texto = "Este es un mensaje de prueba enviado desde JUNI5";
        
        TelegramStatus status = this.statusApi.postStatus(texto);
        messageId = status.getMessage_id();
        
        assertTrue(status.getPhoto() == null);
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
        
        TelegramStatus status = this.statusApi.postStatus(texto, filePath);
        messageId = status.getMessage_id();
        
        assertTrue(!status.getPhoto().isEmpty());
    }
    
    @Test @Order(4)
    public void deleteMessageWithPhoto() {
        boolean result = this.statusApi.deleteStatus(messageId);
        
        assertTrue(result);
    }
}