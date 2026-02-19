package Day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.sql.Array;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DifWaysToCreatePostBody {
    private static final Logger log = LoggerFactory.getLogger(DifWaysToCreatePostBody.class);

    // 1) Сохранение данных с HashMap


    @Test
    void testGetUsingHashMap() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/students")
                .then()
                .statusCode(200)
                .body("[0].location", equalTo("India"))
                .body("[1].name", equalTo("Kim"))
                .body("[2].courses", contains("API Testing", "Postman", "SQL"))
                .log().all();
    }


    //Создание объекта POST
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

    // Замена добавленного объекта
    @Test(priority = 2, dependsOnMethods = {"testPostUsingHashMap"})
    public void testPutUsingHashMap() {

        // Парсим тело json чтобы потом получить id
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/students");
        System.out.println(response.getBody().asString());

        String id = response.jsonPath().getString("[-1].id");
        System.out.println(id);

        HashMap data = new HashMap();
        data.put("name", "Muxrifddin");
        data.put("location", "Uzbekistan");
        data.put("phone", 55500233);
        String[] courses = {"Android", "Postman"};
        data.put("courses", courses);


        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .put("http://localhost:3000/students/" + id)
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .header("X-Powered-By", "tinyhttp")
                .header("Access-Control-Allow-Origin", "*")
                .log().all();
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
                .header("Content-Type", "application/json")
                .header("X-Powered-By", "tinyhttp")
                .header("Access-Control-Allow-Origin", "*")
                .log().all();
    }


    // 2) Сохранение данных с org.json library

    //Создание объекта
    @Test(priority = 1)
    public void testPostUsingJson() {

        JSONObject data = new JSONObject();
        data.put("name", "Muxrifddin");
        data.put("location", "Uzbekistan");
        data.put("phone", 555111233);
        String[] courses = {"Android", "Postman"};
        data.put("courses", courses);


        given()
                .contentType(ContentType.JSON)
                .body(data.toString())
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("name", equalTo("Muxrifddin"))
                .body("location", equalTo("Uzbekistan"))
                .body("phone", equalTo(555111233))
                .body("courses", hasItems(courses))
                .log().body();


    }

    // 3) Сохранение данных с POJO class

    //Создание объекта
    @Test(priority = 1)
    public void testPostUsingPOJO() {

        POPJOclass data = new POPJOclass("Petya", "Uzbekistan",21321321, List.of("Android", "Postman"));


        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("name", equalTo("Petya"))
                .body("location", equalTo("Uzbekistan"))
                .body("phone", equalTo(21321321))
                .body("courses", hasItems("Android", "Postman"))
                .log().body();


    }


}
