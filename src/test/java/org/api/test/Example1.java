package org.api.test;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;

public class Example1 {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void shouldReturnAllUsers() {
                given().log().all().
                    contentType(JSON).
                when().
                    get("/api/users").
                then().
                    statusCode(200).
                        body("data[0].id", CoreMatchers.equalTo(1)).
                        body("data.first_name", hasItems("George", "Janet"));
    }

    @Test
    public void shouldReturnASingleUser() {
        given().log().all().
                    contentType(JSON).
                when().
                    get("/api/users/1").
                then().
                    statusCode(200).
                    body("data.first_name", CoreMatchers.equalTo("George"));
    }

    @Test
    public void shouldCreateANewUser() {
        JSONObject object = new JSONObject();
        object.put("name", "zotho");
        object.put("job", "qa engineer");

        given().log().all().
                    contentType(JSON).
                    body(object.toJSONString()).
                when().
                    post("/api/users/").
                then().
                    statusCode(201).
                    body("name", CoreMatchers.equalTo("zotho"));
    }

    @Test
    public void shouldUpdateANewUser() {
        JSONObject object = new JSONObject();
        object.put("name", "zethe");
        object.put("job", "sdet");

        given().log().all().
                    contentType(JSON).
                    body(object.toJSONString()).
                when().
                    put("/api/users/").
                then().
                    statusCode(200).
                    body("job", CoreMatchers.equalTo("sdet"));
    }

    @Test
    public void shouldDeleteASingleUser() {
        given().log().all().
                    contentType(JSON).
                when().
                    delete("/api/users/1").
                then().
                    statusCode(204);
    }
}
