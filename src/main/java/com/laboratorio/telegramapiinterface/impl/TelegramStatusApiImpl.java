package com.laboratorio.telegramapiinterface.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.telegramapiinterface.TelegramStatusApi;
import com.laboratorio.telegramapiinterface.model.TelegramDeleteMessage;
import com.laboratorio.telegramapiinterface.model.TelegramSendMessage;
import com.laboratorio.telegramapiinterface.model.response.TelegramDeleteMessageResponse;
import com.laboratorio.telegramapiinterface.model.response.TelegramSendMessageResponse;
import com.laboratorio.telegramapiinterface.utils.TelegramApiConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 23/08/2024
 * @updated 04/10/2024
 */
public class TelegramStatusApiImpl implements TelegramStatusApi {
    protected static final Logger log = LogManager.getLogger(TelegramStatusApiImpl.class);
    private final ApiClient client;
    private final String access_token;
    private final String chadId;
    private final TelegramApiConfig apiConfig;
    private final Gson gson;

    public TelegramStatusApiImpl(String access_token, String chadId) {
        this.client = new ApiClient();
        this.access_token = access_token;
        this.chadId = chadId;
        this.apiConfig = TelegramApiConfig.getInstance();
        this.gson = new Gson();
    }
    
    private void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    @Override
    public TelegramSendMessageResponse postStatus(String text) {
        String urlBase = this.apiConfig.getProperty("url_base_telegram");
        String endpoint = this.apiConfig.getProperty("endpoint_sendmessage");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("sendmessage_valor_ok"));
        
        try {
            // Se crea la request
            TelegramSendMessage requestSendMessage = new TelegramSendMessage(this.chadId, text);
            String requestJson = this.gson.toJson(requestSendMessage);
            log.debug("Request a enviar: " + requestJson);
            
            String url = urlBase + "/" + this.access_token + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST, requestJson);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            return this.gson.fromJson(response.getResponseStr(), TelegramSendMessageResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (ApiClientException e) {
            throw  e;
        }
    }

    @Override
    public boolean deleteStatus(int messageId) {
        String urlBase = this.apiConfig.getProperty("url_base_telegram");
        String endpoint = this.apiConfig.getProperty("endpoint_deleteMessage");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("deleteMessage_valor_ok"));
        
        try {
            // Se crea la request
            TelegramDeleteMessage telegramDeleteMessage = new TelegramDeleteMessage(this.chadId, messageId);
            String requestJson = this.gson.toJson(telegramDeleteMessage);
            log.debug("Request a enviar: " + requestJson);
            
            String url = urlBase + "/" + this.access_token + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST, requestJson);
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            TelegramDeleteMessageResponse deleteMessageResponse = this.gson.fromJson(response.getResponseStr(), TelegramDeleteMessageResponse.class);
            
            return deleteMessageResponse.isResult();
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (ApiClientException e) {
            throw  e;
        }
    }

    @Override
    public TelegramSendMessageResponse postStatus(String text, String filePath) throws Exception {
        String urlBase = this.apiConfig.getProperty("url_base_telegram");
        String endpoint = this.apiConfig.getProperty("endpoint_sendphoto");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("sendphoto_valor_ok"));
        
        try {
            String url = urlBase + "/" + this.access_token + "/" + endpoint;
            ApiRequest request = new ApiRequest(url, okStatus, ApiMethodType.POST);
            request.addTextFormData("chat_id", this.chadId);
            request.addTextFormData("caption", text);
            request.addFileFormData("photo", filePath);
            
            
            ApiResponse response = this.client.executeApiRequest(request);
            
            return this.gson.fromJson(response.getResponseStr(), TelegramSendMessageResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (ApiClientException e) {
            throw  e;
        }
    }
}