package Day3;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

public class HeadersDemo {

    @Test(priority = 1)
    public void testHeaders() {

        given()
                .when()
                .get("https://www.google.com")
                .then()
                .header("Content-Encoding", "gzip")
                .header("Transfer-Encoding", "chunked");


    }

    @Test(priority = 2)
    public void getHeaders() {

        Response res = given()
                .when()
                .get("https://www.google.com");
        res.getHeaders().forEach(System.out::println);




    }
}

