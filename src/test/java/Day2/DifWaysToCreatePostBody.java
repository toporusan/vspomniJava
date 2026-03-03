package Day2;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class DifWaysToCreatePostBody {

    Faker faker = new Faker();


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
                .log().all();
    }


    //Создание объекта POST
    @Test(priority = 1)
    public void testPostUsingHashMap() {

        Map<String, Object> data = new HashMap<>();
        data.put("name", "Vasif");
        data.put("location", "Uzbekistan");
        data.put("phone", "555111233");
        List<String> courses = List.of("API Testing", "Postman", "SQL");
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
                .body("phone", equalTo("555111233"))
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

        Map<String, Object> data = new HashMap<>();
        data.put("name", "Muxrifddin");
        data.put("location", "Uzbekistan");
        data.put("phone", "55500233");
        List<String> courses = List.of("Android", "Postman");
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
        data.put("phone", "555111233");
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
                .body("phone", equalTo("555111233"))
                .body("courses", hasItems(courses))
                .log().body();


    }

    // 3) Сохранение данных с POJO class

    //Создание объекта
    @Test(priority = 1)
    public void testPostUsingPOJO() {
        String name = faker.name().firstName();
        String phone = faker.phoneNumber().phoneNumber();
        String location = faker.country().name();

        POJOclass data = new POJOclass(name, location, phone, List.of("Android", "Postman"));


        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .log().body();
    }


    // 4) Сохранение данных с JSON file

    //Создание объекта
    @Test(priority = 1)
    public void testPostUsingJsonFile() {

        File f = new java.io.File("src/test/java/Day2/body.json");
        FileReader fr = null;
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);


        given()
                .contentType(ContentType.JSON)
                .body(data.toString())
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .log().body();
    }


}
