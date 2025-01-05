package Views;

import Interfaces.IUser;

public class UserView {

	public UserView(){

	}
	public String renderUser(IUser user){
		return "User created: "+user.getUserName()+";"+user.getAge()+";"+user.getMobile()+";"+user.getPassword();
	}
	public void loginMessage(IUser loggedInUser){
		if (loggedInUser != null) {
			System.out.println("Login successful. Welcome, " + loggedInUser.getUserName() + "!" + "\n");
		} else {
			System.out.println("Login failed. Please check your credentials and try again." + "\n");
		}
	}
	public void logoutMessage() {
		System.out.println("Goodbye! See you soon.\n");
	}
}//end userView