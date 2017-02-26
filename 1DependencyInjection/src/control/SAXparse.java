package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SAXparse {
    public static void main(String[] args) throws ParserConfigurationException,  
    SAXException, IOException {  
    	// 创建解析工厂  
    	SAXParserFactory factory = SAXParserFactory.newInstance();  
    	// 创建解析器  
    	SAXParser parser = factory.newSAXParser();  
    	// 得到读取器  
    	XMLReader reader = parser.getXMLReader();  
    	// 设置内容处理器  
    	MyHandler handler = new MyHandler();  
    	reader.setContentHandler(handler);  
    	// 读取xml文档  
    	reader.parse("src/controller.xml");  
    	ArrayList<ActionMapping> list = handler.getContexts();  
    	ActionMapping ctx;  
    	for(int i = 0; i < list.size(); i++) {  
    		ctx = new ActionMapping();  
    		ctx = (ActionMapping) list.get(i);
    		System.out.println(ctx.getResults().get("fail").get(1));  
    	}  

    } 

}
