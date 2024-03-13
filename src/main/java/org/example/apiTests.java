package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class apiTests {

    @Test
    public void testUser() {
        /*  чтобы пришел 200 ответ нужно ввести уникальное значение username при каждом новом запросе */
        String requestBody = "{\"username\": \"AnnyDi\", \"password\": \"2612\"}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://9b142cdd34e.vps.myjino.ru:49268/register")
                .then()
                .statusCode(201)
                .log().all();
    }


    @Test
    public void testLogin() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
        String requestBody = "{\"username\": \"string\", \"password\": \"string\"}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void getProduct() {
        Response response = RestAssured.given()
                .get("http://9b142cdd34e.vps.myjino.ru:49268/products");
        response
                .then()
                .statusCode(201)
                .log().all();

    }

    @Test
    public void addProduct() {
        String requestBody = "{\"name\":HP Pavilion Laptop\"\", \"category\":Electronics\"\",\"price\":10.99,\"discount\":10}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://9b142cdd34e.vps.myjino.ru:49268/products")
                .then()
                .log().all()
                .extract().response();

    }

    @Test
    public void getProductId() {
        Response response = RestAssured.given()
                .get("http://9b142cdd34e.vps.myjino.ru:49268/products");
        response
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void updateProduct() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
        String requestBody = "{ \"name\": \"mobilephone\",\"category\": \"Electronics\",\"price\": 15.99, \"discount\": 10}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/products/5")
                .then()
                .log().all()
                .extract().response();


    }

    @Test
    public void deleteProducts() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("http://9b142cdd34e.vps.myjino.ru:49268/products/4")
                .then()
                .log().all()
                .extract().response();
    }
}