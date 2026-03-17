package Day6;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;

import static io.restassured.RestAssured.given;


public class XMLSchemaValidation {

    @Test
    public void xmlSchemaValidation() throws Exception {

        String requestBody = """
                <?xml version="1.0" encoding="utf-8"?>
                <soap12:Envelope xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                  <soap12:Body>
                    <tns:ListOfContinentsByName xmlns:tns="http://www.oorsprong.org/websamples.countryinfo"/>
                  </soap12:Body>
                </soap12:Envelope>
                """;

        // Шаг 1 — отправляем запрос и получаем ответ
        Response response = given()
                .baseUri("http://webservices.oorsprong.org")
                .header("Content-Type", " application/soap+xml; charset=utf-8")
                .body(requestBody)
                .when()
                .post("websamples.countryinfo/CountryInfoService.wso");

        System.out.println(response.getHeaders());
        System.out.println(response.getBody().asString());



    }
}