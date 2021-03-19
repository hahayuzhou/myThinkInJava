package com.thinking.my.xml.mytest;


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @Description
 * @Author liyong
 * @Date 2020/12/10 3:12 下午
 **/
public class MyXMLReader {
    public static void main(String arge[]) {
        try {
            File f = new File("/Users/liyong/Desktop/dag.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            Node fnode =  doc.getFirstChild();
//           NodeList nodeList =  fnode.getChildNodes();
//            int lll =  nodeList.getLength();
//            for (int i = 0; i < lll; i++) {
//                Node node = nodeList.item(i);
//                System.out.println(node.getNodeValue()+"--"+node.getNodeName()+"--"+node.getLocalName());
//                ccc(node);
//            }
//
//            NodeList cnodeList =  fnode.getChildNodes();
//            int clll =  cnodeList.getLength();
//            for (int i = 0; i < clll; i++) {
//                Node node = cnodeList.item(i);
//                System.out.println(node.getNodeValue()+"--"+node.getNodeName()+"--"+node.getLocalName());
//                ccc(node);
//            }

//            ccc(doc,"variable");
            ccc(doc,"block");
//           Element element =  doc.getDocumentElement();
//            System.out.println(element.getTagName());
//            System.out.println(element.getAttribute("variable"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void ccc(Node fnode){
        NodeList cnodeList =  fnode.getChildNodes();
        int clll =  cnodeList.getLength();
        for (int i = 0; i < clll; i++) {
            Node node = cnodeList.item(i);
            System.out.println(node.getNodeValue()+"--"+node.getNodeName()+"--"+node.getLocalName());
        }
    }
    public static void ccc(Document document,String value){
        NodeList cnodeList =  document.getElementsByTagName(value);
        int clll =  cnodeList.getLength();
        for (int i = 0; i < clll; i++) {
            Node node = cnodeList.item(i);
            System.out.println(node.getFirstChild().getNodeValue()+"--"+node.getNodeName());
            NamedNodeMap namedNodeMap = node.getAttributes();
           int ll = namedNodeMap.getLength();
            for (int j = 0; j < ll; j++) {
                Node nnode = namedNodeMap.item(j);
                System.out.println(nnode);
                System.out.println(nnode.getNodeValue()+"--"+nnode.getNodeName());
            }
        }
    }
}


