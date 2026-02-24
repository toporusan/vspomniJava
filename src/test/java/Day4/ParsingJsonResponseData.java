package Day4;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ParsingJsonResponseData {

    @Test(priority = 1)
    public void testJsonResponse() {
        given()
                .contentType("application/json")
                .when()
                .get("http://localhost:3000/store")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("book[1].category", equalTo("fiction"))
                .log().all();

    }
}
