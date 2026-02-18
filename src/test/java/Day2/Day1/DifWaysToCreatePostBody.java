package Day2.Day1;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class DifWaysToCreatePostBody {
    private static final Logger log = LoggerFactory.getLogger(DifWaysToCreatePostBody.class);

    // Сохранение данных с HashMap


    //Создание объекта
    @Test(priority = 1)
    public void testPostUsingHashMap() {

        HashMap data = new HashMap();
        data.put("name", "Vasif");
        data.put("location", "Uzbekistan");
        data.put("phone", 555111233);
        String[] courses = {"API Testing", "Postman", "SQL"};
        data.put("courses", courses);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("name", equalTo("Vasif"))
                .body("location", equalTo("Uzbekistan"))
                .body("phone", equalTo(555111233))
                .body("courses", hasItems(courses))
                .log().body();


    }

    // Удаление добавленного объекта
    @Test(priority = 2, dependsOnMethods = {"testPostUsingHashMap"})
    public void testDeleteUsingHashMap() {

        // Парсим тело json чтобы потом получить id
        Response response = given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:3000/students");
        System.out.println(response.getBody().asString());

        String id = response.jsonPath().getString("[-1].id");
        System.out.println(id);

        given()
                .when()
                .delete("http://localhost:3000/students/" + id)
                .then()
                .statusCode(200)
                .log().body();
    }

}
