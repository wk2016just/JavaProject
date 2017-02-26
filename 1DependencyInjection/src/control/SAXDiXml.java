package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.security.action.GetBooleanAction;

public class SAXDiXml extends DefaultHandler{

	//����dependency-mapping�б��������dibean��������properties
    public DependencyMapping dimapping=new DependencyMapping();
    public HashMap<String, DIBean> allbean=new HashMap<>();
    public DIBean dibean;
    
    public HashMap<String, Property> properties;
    private Property property;
    
    
    private String key;
    private String currentTag =null;
    private int level=0;
    
    @Override  
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        currentTag = qName;

        if("bean".equals(currentTag)) {
        	dibean=new DIBean();
        	properties=new HashMap<>();
            level=1;
        }
        if("property".equals(currentTag)) { 
            property=new Property();
        }
    }  
  
    @Override  
    public void characters(char[] ch, int start, int length) throws SAXException {
    	
    	//��ǰtagΪname��Ϊaction��name��levelΪ1
    	if("name".equals(currentTag)&&level==1) {  
            key = new String(ch,start,length);
            dibean.setName(key);
            level=2;
        }else{
        	//����actron����levelΪ2��property��name����
        	if("name".equals(currentTag)&&level==2){
        		String value=new String(ch,start,length);
        		property.setName(value);
        	}
        }
    	
    	//����������Ϊproperty��ref���ԣ�����֮��action�Ƿ��������ļ��
    	if("ref-class".equals(currentTag)&&level==2){
    		String value=new String(ch,start,length);
    		property.setRef(value);
    	}
    	if("class".equals(currentTag)){
    		String value=new String(ch,start,length);
    		dibean.setUrl(value);
    	}
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {
    	
    	if("property".equals(qName)){
         	properties.put(property.getName(),property);
        }
    	//���Ϊbean��level��0
        if("bean".equals(qName)) {
        	dibean.setProperties(properties);
        	allbean.put(key, dibean);
        	dimapping.setDependencyMapping(allbean);
            level=0;
        }
        currentTag = null;  
    }  
  
    public DependencyMapping getDependencyMapping(){
    	return this.dimapping;
    }
    
    
    public DependencyMapping SAXparser() throws ParserConfigurationException, SAXException, IOException{
    	SAXParserFactory factory = SAXParserFactory.newInstance();  
    	SAXParser parser = factory.newSAXParser();  
    	XMLReader reader = parser.getXMLReader();  
    	SAXDiXml handler = new SAXDiXml();  
    	reader.setContentHandler(handler);  
    	reader.parse("src/di.xml");
    	DependencyMapping dm=handler.getDependencyMapping();
    	
    	return dm;
    	
    }

    

}
