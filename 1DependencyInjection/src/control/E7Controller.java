package control;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;





public class E7Controller extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String uri=req.getRequestURI();
		
		String[] a=uri.split("/");
		String[] b=a[2].split("\\.");
		String name=b[0];
		
		ActionMapping actionmapping = null;
		DependencyMapping dimapping=null;
		DIBean dibean=null;
		
		try {
			actionmapping=SAXparse(name);
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		try {
			//SAX解析beanmap，通过传入SearchDi中查询是否含有dibean
			dimapping=new SAXDiXml().SAXparser();
			dibean = SearchDi(dimapping, actionmapping.getUrl());
		} catch (ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		}
		
		
		if(dibean == null){
		try {
			try {
				DerictAction(req,resp,actionmapping);
			} catch (NoSuchFieldException | SecurityException | InstantiationException | IllegalAccessException
					| NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {

				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		}
		else{
			try {
				
				Class<?> c = Class.forName(actionmapping.getUrl());
				//在DependencyInjection()方法中注入所需要的ref bean
				Object o = new DependencySearch(dimapping, dibean).DependencyInjection();
				//通过反射传入request，Response参数给action执行
				Method m = c.getDeclaredMethod(actionmapping.getMethod(),new Class[]{HttpServletRequest.class,HttpServletResponse.class}); 
				String result=(String) m.invoke(o,req,resp);
				
				HashMap<?, ?> results=actionmapping.getResults();
				Iterator<?> iter = results.entrySet().iterator();
				
				//result与结果集中resultstring匹配，相同则进行返回给client
				while (iter.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iter.next();
			    String resultstring = (String) entry.getKey();
				if(result.equals(resultstring)){
					@SuppressWarnings("unchecked")
					ArrayList<String> list=(ArrayList<String>) results.get(resultstring);
					if(list.get(0).equals("forward")){
						req.getRequestDispatcher(list.get(1)).forward(req, resp);;
					}else{
						if(list.get(0).equals("redirect")){
							((HttpServletResponse) req).sendRedirect(list.get(1));
						}
					}
				}

				}
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	private ActionMapping SAXparse(String name) throws ParserConfigurationException, SAXException, IOException{
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
    	reader.parse("C://Users/Administrator/Desktop/java practice/LConConfig/src/controller.xml");  
    	ArrayList<ActionMapping> list = handler.getContexts();  
    	ActionMapping actionmapping=new ActionMapping();  
    	for(int i = 0; i < list.size(); i++) {  
    		if(list.get(i).getName().equals(name)){
    		actionmapping = list.get(i);
    		}
    	}
    	return actionmapping;
	}
	
	private void DerictAction(HttpServletRequest req,HttpServletResponse resp,ActionMapping actionmapping) throws ClassNotFoundException, 
																																							NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, IOException, ServletException{
		Class<?> clazz = Class.forName(actionmapping.getUrl());
		Object obj = clazz.newInstance();
 
		Method m = clazz.getDeclaredMethod(actionmapping.getMethod(),new Class[]{HttpServletRequest.class,HttpServletResponse.class}); 
		String result=(String) m.invoke(obj,req,resp);
		 
		HashMap<?, ?> results=actionmapping.getResults();
		Iterator<?> iter = results.entrySet().iterator();

		while (iter.hasNext()) {

		@SuppressWarnings("rawtypes")
		Map.Entry entry = (Map.Entry) iter.next();
	    String key = (String) entry.getKey();
		if(result.equals(key)){
			@SuppressWarnings("unchecked")
			ArrayList<String> list=(ArrayList<String>) results.get(key);
			if(list.get(0).equals("forward")){
				req.getRequestDispatcher(list.get(1)).forward(req, resp);;
			}else{
				if(list.get(0).equals("redirect")){
					resp.sendRedirect(list.get(1));
				}
	}}
	}
	}
	
	private DIBean SearchDi(DependencyMapping dimapping,String url){
		DIBean beanContext=null;
		Iterator<?> iter = dimapping.getDependencyMapping().entrySet().iterator();
		while (iter.hasNext()) {
			Entry<?, ?> e=(Entry<?, ?>) iter.next();
			beanContext=(DIBean) e.getValue();
			if(beanContext.getUrl().equals(url)){
				return beanContext;
			}
		}
		return beanContext;
		
	}

}














