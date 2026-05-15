package com.APITest;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class basicAPITest {

    @Test
    public void testGet() {
        Response response = given()
           .log().all()
                .baseUri("...")
                .queryParam("k", "v")
                .body("payload")          // set request body here (optional)
                .when()
                .post("...")
                .then()
                .extract().response();
        ResponseBody body = response.getBody();
    }

}
