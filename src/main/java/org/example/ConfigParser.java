package org.example;

//import jdk.internal.icu.text.UnicodeSet;
import org.w3c.dom.*;
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

            ArrayList<Node> propertyNodes = new ArrayList<>();
            for (int i = 0; i < propertiesListLength; i++) {
                propertyNodes.add(propertiesList.item(i));
            }

            ArrayList<Node> nodes = getLowestNode(propertyNodes);

            int i = 0;
            while (nodes.get(i).getNodeType() == Node.TEXT_NODE){
                    propertyValues.add(nodes.get(i).getTextContent());
                    i++;
                if (i == nodes.size()){
                    return propertyValues;
                }
            }

            for (Node node : nodes) {
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                Element element = (Element) node;
                NodeList values = element.getChildNodes();
                if (values.getLength() == 1) {
                    propertyValues.add(element.getTextContent().trim());
                    continue;
                }

                if (element.hasAttributes()) {
                    NamedNodeMap attributes = element.getAttributes();
                    for (int j = 0; j < attributes.getLength(); j++) {
//                        String attributeName = attributes.item(j).getNodeName().trim();
                        String attributeValue = attributes.item(j).getTextContent().trim();
                        propertyValues.add(attributeValue);
                    }
                }
                propertyValues.addAll(getValues(values));
//                return propertyValues;
            }

        } catch (ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return propertyValues;
    }

    private static ArrayList<String> getValues(NodeList nodes) {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node value = nodes.item(i);
            if (value.getNodeType() != Node.ELEMENT_NODE) continue;
//                    System.out.println(value.getTextContent().trim());
            values.add(value.getTextContent());
        }
        return values;
    }

    private static ArrayList<Node> getLowestNode(ArrayList<Node> rootNodes) {
        ArrayList<Node> lowestNodes = new ArrayList<>();
        ArrayList<Node> parentNodes = new ArrayList<>();
        for (Node rootNode : rootNodes) {
            String checkString = rootNode.getTextContent().trim();
//            if(!checkString.isEmpty()) lowestNodes.add(rootNode);
            if (rootNode.getNodeType() != Node.ELEMENT_NODE) continue;
            if (!rootNode.hasChildNodes()) {
                lowestNodes.add(rootNode);
                continue;
            }
            NodeList childNodes = rootNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node n = childNodes.item(i);
                if (n.getNodeType() != Node.TEXT_NODE) {
                    if (n.getNodeType() == Node.ELEMENT_NODE) parentNodes.add(n);
                    else continue;
                } else {
                    lowestNodes.add(n);
                }
//                parentNodes.add(n);
            }
        }
        if (parentNodes.isEmpty()) return lowestNodes;
        else return getLowestNode(parentNodes);
    }

}
