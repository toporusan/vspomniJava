package Day6;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class ReadXMLFile {

    public static void main(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse("src/test/java/Day6/resourse/CountryInfoService.xml");

            Element root = document.getDocumentElement();
//            NodeList nodeList = root.getChildNodes();
//            System.out.println(nodeList.toString());
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    String name = element.getAttribute("sCode");
//                    System.out.println(name);
//                }
//            }
            NodeList continents = document.getElementsByTagName("m:tContinent");

            for (int i = 0; i < continents.getLength(); i++) {
                Element continent = (Element) continents.item(i);
                String continentName = continent.getTextContent();
                System.out.println(continentName);
            }



        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        ;




    }
}
