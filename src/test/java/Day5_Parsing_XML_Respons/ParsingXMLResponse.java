package Day5_Parsing_XML_Respons;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class ParsingXMLResponse {

    @Test
    public void testXMLResponse() {

        /*        String soapBody = """
                <?xml version="1.0" encoding="utf-8"?>
                <soap12:Envelope xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                  <soap12:Body>
                    <tns:ListOfCurrenciesByName xmlns:tns="http://www.oorsprong.org/websamples.countryinfo"/>
                  </soap12:Body>
                </soap12:Envelope>
                """;*/ //  можно запрос сохранить в файл а затем его использовать

        //  можно вытягивать иксмл запрос из файла
        String soapBody = "";
        try {
            soapBody = Files.readString(
                    Paths.get("src/test/resources/XMLRequests/ListOfContinentsByName.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // SOAP CountryInfoService  в постмане коллекция

        Response res = given()
                .contentType("application/soap+xml; charset=utf-8")
                .baseUri("http://webservices.oorsprong.org")
                .body(soapBody)
                .when()
                .post("/websamples.countryinfo/CountryInfoService.wso")
                .then()
                .extract().response();

//        System.out.println(res.headers().toString());
//        System.out.println();
        System.out.println(res.asString());
//        Assert.assertEquals(res.contentType(), "application/soap+xml; charset=utf-8");

        // Найти и свалидировать какие-нибудь данные без сохранения респонса
        /* local-name() — это функция XPath, которая возвращает:
        имя тега без namespace, нужна, для того чтобы игнорировать неймспейсы */

        List<String> sCode = res.xmlPath()
                .getList("**.findAll { it.name() == 'sCode' }*.text()");
        List<String> sName = res.xmlPath()
                .getList("**.findAll { it.name() == 'sName' }*.text()");


        System.out.println(sCode);
        System.out.println(sName);

    }
}
