package Day1;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class HTTPRequests {

    int id;

    //@Test
    void spacexdata() {
        given()
                .when()/**/
                .get("https://jsonplaceholder.typicode.com/comments?postId=2&id=6")
                .then()
                .statusCode(200)
                .body("comments.size()", equalTo(1));
    }

    @Test (priority = 1)
    void createBooking(){

        HashMap<String,Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2022-01-01");

        HashMap<String,Object> map = new HashMap<>();
        map.put("firstname","Jim");
        map.put("lastname","Brown");
        map.put("totalprice", 111);
        map.put("depositpaid",true);
        map.put("additionalneeds","Breakfast");
        map.put("bookingdates",bookingdates);

//        given()
//                .contentType("application/json")
//                .body(map)
//                .when()
//                .post("https://restful-booker.herokuapp.com/booking")
//                .then()
//                .statusCode(200)
//                .log().all();

        id = given()
                .contentType("application/json")
                .accept("application/json")
                .body(map)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .jsonPath().getInt("bookingid");

        System.out.println("booking id is " + id);
        System.out.println();
    }

    @Test (priority = 2, dependsOnMethods = {"createBooking"})
    void updateBooking(){

        HashMap<String,Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2022-01-01");

        HashMap<String,Object> map = new HashMap<>();
        map.put("firstname","Jim");
        map.put("lastname","Brown");
        map.put("totalprice", 111);
        map.put("depositpaid",true);
        map.put("additionalneeds","Breakfast");
        map.put("bookingdates",bookingdates);

        given()
                .contentType("application/json")
                .accept("application/json")
                .body(map)
                .when()
                .put("https://restful-booker.herokuapp.com/booking" + id)
                .then()
                .statusCode(200)
                .log().all();
    }

}

