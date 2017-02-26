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
    	// ������������  
    	SAXParserFactory factory = SAXParserFactory.newInstance();  
    	// ����������  
    	SAXParser parser = factory.newSAXParser();  
    	// �õ���ȡ��  
    	XMLReader reader = parser.getXMLReader();  
    	// �������ݴ�����  
    	MyHandler handler = new MyHandler();  
    	reader.setContentHandler(handler);  
    	// ��ȡxml�ĵ�  
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
