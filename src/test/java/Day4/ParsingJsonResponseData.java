package Day4;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

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
                .body("book[4].category", equalTo("dystopian"));

    }

    @Test(priority = 2)
    public void testJsonResponse2() {
        Response res = given()
                .baseUri("http://localhost")
                .port(3000)
                .contentType("application/json")
                .when()
                .get("/store");

        Assert.assertEquals(res.statusCode(), 200); //  валидация статус кода
        System.out.println("statusCode: " + res.statusCode());
        Assert.assertEquals(res.getHeader("Content-Type"), "application/json"); // Валидация хедера
        System.out.println("header: " + res.getHeader("Content-Type"));
        Assert.assertEquals(res.jsonPath().get("book[3].title").toString(), "The Lord of the Rings"); // валидация поля json
        System.out.println("title: " + res.jsonPath().get("book[3].title"));

        JSONObject jo = new JSONObject(res.asString());
        JSONArray books = jo.getJSONArray("book");

        List<String> titles = new ArrayList<>();

        books.forEach(book -> {
            JSONObject bookObj = (JSONObject) book;
            titles.add(bookObj.getString("title"));
        });


        System.out.println(titles);


    }
}
