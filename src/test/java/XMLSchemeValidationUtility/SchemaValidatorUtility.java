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

/*
    Утилитный класс для валидации XML-ответов от API против XSD-схемы.

    Задача: принять HTTP-ответ (Response), извлечь из него нужный XML-элемент
    и проверить соответствие структуре, описанной в XSD-файле.

    Содержит два перегруженных метода validatorXmlSchema:
      - С параметром namespaceURI — для SOAP-ответов, где элементы имеют xmlns-префиксы
      - Без namespaceURI — для простых XML, где элементы без namespace

    Пример использования с namespace (SOAP):
      XmlSchemaValidator.validatorXmlSchema(
          response,
          "src/test/resources/schema.xsd",
          XMLConstants.W3C_XML_SCHEMA_NS_URI,
          "ListOfContinentsByNameResponse",
          "http://www.oorsprong.org/websamples.countryinfo"
      );

    Пример использования без namespace:
      XmlSchemaValidator.validatorXmlSchema(
          response,
          "src/test/resources/schema.xsd",
          XMLConstants.W3C_XML_SCHEMA_NS_URI,
          "return"
      );
*/
public class SchemaValidatorUtility {

    public void validatorXmlSchema(Response response, String xmlSchemaLocation, String schemaLanguage, String rootElementName, String namespaceURI) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        try {
            // Создаём фабрику для построения XML-парсера
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Включаем поддержку namespace (xmlns) — без этой строки getElementsByTagNameNS вернёт null
            factory.setNamespaceAware(true);

            // Создаём сам парсер через фабрику
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Берём тело ответа как строку → StringReader превращает строку в поток символов →
            // InputSource оборачивает поток так, чтобы парсер мог его прочитать →
            // parse() разбирает XML и строит дерево объектов (DOM) в памяти
            Document document = builder.parse(new InputSource(new StringReader(response.getBody().asString())));

            // Проверяем: передан namespace или нет?
            // Если namespaceURI == null или пустая строка → элемент без namespace, ищем просто по имени тега
            // Если namespaceURI указан → элемент с namespace (SOAP), ищем по namespace + имя тега
            // .item(0) — берём первый найденный элемент из списка
            // (Element) — приводим тип: getElementsByTagName возвращает NodeList, нам нужен Element
            Element responseElement = (namespaceURI == null || namespaceURI.isEmpty())
                    ? (Element) document.getElementsByTagName(rootElementName).item(0)
                    : (Element) document.getElementsByTagNameNS(namespaceURI, rootElementName).item(0);

            // Transformer умеет преобразовывать XML-объекты в разные форматы (строка, файл, поток)
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            // StringWriter — буфер в памяти, куда Transformer запишет результат (как StringBuilder)
            StringWriter writer = new StringWriter();

            // DOMSource оборачивает наш Element как источник данных для Transformer
            // StreamResult говорит куда писать результат — в наш StringWriter
            // После этой строки в writer будет XML-строка найденного элемента
            transformer.transform(new DOMSource(responseElement), new StreamResult(writer));

            // Достаём готовую XML-строку из буфера
            String innerXml = writer.toString();

            System.out.println("Извлечённый XML: " + innerXml);

            // SchemaFactory создаёт валидатор для нужного стандарта схем
            // schemaLanguage = "http://www.w3.org/2001/XMLSchema" — говорит что используем XSD (W3C стандарт)
            SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);

            // Загружаем XSD-файл с диска и компилируем его в объект Schema
            Schema schema = schemaFactory.newSchema(new File(xmlSchemaLocation));

            // Создаём валидатор из скомпилированной схемы
            Validator validator = schema.newValidator();

            // Валидируем XML: StreamSource оборачивает нашу строку как источник для валидатора
            // Если XML не соответствует схеме → выбросит SAXException с описанием что не так
            // Если всё ок → молча продолжает выполнение
            validator.validate(new StreamSource(new StringReader(innerXml)));

            System.out.println("XML валиден согласно XSD схеме!");

        }
        catch (ParserConfigurationException e) {
            System.out.println("Ошибка конфигурации XML-парсера: " + e.getMessage());
            throw new RuntimeException("Ошибка конфигурации XML-парсера", e);
        } catch (SAXException e) {
            System.out.println("Ошибка парсинга XML или валидации по XSD: " + e.getMessage());
            throw new RuntimeException("Ошибка парсинга XML или валидации по XSD", e);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла схемы или тела ответа: " + e.getMessage());
            throw new RuntimeException("Ошибка чтения файла схемы или тела ответа", e);
        } catch (TransformerException e) {
            System.out.println("Ошибка при конвертации XML-элемента в строку: " + e.getMessage());
            throw new RuntimeException("Ошибка при конвертации XML-элемента в строку", e);
        } catch (NullPointerException e) {
            System.out.println("Элемент '" + rootElementName + "' не найден в XML-ответе. Проверь имя тега: " + e.getMessage());
            throw new RuntimeException("Элемент '" + rootElementName + "' не найден в XML-ответе", e);
        }


    }
    public static void validatorXmlSchema(Response response, String xmlSchemaLocation, String schemaLanguage, String rootElementName) {

        try {
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

        } catch (ParserConfigurationException e) {
            System.out.println("Ошибка конфигурации XML-парсера: " + e.getMessage());
            throw new RuntimeException("Ошибка конфигурации XML-парсера", e);
        } catch (SAXException e) {
            System.out.println("Ошибка парсинга XML или валидации по XSD: " + e.getMessage());
            throw new RuntimeException("Ошибка парсинга XML или валидации по XSD", e);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла схемы или тела ответа: " + e.getMessage());
            throw new RuntimeException("Ошибка чтения файла схемы или тела ответа", e);
        } catch (TransformerException e) {
            System.out.println("Ошибка при конвертации XML-элемента в строку: " + e.getMessage());
            throw new RuntimeException("Ошибка при конвертации XML-элемента в строку", e);
        } catch (NullPointerException e) {
            System.out.println("Элемент '" + rootElementName + "' не найден в XML-ответе. Проверь имя тега: " + e.getMessage());
            throw new RuntimeException("Элемент '" + rootElementName + "' не найден в XML-ответе", e);
        }
    }
}
