import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RestApiSpec {

    private static RequestSpecification requestSpecification = RestAssured.given();

    public static Response get(String uri) {

        return requestSpecification.contentType(ContentType.JSON).get(uri);
    }

    public static Response post(String uri, Object body) {
        return requestSpecification.contentType(ContentType.JSON).body(body).post(uri);
    }

    public static Response put(String uri, Object body) {
        return requestSpecification.contentType(ContentType.JSON).body(body).put(uri);
    }

    public static Response delete(String uri) {
        return requestSpecification.contentType(ContentType.JSON).delete(uri);

    }
}
