package com.APITest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class basicTest {

    @Test
    public void testGet() {
        RestAssured
                .get("https://www.my-api.com/resource/123")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body("id", equalTo(123));
    }

}
