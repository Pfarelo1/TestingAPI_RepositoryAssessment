package com.APIAssessment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Test_Request {

    @Test
    public void testJsonResponseValidation() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
            .when()
                .get("/users?page=1")
            .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
            .extract()
                .response();

        assertEquals(response.jsonPath().getInt("page"), 1);
        assertEquals(response.jsonPath().getInt("per_page"), 6);
        assertEquals(response.jsonPath().getInt("total"), 12);
        assertEquals(response.jsonPath().getInt("total_pages"), 2);

        assertEquals(response.jsonPath().getString("data[0].email"), "george.bluth@reqres.in");
        assertEquals(response.jsonPath().getString("data[0].first_name"), "George");
        assertEquals(response.jsonPath().getString("data[0].last_name"), "Bluth");
    }

    @Test
    public void testJsonErrorResponseValidation() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
            .when()
                .get("/invalid-endpoint")
            .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
            .extract()
                .response();

        assertEquals(response.jsonPath().getString("error"), "Missing password");
    }
}
