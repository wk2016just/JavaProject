package control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class DependencySearch {
	
	private DependencyMapping dimapping;
	private DIBean dibean;
	
	
	
	public DependencySearch(DependencyMapping dimapping, DIBean dibean) {
		super();
		this.dimapping = dimapping;
		this.dibean = dibean;
	}



	public Object DependencyInjection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		
		Class<?> c = Class.forName(dibean.getUrl());
		Object o = c.newInstance();
		
		//得到bean的属性列表，判断其含有ref bean与否
		Iterator<?> iter = dibean.getProperties().entrySet().iterator();
		while (iter.hasNext()) {
			Entry<?, ?> e = (Entry<?, ?>) iter.next();
			String name = (String) e.getKey();
			//如果含有ref
			String ref = dibean.getProperties().get(name).getRef();
			if(ref != null){
				DIBean nextBean = null;
				Iterator<?> beanIter = dimapping.getDependencyMapping().entrySet().iterator();
				Object filed = null;
				while (beanIter.hasNext()) {
					Entry<?, ?> entry = (Entry<?, ?>) beanIter.next();
					nextBean=(DIBean) entry.getValue();
					if(nextBean.getName().equals(ref)){
						//进行递归的构造依赖对象
						filed=new DependencySearch(dimapping, nextBean).DependencyInjection();
					
					}
				}
				
				//获得setter方法进行注入
				String methodName="set"+name.substring(0, 1).toUpperCase() + name.substring(1);
				Method m=c.getDeclaredMethod(methodName,c.getDeclaredField(name).getType());
				m.invoke(o, filed);
				
			}
		}
		
		
		return o;
		
	}

}
