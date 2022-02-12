package com.rest.api;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class HWTest {

    @Test
    public void MakeSureThatGoogleWorks() {
        given().when().get("https://www.google.com/").then().statusCode(200);
    }

    @Test
    public void Task1() {
        given().when().get("https://ergast.com/api/f1/2017/circuits.json").then().body("MRData.CircuitTable.Circuits.", hasSize(20));
    }

    @Test
    public void Task2() {
        given().when().get("http://ergast.com/api/f1/2017/circuits.json").then()
                .assertThat().statusCode(200)
                .assertThat().contentType("application/json")
                .assertThat().header("Content-Length", "4551");
    }

    @Test
    public void Task3() {
        given().queryParam("text", "test").when().get("http://md5.jsontest.com/").then().body("original", equalTo("test"));
    }

    @Test
    public void Task4() {
        given().baseUri("https://ergast.com").basePath("/api/f1").when().get("/2016/circuits.json").then().body("MRData.CircuitTable.Circuits.circuitId", hasSize(21));
    }

    @Test
    public void Task5Post() {
        given()
                .baseUri("https://reqres.in/")
                .basePath("/api/users")
                .body("{\n" +
                        "    \"name\": \"tata\",\n" +
                        "    \"job\": \"qa\"\n" +
                        "}")
                .when().post().then().body("id", is(not(empty())));
    }
}
