package Controllers;


import Exceptions.InvalidDataException;
import Exceptions.MissingArgumentException;
import Exceptions.TooManyArgumentsException;
import Interfaces.IUser;
import Models.User;
import Persistence.DataPersistence;
import Views.UserView;
import java.util.*;

public class UserController {

	public List<IUser> m_User;
	public UserView m_userView;
	private static IUser currentUser;
	private static final int MIN_AGE=14;
	private static final int MAX_AGE=100;

	private static final String USER_DATA_FILE = "users.json";
	public UserController(){
		this.m_userView=new UserView();
		this.m_User = DataPersistence.loadData(USER_DATA_FILE,IUser.class);
		if (m_User == null) {
			this.m_User = new ArrayList<>();
		}

	}

	public IUser getCurrentUser() {
		return currentUser;
	}

	public String createUser(String[] args){
	int argsNum=4;
		try {
			if (args.length < argsNum) {
				throw new MissingArgumentException("Missing arguments to create the user.");
			} else if (args.length > argsNum) {
				throw new TooManyArgumentsException("Too many arguments, note the example.");
			} else {
				return this.checkRepeatedUserAndCreate(args);
			}
		} catch (MissingArgumentException | TooManyArgumentsException | InvalidDataException e) {
			return "Error: "+ e.getMessage();
		}

	}
	private String checkRepeatedUserAndCreate(String[] args){
		try {
			int age = Integer.parseInt(args[0]);
			if (age < MIN_AGE || age > MAX_AGE) {
				throw new InvalidDataException("Invalid age. Age must be between 14 and 100 (inclusive).");
			}
			Integer.parseInt(args[2]);
			throw  new InvalidDataException("Invalid user name. You entered an integer instead of a string.");
		} catch (NumberFormatException e) {
			try {
				return this.saveUser(args);

			} catch (NumberFormatException ex) {
				throw new InvalidDataException("Invalid entry. You entered a string instead of an integer.");
			}
		}
	}
	private String saveUser(String[] args){
		int minPassChar=3;
		if (args[1].matches("\\d{9}")) {
			for (IUser user : m_User) {
				if (user.getUserName().equals(args[2]))
					return "That user already exists";
			}
			if (args[3].length() >= minPassChar) {
				IUser newUser = new User(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], args[3]);
				m_User.add(newUser);
				DataPersistence.saveData(m_User, USER_DATA_FILE);
				return m_userView.renderUser(newUser);
			} else {
				throw new InvalidDataException("Invalid entry. Password must have a minimum of 3 characters.");
			}
		}else{
			throw new InvalidDataException("Invalid entry. Phone number must be an integer of 9 digits.");
		}
	}

	public void login(String[] loginData){
		int numLogArgs=2;
		try {
			if (loginData.length<numLogArgs) {
				throw new MissingArgumentException("Both username and password are required for login.");
			}
			currentUser = null;
			for (IUser user : m_User) {
				if (user.getUserName().equals(loginData[0]) && user.getPassword().equals(loginData[1])) {
					currentUser = user;
				}
			}
			m_userView.loginMessage(currentUser);
		} catch (MissingArgumentException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}


	public void logout(){
		currentUser=null;
		m_userView.logoutMessage();
	}
}//end UserController