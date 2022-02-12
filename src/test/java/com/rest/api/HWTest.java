package com.rest.api;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

public class HWTest {
    private static org.apache.log4j.Logger log = Logger.getLogger(FirstTest.class);

    @Test
    public void WithoutDocumentNumber() throws IOException {
        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("https://api.novaposhta.ua/v2.0/json/");
        String json = "{\n" +
                "    \"apiKey\": \"f0a8ed3fd5f618970de71afbc1d9828c\",\n" +
                "    \"modelName\": \"TrackingDocument\",\n" +
                "    \"calledMethod\": \"getStatusDocuments\",\n" +
                "    \"methodProperties\": {\n" +
                "        \"Documents\": [\n" +
                "            {\n" +
                "                \"DocumentNumber\": \"\",\n" +
                "                \"Phone\":\"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-type", "string");
        CloseableHttpResponse response = httpClient.execute(httpPost);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".success");

        Assert.assertTrue(jsonArray.contains(false));
    }

    @Test
    public void WithoutPhoneNumber() throws IOException {
        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("https://api.novaposhta.ua/v2.0/json/");
        String json = "{\n" +
                "    \"apiKey\": \"f0a8ed3fd5f618970de71afbc1d9828c\",\n" +
                "    \"modelName\": \"TrackingDocument\",\n" +
                "    \"calledMethod\": \"getStatusDocuments\",\n" +
                "    \"methodProperties\": {\n" +
                "        \"Documents\": [\n" +
                "            {\n" +
                "                \"DocumentNumber\": \"20700146926298\",\n" +
                "                \"Phone\":\"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-type", "string");
        CloseableHttpResponse response = httpClient.execute(httpPost);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".success");
        JSONArray jsonArrayNull = docCtx.read(".RecipientFullName");

        Assert.assertTrue(jsonArray.contains(true));
        Assert.assertTrue(jsonArrayNull.isEmpty());
    }

    @Test
    public void WithAllInfo() throws IOException {
        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("https://api.novaposhta.ua/v2.0/json/");
        String json = "{\n" +
                "    \"apiKey\": \"f0a8ed3fd5f618970de71afbc1d9828c\",\n" +
                "    \"modelName\": \"TrackingDocument\",\n" +
                "    \"calledMethod\": \"getStatusDocuments\",\n" +
                "    \"methodProperties\": {\n" +
                "        \"Documents\": [\n" +
                "            {\n" +
                "                \"DocumentNumber\": \"20700146926298\",\n" +
                "                \"Phone\":\"0675701023\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-type", "string");
        CloseableHttpResponse response = httpClient.execute(httpPost);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".success");
        JSONArray jsonArrayNull = docCtx.read(".RecipientFullName");

        Assert.assertTrue(jsonArray.contains(true));
        Assert.assertTrue(jsonArrayNull.contains("Тетяна Виногородська"));
    }
}
