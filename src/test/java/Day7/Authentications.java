package Day7;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
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
                .body("authenticated",equalTo(true))
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
                .body("authenticated",equalTo(true))
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
                .body("authenticated",equalTo(true))
                .log().all();
    }

    @Test
    @DisplayName("Digest basic authentication test ")
    @Order(3)
    public void bearerTokenAuthenticationTest() {

        given()
                .header("Content-Type", "application/json")
                .baseUri("https://postman-echo.com")
                .auth().digest("postman", "password")
                .when()
                .get("/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated",equalTo(true))
                .log().all();
    }


}
