package com.poo0054.dtd;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZhangZhi
 * @version 1.0
 * @since 2022/7/19 15:35
 */
public class DTDTest {


    @Test
    public void ValidTest() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("dtd/test.xml");
        documentBuilder.setEntityResolver((publicId, systemId) -> {
            //获取名称
            return new InputSource(classLoader.getResourceAsStream("dtd/" + new File(systemId).getName()));
        });
        Document parse = documentBuilder.parse(resourceAsStream);
        Element documentElement = parse.getDocumentElement();
        NodeList childNodes = documentElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                String textContent = node.getTextContent();
                String value = ((DeferredElementImpl) node).getAttribute("value");
                System.out.println(nodeName + "\t" + textContent);
                System.out.println("value--->" + "\t" + value);
            }
        }
    }


}
