import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.example.Url.Url.BASE_URI;
import static org.hamcrest.Matchers.equalTo;

public class CartTests {

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTcxMTI4NTU5OSwianRpIjoiYWMxMjJhODUtMGIyNi00Yjc4LWFlMjAtZDcyN2Q2N2QyOGE1IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6ImFubnlkaTkiLCJuYmYiOjE3MTEyODU1OTksImNzcmYiOiI3MTU5NGY1NC1mNWQwLTQwMjctYTRjMC1iM2U2YWU4YjA5ZTEiLCJleHAiOjE3MTEyODY0OTl9.LykSa-ciqFLecqAWTg3O0m3hlPCUjgz2ksFcShfqBLw";

    // Передача токена - получена ошибка   "message": "Cart not found"
    @Test
    public void GetCartTest() {

        Header authHeader = new Header("Authorization", "Bearer " + JWT_TOKEN);
        given().header(authHeader).and().expect().body(equalTo(JWT_TOKEN))
                .when().get(BASE_URI + "/cart");
        Response response =
                (Response) given().contentType(ContentType.JSON);
        response.then()
                .statusCode(404).log().all();
    }


    // Product added to cart successfully with null
    @Test
    public void AddCartNewTest() {
        Object requestBody = new SetCartDescription(19, 10);
        Header authHeader = new Header("Authorization", "Bearer " + JWT_TOKEN);
        given().header(authHeader).body(requestBody)
                .and().expect().body(equalTo(JWT_TOKEN))
                .when().post(BASE_URI + "/cart");
        Response response =
                (Response) given().contentType(ContentType.JSON);
        response.then()
                .statusCode(200).log().all();

    }

    // удаление продукта с id=1 не разрешается
    @Test
    public void DeleteCartNotAllowedTest() {

        Header authHeader = new Header("Authorization", "Bearer " + JWT_TOKEN);
        given().header(authHeader)
                .and().expect().body(equalTo(JWT_TOKEN))
                .when().delete(BASE_URI + "/cart/1");
        Response response =
                (Response) given().contentType(ContentType.JSON);
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(200).log().all();
    }

    // при удалении продукта с id=19
    @Test
    public void DeleteCartNotFoundTest() {
        Header authHeader = new Header("Authorization", "Bearer " + JWT_TOKEN);
        given().header(authHeader)
                .and().expect().body(equalTo(JWT_TOKEN))
                .when().delete(BASE_URI + "/cart/19");
        Response response =
                (Response) given().contentType(ContentType.JSON);
        response.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CartResponse.json"))
                .statusCode(200).log().all();
    }

    //Product removed from cart successfully
    @Test
    public void DeleteCartTest() {
        Header authHeader = new Header("Authorization", "Bearer " + JWT_TOKEN);
        given().header(authHeader)
                .and().expect().body(equalTo(JWT_TOKEN))
                .when().delete(BASE_URI + "/cart/1");
        Response response =
                (Response) given().contentType(ContentType.JSON);
        response.then()
                .statusCode(200).log().all();
    }
}