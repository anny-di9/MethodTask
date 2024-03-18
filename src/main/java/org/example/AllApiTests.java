package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


// ** 2-задание **

public class AllApiTests {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
    }

    //1
    @Test
    public void testUser() {
        /*  чтобы пришел 200 ответ нужно ввести уникальное значение username при каждом новом запросе
         */
        String requestBody = "{\"username\": \"Anny0867990\", \"password\": \"5754\"}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "/register")
                .then()
                .statusCode(201)
                .log().all();
    }

    //2
    @Test
    public void testLogin() {
        String requestBody = "{\"username\": \"string\", \"password\": \"string\"}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "/login")
                .then()
                .statusCode(200)
                .log().all();
    }

    //3
    @Test
    public void getProducts() {
        Response response = given()
                .contentType(ContentType.JSON)
                .request(Method.GET, baseURI + "/products");
        response
                .then().statusCode(200).log().all();

    }

    //4
    @Test
    public void addProduct() {
        String requestBody = "{\"test name\":HP Pavilion Laptop\"\", \"category\":Electronics\"\",\"price\":10.99,\"discount\":10}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .request(Method.POST + baseURI + "/products/1")
                .then()
                .statusCode(404)
                .log().all();
    }

    //5
    @Test
    public void getProductById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .request(Method.GET, baseURI + "/products/1");
        response
                .then().statusCode(200).log().all();
    }

    //6
    @Test
    public void updateProduct() {

        String requestBody = "{ \"name\": \"mobilephone\",\"category\": \"Electronics\",\"price\": 15.99, \"discount\": 10}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .request(Method.PUT + baseURI + "/products/1")
                .then()
                .statusCode(404)
                .log().all();
    }

    //7
    @Test
    public void deleteProducts() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .request(Method.DELETE + baseURI + "/products/4")
                .then()
                .statusCode(404)
                .log().all();
    }

    //8
    @Test
    public void getShoppingCart() {
        Response response = given()
                .contentType(ContentType.JSON)
                .request(Method.GET, baseURI + "/cart");
        response
                .then().statusCode(401).log().all();

    }

    //9
    @Test
    public void addProductToShoppingCart() {
        String requestBody = "{\"product_id\":5, \"quantity\": 13}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURI + "/cart")
                .then()
                .statusCode(401)
                .log().all();
    }

    //10
    @Test
    public void deleteProductsCart() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(baseURI + "/cart/5")
                .then()
                .statusCode(401)
                .log().all();
    }
}