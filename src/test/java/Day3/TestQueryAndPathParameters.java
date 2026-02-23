package Day3;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class TestQueryAndPathParameters {

    // Квэри не нужно добавлять в путь через скобки https://reqres.in/{mypath1}/{mypath2}?{page}&{per_page}!!!!
    // это Ошибка!, если ты задал уже квери то он и так приплюсуется автоматически .queryParams("page", 1)

    @Test
    public void testQueryAndPathParameters() {

        given()
                .header("Content-Type", ContentType.JSON)
                .header("x-api-key", "reqres_433b3d85e17f48cb94b3fda56bf3f592")
                .pathParams("mypath1", "api")
                .pathParams("mypath2", "users")
                .queryParams("page", 1)
                .queryParams("per_page", 67)
                .when()
                .get("https://reqres.in/{mypath1}/{mypath2}")
                .then()
                .log().body();

    }


}
