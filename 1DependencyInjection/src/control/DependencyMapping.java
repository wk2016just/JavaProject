package control;

import java.util.HashMap;

public class DependencyMapping {
	private HashMap<String, DIBean> contexts;

	public HashMap<String, DIBean> getDependencyMapping() {
		return contexts;
	}

	public void setDependencyMapping(HashMap<String, DIBean> contexts) {
		this.contexts = contexts;
	}
	
	

}
