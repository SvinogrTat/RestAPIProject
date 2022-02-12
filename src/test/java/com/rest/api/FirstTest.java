package com.rest.api;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class FirstTest {
    private static org.apache.log4j.Logger log = Logger.getLogger(FirstTest.class);

//    @Test
//    public void Test() throws IOException {
//        BasicConfigurator.configure();
//        String url = "https://github.com/";
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//        log.info("Status cod: " + response.getStatusLine().getStatusCode());
//        log.info("Status cod: " + response.getStatusLine().getReasonPhrase());
//        for (Header header : response.getAllHeaders()) {
//            log.info(header.getName() + ":" + header.getValue());
//        }
//
//        HttpEntity entity = response.getEntity();
//        InputStream body = response.getEntity().getContent();
//        DocumentContext docCtx = JsonPath.parse(body);
//        JSONArray jsonArray = docCtx.read("");
//
//    }

    @Test
    public void Test2() throws IOException, URISyntaxException {
        String kharkiv = "kharkiv";

        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https")
                .setHost("www.metaweather.com")
                .setPath("/api/location/search")
                .setParameter("query", kharkiv);
        URI uri = builder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".[0].title");

        log.info("Status cod: " + response.getStatusLine().getStatusCode());
        log.info("Status cod: " + response.getStatusLine().getReasonPhrase());
        for (Header header : response.getAllHeaders()) {
            log.info(header.getName() + ":" + header.getValue());
        }

        log.info("jsonArray: " + jsonArray.toString().toLowerCase());
        log.info("weatherArray: " + kharkiv);
        Assert.assertTrue(jsonArray.toString().toLowerCase().contains(kharkiv));

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test
    public void Test3() throws IOException, URISyntaxException {
        String coordinates = "49.990101,36.230301";

        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https")
                .setHost("www.metaweather.com")
                .setPath("/api/location/search")
                .setParameter("lattlong", coordinates);
        URI uri = builder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".[0].latt_long");

        log.info("Status cod: " + response.getStatusLine().getStatusCode());
        log.info("Status cod: " + response.getStatusLine().getReasonPhrase());
        for (Header header : response.getAllHeaders()) {
            log.info(header.getName() + ":" + header.getValue());
        }

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Assert.assertFalse(response.getEntity().getContent().toString().isEmpty());

        log.info("jsonArray: " + jsonArray.toString());
        log.info("weatherArray: " + coordinates);
        Assert.assertTrue(jsonArray.contains(coordinates));
    }

    @Test
    public void Test4() throws IOException, URISyntaxException {
        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https")
                .setHost("www.metaweather.com")
                .setPath("/api/location/922137");
        URI uri = builder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read("$.consolidated_weather[*].weather_state_name");

        log.info("Status cod: " + response.getStatusLine().getStatusCode());
        log.info("Status cod: " + response.getStatusLine().getReasonPhrase());
        for (Header header : response.getAllHeaders()) {
            log.info(header.getName() + ":" + header.getValue());
        }

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Assert.assertFalse(response.getEntity().getContent().toString().isEmpty());
        String weatherArray = "[\"Snow\",\"Snow\",\"Thunder\",\"Snow\",\"Hail\",\"Heavy Cloud\"]";
        log.info("jsonArray: " + jsonArray.toString());
        log.info("weatherArray: " + weatherArray);
        Assert.assertEquals(weatherArray, jsonArray.toString());
    }

    @Test
    public void Test5() throws IOException, URISyntaxException {
        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("https://reqres.in/api/users");
        String json = "{\n" +
                "    \"name\": \"tata\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".id");

        log.info("Status cod: " + response.getStatusLine().getStatusCode());
        log.info("Status cod: " + response.getStatusLine().getReasonPhrase());
        for (Header header : response.getAllHeaders()) {
            log.info(header.getName() + ":" + header.getValue());
        }

        log.info("Status cod: " + jsonArray.toString());
        String id = jsonArray.toString().replaceAll("[\\[\\]\"]", "");


        Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);
        Assert.assertFalse(response.getEntity().getContent().toString().isEmpty());

        URIBuilder builder = new URIBuilder();
        builder.setScheme("https")
                .setHost("reqres.in")
                .setPath("/api/users/" + id);
        URI uri = builder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse getResponse = httpClient.execute(httpGet);

        InputStream getBody = response.getEntity().getContent();
        DocumentContext docCtx2 = JsonPath.parse(body);
        JSONArray jsonArray2 = docCtx.read("$.data.first_name");

        Assert.assertTrue(jsonArray2.toString().contains("tata"));
    }

    @Test
    public void Test6() throws IOException {
        BasicConfigurator.configure();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("https://reqres.in/api/users");
        String json = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);

        InputStream body = response.getEntity().getContent();
        DocumentContext docCtx = JsonPath.parse(body);
        JSONArray jsonArray = docCtx.read(".token");

        Assert.assertFalse(jsonArray.isEmpty());
    }
}
