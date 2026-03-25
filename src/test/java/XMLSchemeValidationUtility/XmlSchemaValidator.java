package XMLSchemeValidationUtility;

import io.restassured.response.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class XmlSchemaValidator {

    public static void validatorXmlSchema(Response response, String xmlSchemaLocation, String schemaLanguage,String rootElementName, String namespaceURI ) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // важно! без этого namespace не распознаётся
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(response.getBody().asString())));

        // ищем нужный элемент: если namespace не указан — ищем по имени тега
        Element responseElement = (namespaceURI == null || namespaceURI.isEmpty())
                ? (Element) document.getElementsByTagName(rootElementName).item(0)
                : (Element) document.getElementsByTagNameNS(namespaceURI, rootElementName).item(0);

        // конвертируем элемент обратно в строку для валидации
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(responseElement), new StreamResult(writer));
        String innerXml = writer.toString();

        System.out.println("Извлечённый XML: " + innerXml);

        // Шаг 3 — загружаем XSD и валидируем
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Schema schema = schemaFactory.newSchema(new File(xmlSchemaLocation));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(innerXml)));

        System.out.println("XML валиден согласно XSD схеме!");
    }
    public static void validatorXmlSchema(Response response, String xmlSchemaLocation, String schemaLanguage,String rootElementName) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // важно! без этого namespace не распознаётся
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(response.getBody().asString())));

        // ищем нужный элемент: если namespace не указан — ищем по имени тега
        Element responseElement = (Element) document.getElementsByTagName(rootElementName).item(0);

        // конвертируем элемент обратно в строку для валидации
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(responseElement), new StreamResult(writer));
        String innerXml = writer.toString();

        System.out.println("Извлечённый XML: " + innerXml);

        // Шаг 3 — загружаем XSD и валидируем
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Schema schema = schemaFactory.newSchema(new File(xmlSchemaLocation));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(innerXml)));

        System.out.println("XML валиден согласно XSD схеме!");
    }
}
