package com.dummy;

import com.jayway.restassured.RestAssured;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.jayway.restassured.RestAssured.given;

public class IntegrationTest {

    @Test
    public void should_show_default_view_of_form() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

        given().queryParam("article.title", "__title__")
               .queryParam("article.author.name", "__name__")
        .expect()
               .statusCode(HttpServletResponse.SC_OK)
               .body(shouldContainsNameAndTitle())
        .when().post("/demo/article");
    }

    private Matcher<String> shouldContainsNameAndTitle() {
        return new Matcher<String>() {
            @Override
            public boolean matches(Object item) {
                 return ((String)item).contains("__name__");
            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {}
            @Override
            public void describeTo(Description description) {}
        };
    }
}
