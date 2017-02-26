package control;

import java.util.HashMap;

public class DIBean {
	
	private String name;
	private String url;
	private HashMap<String, Property> properties;
	
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
	public HashMap<String, Property> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, Property> properties) {
		this.properties = properties;
	}
	
	

}
