package Day1;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class HTTPRequests {

    @Test
    void spacexdata() {
        given()
                .when()/**/
                .get("https://jsonplaceholder.typicode.com/comments?postId=2&id=6")
                .then()
                .statusCode(200)
                .log().all();

    }
}

