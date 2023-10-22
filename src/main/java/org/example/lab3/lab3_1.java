package org.example.lab3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class lab3_1 {

    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final String PET = "/pet";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void testGetPet() {
        // Отримати деталі про домашнього улюбленця за його ID
        long petId = 8; // Замініть на реальний ID
        given().get(PET + "/" + petId).then().statusCode(200);
    }

    @Test
    public void testUpdatePet() {
        // Оновити інформацію про домашнього улюбленця
        long petId = 8; // Замініть на реальний ID
        Map<String, ?> body = Map.of(
                "id", petId,
                "name", "Viacheslav",
                "status", "available"
        );

        given().body(body).put(PET).then().statusCode(200);
    }

    @Test
    public void testAddNewPet() {
        // Додати нового домашнього улюбленця
        Map<String, ?> body = Map.of(
                "id", 122232, // Змінна з номером групи і номером студента
                "name", "Sova", // Змінна з ім'ям студента
                "status", "available"
        );

        given().body(body).post(PET).then().statusCode(200);
    }
}
