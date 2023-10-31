package org.example.lab4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Lab4 {
    public static String baseURL = "https://9010ace4-5ddc-4868-b327-88ecd0ce9b6a.mock.pstmn.io/";
    public static String requestURL = "/ownerName";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseURL;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void TestGetMockSuccess() {
        Response response = given()
                .when()
                .get(requestURL)
                .then()
                .statusCode(200)
                .extract().response();
        String responseBody = response.getBody().asString();
        assert responseBody.contains("name\": \"Viacheslav Sova\"");
    }

    @Test
    public void TestGetMockUnsuccess() {
        Response response = given()
                .when()
                .get(requestURL + "/unsuccess")
                .then()
                .statusCode(403)
                .extract().response();
        String responseBody = response.getBody().asString();
        assert responseBody.contains("exception\": \"Exception!\"");
    }

    @Test
    public void TestPostMock200() {
        given()
                .when()
                .post(requestURL)
                .then()
                .statusCode(200)
                .body("result", equalTo("'Whatever item' was created!"));
    }

    @Test
    public void TestPostMock400() {
        given()
                .when()
                .post(requestURL + "/invalid")
                .then()
                .statusCode(400)
                .body("result", equalTo("400 Bad Request."));
    }

    @Test
    public void TestPutMock204() {
        given()
                .when()
                .put(requestURL)
                .then()
                .statusCode(204);
    }

    @Test
    public void TestDeleteMock() {
        given()
                .when()
                .delete(requestURL)
                .then()
                .statusCode(204);
    }
}