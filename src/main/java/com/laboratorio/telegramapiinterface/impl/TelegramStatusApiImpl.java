package com.laboratorio.telegramapiinterface.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.telegramapiinterface.TelegramStatusApi;
import com.laboratorio.telegramapiinterface.exception.TelegramApiException;
import com.laboratorio.telegramapiinterface.model.TelegramDeleteMessage;
import com.laboratorio.telegramapiinterface.model.TelegramSendMessage;
import com.laboratorio.telegramapiinterface.model.response.TelegramDeleteMessageResponse;
import com.laboratorio.telegramapiinterface.model.response.TelegramSendMessageResponse;
import com.laboratorio.telegramapiinterface.utils.TelegramApiConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataWriter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 23/08/2024
 * @updated 25/09/2024
 */
public class TelegramStatusApiImpl implements TelegramStatusApi {
    protected static final Logger log = LogManager.getLogger(TelegramStatusApiImpl.class);
    private final String access_token;
    private final String chadId;
    private final TelegramApiConfig apiConfig;

    public TelegramStatusApiImpl(String access_token, String chadId) {
        this.access_token = access_token;
        this.chadId = chadId;
        this.apiConfig = TelegramApiConfig.getInstance();
    }
    
    private void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    @Override
    public TelegramSendMessageResponse postStatus(String text) {
        Client client = ClientBuilder.newClient();
        Response response = null;
        String urlBase = this.apiConfig.getProperty("url_base_telegram");
        String endpoint = this.apiConfig.getProperty("endpoint_sendmessage");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("sendmessage_valor_ok"));
        
        try {
            // Se crea la request
            Gson gson = new Gson();
            TelegramSendMessage request = new TelegramSendMessage(this.chadId, text);
            String requestJson = gson.toJson(request);
            log.info("Request a enviar: " + requestJson);
            
            String url = urlBase + "/" + this.access_token + "/" + endpoint;
            WebTarget target = client.target(url);
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(requestJson, MediaType.APPLICATION_JSON));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new TelegramApiException(TelegramStatusApiImpl.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            return gson.fromJson(jsonStr, TelegramSendMessageResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (TelegramApiException e) {
            throw  e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }

    @Override
    public boolean deleteStatus(int messageId) {
        Client client = ClientBuilder.newClient();
        Response response = null;
        String urlBase = this.apiConfig.getProperty("url_base_telegram");
        String endpoint = this.apiConfig.getProperty("endpoint_deleteMessage");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("deleteMessage_valor_ok"));
        
        try {
            // Se crea la request
            Gson gson = new Gson();
            TelegramDeleteMessage request = new TelegramDeleteMessage(this.chadId, messageId);
            String requestJson = gson.toJson(request);
            log.info("Request a enviar: " + requestJson);
            
            String url = urlBase + "/" + this.access_token + "/" + endpoint;
            WebTarget target = client.target(url);
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(requestJson, MediaType.APPLICATION_JSON));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new TelegramApiException(TelegramStatusApiImpl.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            TelegramDeleteMessageResponse deleteMessageResponse = gson.fromJson(jsonStr, TelegramDeleteMessageResponse.class);
            
            return deleteMessageResponse.isResult();
        } catch (JsonSyntaxException e) {
            logException(e);
            throw  e;
        } catch (TelegramApiException e) {
            throw  e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }

    @Override
    public TelegramSendMessageResponse postStatus(String text, String filePath) throws Exception {
        ResteasyClient client = (ResteasyClient)ResteasyClientBuilder.newBuilder().build();
        Response response = null;
        String urlBase = this.apiConfig.getProperty("url_base_telegram");
        String endpoint = this.apiConfig.getProperty("endpoint_sendphoto");
        int okStatus = Integer.parseInt(this.apiConfig.getProperty("sendphoto_valor_ok"));
        
        try {
            String url = urlBase + "/" + this.access_token + "/" + endpoint;
            WebTarget target = client.target(url)
                    .register(MultipartFormDataWriter.class);
            
            MultipartFormDataOutput formDataOutput = new MultipartFormDataOutput();
            File imageFile = new File(filePath);
            InputStream fileStream = new FileInputStream(imageFile);
            formDataOutput.addFormData("chat_id", this.chadId, MediaType.TEXT_PLAIN_TYPE);
            formDataOutput.addFormData("photo", fileStream, MediaType.APPLICATION_OCTET_STREAM_TYPE, imageFile.getName());
            formDataOutput.addFormData("caption", text, MediaType.TEXT_PLAIN_TYPE);
            
            response = target.request()
                    .post(Entity.entity(formDataOutput, MediaType.MULTIPART_FORM_DATA));
            
            String jsonStr = response.readEntity(String.class);
            if (response.getStatus() != okStatus) {
                log.error(String.format("Respuesta del error %d. Detalle: %s", response.getStatus(), jsonStr));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new TelegramApiException(TelegramStatusApiImpl.class.getName(), str);
            }
            
            log.info("Se ejecutó la query: " + url);
            log.info("Respuesta recibida: " + jsonStr);
            
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, TelegramSendMessageResponse.class);
        } catch (JsonSyntaxException | FileNotFoundException e) {
            logException(e);
            throw  e;
        } catch (TelegramApiException e) {
            throw  e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
}