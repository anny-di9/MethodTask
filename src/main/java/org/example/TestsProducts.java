package org.example;


import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.example.Url.BASE_URI;
import static org.hamcrest.Matchers.startsWith;


public class TestsProducts {

    // получить весь список товаров +
    @Test
    public void getProducts() {
        Response response = RestApiSpec.get(BASE_URI + "/products");
        response.then().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .log().all();
    }


    //поиск по существующему id продукта
    @Test
    public void getProductId() {
        Response response = RestApiSpec.get(BASE_URI + "/products/1");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .statusCode(200).log().all();

    }

    //поиск по существующему id продукта
    @Test
    public void getProductNullId() {
        Response response = RestApiSpec.get(BASE_URI + "/products/0");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .statusCode(200).log().all();

    }

    @Test
    public void getProductIdSymbol() {
        Response response = RestApiSpec.get(BASE_URI + "/products/$");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .statusCode(200).log().all();

    }

    //поиск по неуществующему id продукта
    @Test
    public void getProductIdNotFound() {
        Response response = RestApiSpec.get(BASE_URI + "/products/20");
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .statusCode(404).log().all();

    }

    //получить описание продукта по имени c символом
    @Test
    public void getProductByName() {
        String name = "MobileT2547gg";
        Response response = RestApiSpec.get(BASE_URI + "/products" + name);
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .statusCode(404).log().all();
    }


    //добавление продукта запрещено
    @Test
    public void addProductNotAllowed() {
        Object requestBody = new Products("new add product", "electrics", 20, 10);
        Response response = RestApiSpec.post(BASE_URI + "/products", requestBody);
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .statusCode(405).log().all();
    }

    //добавление нового типа товара - ожидается ошибка
    @Test
    public void addNewProduct() {
        Object requestBody = new Products("Bag", "Clothes", 200, 9);
        Response response = RestApiSpec.post(BASE_URI + "/products", requestBody);
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetProductsResponse.json"))
                .statusCode(201).log().all();
    }


    //обновление информации о товаре не разрешается
    @Test
    public void updateInfoProduct() {
        Object requestBody = new Products("Updated Product Name", "Electronics", 15.99, 8);
        Response response = RestApiSpec.put(BASE_URI + "/products/1", requestBody);
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .statusCode(405).log().all();

    }

    //поиск по id=22 - кейс выдает 405 ошибку, хотя ожидалось 404
    @Test
    public void updateInfoProductNeg() {
        Object requestBody = new Products("Updated Product Name", "Electronics", 15.99, 8);
        Response response = RestApiSpec.put(BASE_URI + "/products/22", requestBody);
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .statusCode(404).log().all();

    }

    //продукт с id=1 должен быть удален, в ОР получена ошибка 405
    @Test
    public void deleteProductById() {
        Response response = RestApiSpec.delete(BASE_URI + "/products/1");
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .statusCode(200).log().all();

    }

    // удаление продукта с id=1 не разрешается, проверка заголовка
    @Test
    public void deleteProductNotAllowed() {
        Response response = RestApiSpec.delete(BASE_URI + "/products/1");
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .statusCode(405).log().all();

    }

    // при удалении продукта с id=100 ошибка 404 не появляется
    @Test
    public void deleteProductNotFound() {
        Response response = RestApiSpec.delete(BASE_URI + "/products/100");
        response.then()
                .header("Content-Type", startsWith("text/html"))
                .statusCode(404).log().all();

    }
}