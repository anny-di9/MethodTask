package org.example;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.example.Url.BASE_URI;

public class TestsCart {

    // получить весь список cart
    @Test
    public void getAllCart() {
        Response response = RestApiSpec.get(BASE_URI + "/cart");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(200).log().all();
    }

    // Missing Authorization Header
    @Test
    public void getCart() {
        Response response = RestApiSpec.get(BASE_URI + "/cart");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(401).log().all();
    }

    // msg": "Missing Authorization Header
    @Test
    public void addCartNotAllowed() {
        Object requestBody = new SetCartDescription(1, 23);
        Response response = RestApiSpec.post(BASE_URI + "/cart", requestBody);
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(401).log().all();
    }

    // Product added to cart successfully with null - не пройден
    @Test
    public void addCartNew() {
        Object requestBody = new SetCartDescription(0, 0);
        Response response = RestApiSpec.post(BASE_URI + "/cart", requestBody);
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(201).log().all();
    }

    // удаление продукта с id=1 не разрешается
    @Test
    public void deleteCartNotAllowed() {
        Response response = RestApiSpec.delete(BASE_URI + "/cart/1");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(401).log().all();
    }

    // при удалении продукта с id=25 ошибка 404 не появляется
    @Test
    public void deleteCartNotFound() {
        Response response = RestApiSpec.delete(BASE_URI + "/cart/25");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(404).log().all();
    }

    //Product removed from cart successfully - не пройден
    @Test
    public void deleteCart() {
        Response response = RestApiSpec.delete(BASE_URI + "/cart/2");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(200).log().all();
    }
}