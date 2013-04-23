package com.dummy;

import com.jayway.restassured.RestAssured;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.jayway.restassured.RestAssured.given;

public class IntegrationTest {

    @Test
    public void should_show_default_view_of_form() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9090;

        given().queryParam("name", "aaa")
        .expect()
        .statusCode(HttpServletResponse.SC_OK)
        .when().post("/demo");
    }


}
