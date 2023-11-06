import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlString {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xmlString = "<root><tag1>value1</tag1><tag2>value2</tag2></root>";
        InputStream stream = new ByteArrayInputStream(xmlString.getBytes());
        Document doc = builder.parse(stream);

        Element rootElement = doc.getDocumentElement();
        NodeList tag2List = rootElement.getElementsByTagName("tag2");
        Element tag2Element = (Element) tag2List.item(0);
        String tag2Value = tag2Element.getTextContent();
        System.out.println(tag2Value); // Output: value2

    }
}
