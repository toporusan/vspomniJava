package Day6;

import SchemeValidationUtility.SchemaValidatorUtility;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import java.io.File;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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


        Response response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .header("Content-Type", "application/json")
                .when()
                .get("/user/ivan_petrov")
                .then()
                .extract().response();

        response.then().log().body();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("username")).isEqualTo("ivan_petrov");
        SchemaValidatorUtility.JSONSchemavalidator(response, "src/test/java/Day6/resourse/usersSchema.json");
    }

    @Test
    void name() {
    }
}
