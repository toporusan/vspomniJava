package Day7;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.and;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Authentications {

    // Basic Auth — отправляет логин:пароль в Base64 в заголовке Authorization.
    // Ждёт ответа 401 от сервера, только потом отправляет credentials (два запроса).
    // Минус: пароль легко декодировать — безопасно только по HTTPS.
    @Test()
    @Order(1)
    @DisplayName("Basic authentication test ")
    public void basicAuthenticationTest() {

        given()
                .header("Content-Type", "application/json")
                .baseUri("https://postman-echo.com")
                .auth().basic("postman", "password")
                .when()
                .get("/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();

    }

    // Preemptive Basic Auth — то же самое что Basic, но отправляет credentials сразу,
    // не дожидаясь ответа 401. Быстрее (один запрос вместо двух).
    // Используй когда точно знаешь что сервер требует Basic Auth.
    @Test
    @DisplayName("Preemptive basic authentication test ")
    @Order(2)
    public void preemtiveBasicAuthenticationTest() {

        given()
                .header("Content-Type", "application/json")
                .baseUri("https://postman-echo.com")
                .auth().preemptive().basic("postman", "password")
                .when()
                .get("/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();
    }

    // Digest Auth — более безопасная альтернатива Basic.
    // Пароль не передаётся в открытом виде — вместо него отправляется хэш (MD5).
    // Сервер присылает случайное число (nonce), клиент хэширует пароль + nonce → не перехватить.
    @Test
    @DisplayName("Digest basic authentication test ")
    @Order(3)
    public void digestBasicAuthenticationTest() {

        given()
                .header("Content-Type", "application/json")
                .baseUri("https://postman-echo.com")
                .auth().digest("postman", "password")
                .when()
                .get("/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();
    }

    @Test
    @DisplayName("Bearer token authentication test")
    @Order(4)
    public void bearerTokenAuthenticationTest() {

        String body = """
                {
                  "username": "emilys",
                  "password": "emilyspass"
                }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri("https://dummyjson.com")
                .body(body)
                .when()
                .post("/auth/login")
                .then().extract().response();

        String accessToken = response.jsonPath().getString("accessToken");

        assertThat(response.statusCode()).as("Status code note 200").isEqualTo(200);
        assertThat(response.getTime()).as("Get time is not less than 3000").isLessThan(3000);
        assertThat(response.getBody().asString()).as("The body is empty").isNotEmpty();


        Response response1 = given()
                .header("Content-Type", "application/json")
                .baseUri("https://dummyjson.com")
                .auth().oauth2(accessToken)
                .body(body)
                .when()
                .get("/auth/me")
                .then()
                .extract().response();

        assertThat(response1.statusCode()).as("Status code note 200").isEqualTo(200);
        assertThat(response1.getTime()).as("Get time is not less than 3000").isLessThan(3000);
        assertThat(response1.getBody().asString()).as("The body is empty").isNotEmpty();
        assertThat(response1.jsonPath().getString("firstName")).as("The name is not Emily").isEqualTo("Emily");
        assertThat(response1.jsonPath().getString("crypto.coin")).as("The crypto is not Bitcoin").isEqualTo("Bitcoin");
        assertThat(response1.jsonPath().getString("crypto.coin")).as("The crypto is not Bitcoin").hasSize(7);

        int size = response1.jsonPath().getString("crypto.coin").length();
        System.out.println(size);

        assertThat(response1.jsonPath().getString("address.postalCode")).as("The crypto is not Bitcoin").containsOnlyDigits();

    }


}


