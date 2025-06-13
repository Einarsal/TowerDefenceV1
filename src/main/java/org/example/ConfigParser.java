package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ConfigParser {

    public static ArrayList<String> getProperty(String fileURL, String propertyTag) {
        ArrayList<String> propertyValues = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fileURL);

            NodeList propertiesList = doc.getElementsByTagName(propertyTag);
            int propertiesListLength = propertiesList.getLength();
//            if (propertiesListLength == 1) {
//                propertyValues.add(propertiesList.item(0).getTextContent());
//                return propertyValues;
//            }

            for (int i = 0; i < propertiesListLength; i++) {
                Node node = propertiesList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    return propertyValues;
                }

                Element property = (Element) node;
                NodeList values = property.getChildNodes();
                if (values.getLength() == 1) {
                    propertyValues.add(property.getTextContent().trim());
                    return propertyValues;
                }
                System.out.println(values.getLength());
                for (int j = 0; j < values.getLength(); j++) {
                    Node value = values.item(j);
                    if (value.getNodeType() != Node.ELEMENT_NODE) continue;
                    System.out.println(value.getTextContent().trim());
                    propertyValues.add(value.getTextContent());
                }
            }
        } catch (ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        return propertyValues;
    }
}
