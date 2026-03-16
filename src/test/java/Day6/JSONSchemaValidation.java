package Day6;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class JSONSchemaValidation {

    @Test
    public void jsonSchemaValidation() {

        String body = """
                {
                  "username": "ivan_petrov",
                  "firstName": "Ivan",
                  "lastName": "Petrov",
                  "email": "ivan.petrov@example.com",
                  "password": "P@ssw0rd123",
                  "phone": "+79161234567",
                  "userStatus": 1
                }
                """;

        given()
                .baseUri("https://petstore.swagger.io/v2")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/user")
                .then()
                .assertThat().statusCode(200)
                .log().body();


        given()
                .baseUri("https://petstore.swagger.io/v2")
                .header("Content-Type", "application/json")
                .when()
                .get("/user/ivan_petrov")
                .then()
                .assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/Day6/resourse/usersSchema.json")))
                .log().body();

    }


}
