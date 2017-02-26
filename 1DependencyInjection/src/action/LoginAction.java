package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserBean;

public class LoginAction {
	UserBean userBean;
	
	public LoginAction(){
		
	}
	
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String login(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("username");
		String ps=request.getParameter("passwd");
		if(userBean.login(name, name)){
			return "success";
		}else{
			return "fail";
		}
	}

}
