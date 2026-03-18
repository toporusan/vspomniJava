package Day6;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

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

        //System.out.println(response.getBody().asString());

        // Шаг 2 — парсим ответ и извлекаем ListOfContinentsByNameResponse из SOAP-обёртки
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // важно! без этого namespace не распознаётся
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(response.getBody().asString())));

        // ищем нужный элемент по namespace и имени тега
        Element responseElement = (Element) document.getElementsByTagNameNS(
                "http://www.oorsprong.org/websamples.countryinfo",
                "ListOfContinentsByNameResponse"
        ).item(0);

        // конвертируем элемент обратно в строку для валидации
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(responseElement), new StreamResult(writer));
        String innerXml = writer.toString();

        System.out.println("Извлечённый XML: " + innerXml);

        // Шаг 3 — загружаем XSD и валидируем
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("src/test/java/Day6/resourse/countryInfo.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(innerXml)));

        System.out.println("XML валиден согласно XSD схеме!");
    }


}