package Day6;

import XMLSchemeValidationUtility.XmlSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.File;
import java.io.IOException;

import static XMLSchemeValidationUtility.XmlSchemaValidator.validatorXmlSchema;
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

        Response response = given()
                .baseUri("http://webservices.oorsprong.org")
                .header("Content-Type", " application/soap+xml; charset=utf-8")
                .body(requestBody)
                .when()
                .post("websamples.countryinfo/CountryInfoService.wso");

        validatorXmlSchema(
                response,
                "src/test/java/Day6/resourse/countryInfo.xsd",
                XMLConstants.W3C_XML_SCHEMA_NS_URI,
                "http://www.oorsprong.org/websamples.countryinfo",
                "ListOfContinentsByNameResponse"
        );
    }

    @Test
    public void xmlSchemaValidation2() throws ParserConfigurationException, IOException, TransformerException, SAXException {

        Response response = given()
                .baseUri("https://api.asakabank.uz")
                .header("Content-Type", "text/xml; charset=UTF-8")
                .header("device-id", "c840f81b8bd1c268")
                .header("device-name", "Xiaomi 24117RK2CG")
                .header("api-key", "6fe47b9745e3d25f238f15c7693a9603")
                .body(new File("src/test/java/Day6/p2pInfo/request.xml"))
                .when()
                .post("SAPI/MAWS")
                .then()
                .extract().response();

        XmlSchemaValidator.validatorXmlSchema(response, "src/test/java/Day6/p2pInfo/xmlSchena.xsd",
                XMLConstants.W3C_XML_SCHEMA_NS_URI,"return");

    }
}