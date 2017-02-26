package model;

public class UserBean {
	
	private String userId;
	//Ä¬ÈÏÖµ
	private String userName="wk2016";
	private String userPass="2016";
	
	public UserBean(){
		
	}
	
	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean login(String userName,String userPass ) {
		Boolean loginflag = false;
		
		loginflag = (userName != null && userName.equals(this.getUserName())	&&
				userPass != null &&userPass.equals(this.getUserPass())	);

		return loginflag;
	}

}
