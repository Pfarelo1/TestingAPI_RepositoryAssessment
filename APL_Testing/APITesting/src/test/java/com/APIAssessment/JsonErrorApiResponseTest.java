package com.APIAssessment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class JsonErrorApiResponseTest {

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
