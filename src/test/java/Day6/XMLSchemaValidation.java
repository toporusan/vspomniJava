package Day6;

import io.restassured.config.RestAssuredConfig;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class XMLSchemaValidation {

    @Test
    public void xmlSchemaValidation() {

        String body = """
                <?xml version="1.0" encoding="utf-8"?>
                <soap12:Envelope xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                  <soap12:Body>
                    <tns:ListOfContinentsByName xmlns:tns="http://www.oorsprong.org/websamples.countryinfo"/>
                  </soap12:Body>
                </soap12:Envelope>
                """;

        given()
                .baseUri("http://webservices.oorsprong.org")
                .header("Content-Type", "text/xml; charset=utf-8")
                .body(body)
                .when()
                .post("/websamples.countryinfo/CountryInfoService.wso")
                .then()
                .assertThat().statusCode(200)
                //.log().body();
                .body("Envelope.Body.ListOfContinentsByNameResponse",
                        matchesXsdInClasspath("continents.xsd"));

        // .body(RestAssuredMatchers.matchesXsd( new File("src/test/java/Day6/country.xsd")));
    }
}
