package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActionMapping {  
	
	
	
		private String name;
		
		private String type;
	    /** 
	     * 相当于web.xml中url－pattern，唯一的 
	     */  
	    private String url;  
	      
	    /** 
	     * ActionAnnotation注解对应方法，也就是action中要执行的方法 
	     */  
	    private String method;  
	      
	    /** 
	     * ActionAnnotation中的Result,对应action方法返回的类型。例如：key：'SUCCESS';value:'index.jsp' 
	     */  
	    private HashMap<String, ArrayList<String>> results;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public HashMap<String, ArrayList<String>> getResults() {
			return results;
		}

		public void setResults(HashMap<String, ArrayList<String>> results) {
			this.results = results;
		}
		
		


}
