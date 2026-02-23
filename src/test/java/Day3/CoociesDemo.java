package Day3;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class CoociesDemo {


    @Test(priority = 1)
    public void coocieDemo() {

        given()
                .when()
                .get("https://www.google.com ")
                .then()
                .cookie("AEC", "AaJma5sbxHxqFZpPQq-ZFYyxjno2TDXfnEB4T5vRmVV55kRj6PU3m7u4wQ")
                .log().all();
    }

    @Test(priority = 2)
    public void GetCoocieInfo() {

        Response res = given()
                .when()
                .get("https://www.google.com ");

        String cookie = res.getCookie("AEC");
        System.out.println(cookie);

        System.out.println();

        Map<String, String > cookies = res.getCookies();
        for (String key : cookies.keySet()) {
            System.out.println(key + " : " + cookies.get(key));
        }

        System.out.println(cookies.keySet());



    }
}
