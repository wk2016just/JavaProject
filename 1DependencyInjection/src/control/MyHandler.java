package control;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;


public class MyHandler extends DefaultHandler{
	 
    private String currentTag;
    public ArrayList<ActionMapping> contexts=new ArrayList<ActionMapping>();
    private ArrayList<String> list;
    private HashMap<String, ArrayList<String>> results;
    private ActionMapping actionmapping;
    private int f1=0;
    private String resultName;
    
    
    @Override  
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        currentTag = qName;  
        if("action".equals(currentTag)&&f1==0) {
        	results=new HashMap<String, ArrayList<String>>();
            actionmapping = new ActionMapping(); 
            f1=1;
        }
    }  
  
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {
    
    	if("name".equals(currentTag)&&f1==1) {  
            String name = new String(ch,start,length);  
            actionmapping.setName(name);
            f1=2;
        }else{
        	if("name".equals(currentTag)&&f1==2) {  
        		String url = new String(ch,start,length);  
        		actionmapping.setUrl(url); 
        		f1=3;
        	}else{
        		if("name".equals(currentTag)&&f1==3) {  
                    String name = new String(ch,start,length);  
                    resultName=name;
                }
        	}
        }
        if("method".equals(currentTag)) {  
            String method = new String(ch,start,length);  
            actionmapping.setMethod(method);  
        }  
        if("type".equals(currentTag)) {  
            String type = new String(ch,start,length); 
            list=new ArrayList<String>();
            list.add(type);
        }
        if("value".equals(currentTag)) {  
           String dis = new String(ch,start,length);  
           list.add(dis);
           results.put(resultName, list);
           list=null;
           resultName=null;
        }
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if("action".equals(qName)&&f1!=0) {
        	actionmapping.setResults(results);
            contexts.add(actionmapping);  
            actionmapping = null;  
            results=null;
            f1=0;
        }  
        currentTag = null;  
    }  
  
      
    public ArrayList<ActionMapping> getContexts() {  
        return contexts;  
    }  

}
