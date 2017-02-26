package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActionMapping {  
	
	
	
		private String name;
		
		private String type;
	    /** 
	     * �൱��web.xml��url��pattern��Ψһ�� 
	     */  
	    private String url;  
	      
	    /** 
	     * ActionAnnotationע���Ӧ������Ҳ����action��Ҫִ�еķ��� 
	     */  
	    private String method;  
	      
	    /** 
	     * ActionAnnotation�е�Result,��Ӧaction�������ص����͡����磺key��'SUCCESS';value:'index.jsp' 
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
