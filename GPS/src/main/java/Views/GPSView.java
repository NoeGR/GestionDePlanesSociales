package Views;

import Interfaces.ISocialPlan;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class GPSView {

	private final Scanner sc;

	public GPSView() {
		sc = new Scanner(System.in);
	}

	public void welcome() {
		System.out.println("Welcome to App (Gestión de Planes Sociales)");
	}
	public void promptForUserDetails() {
		System.out.println("Enter user details:");
		System.out.print("Age , ");
		System.out.print("Mobile (9 digits) , ");
		System.out.print("Name , ");
		System.out.print("Password\n");

	}

	public void promptForLoginDetails() {
		System.out.println("Enter login details:");
		System.out.print("Username , ");
		System.out.print("Password\n");
	}
	public void loginLeadInput(){
		System.out.println("You must separate the username from the password with a comma as follows:");
		System.out.println("Example: pedro,password123\n");
	}

	public void userLeadInput(){
		System.out.println("Data is entered with commas as follows:");
		System.out.println("Example: 19,132456789,pedro,password123\n");
	}
	public void planLeadInput(){
		System.out.println("Data is entered with commas as follows :");
		System.out.println("Example: Plan1,meetingPointExa,15\n");
	}
	public void activityLeadInput(){
		System.out.println("Data is entered with commas as follows:");
		System.out.println("Example: activityName,descriptionExa,120,20,22 ");
	}
	public void promptForActivityDetails() {
		System.out.println("\nEnter activity details:");
		System.out.print("Name , ");
		System.out.print("Description , ");
		System.out.print("Duration , ");
		System.out.print("Cost , ");
		System.out.print("Capacity\n");

	}
	public void promptForSocialPlanDetails() {
		System.out.println("Enter social plan details:");
		System.out.print("Name , ");
		System.out.print("Meeting Point , ");
		System.out.print("Capacity\n");

	}
	public void addActivitiesInfo(){
		System.out.println("add an activity:");
	}
	public void promptForSocialPlanName() {
		System.out.println("Enter social plan name:");
	}
	public void renderPlansList(List<ISocialPlan> plans, String activityName){
		System.out.println("List of plans that contain the activity: "+activityName);
		if(!plans.isEmpty()){
			for(ISocialPlan socialPlan:plans){
				System.out.println(socialPlan.getName());
			}
		}else {
			System.out.println("There are no plans\n");
		}
	}

	public int getOptionFromUser() {
		int option;
		System.out.print("Enter your choice: ");
		try {
			String input = sc.nextLine();
			option = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			option = -1;
		}
		return option;
	}
	public String[] getUserInput() {
		String userInput = sc.nextLine();
		String[] inputArray = userInput.split(",");
		for (int i = 0; i < inputArray.length; i++) {
			inputArray[i] = inputArray[i].trim();
		}
		return inputArray;
	}
	public String getCommandInput() {
		String userInput;
		try {
			 	userInput = sc.nextLine().trim();
			if (isString(userInput)) {
				return userInput;
			} else {
				throw new RuntimeException("Invalid entry. Please enter a valid command.");
			}
		}catch (RuntimeException e){
			return e.getMessage();
		}
	}
	public String getStringInput() {
		String userInput;
		try {
			userInput = sc.nextLine().trim();
			if (isString(userInput)) {
				return userInput;
			} else {
				throw new RuntimeException("Invalid entry");
			}
		}catch (RuntimeException e){
			return e.getMessage();
		}
	}
	private boolean isString(String value) {
		return value != null && !value.matches(".*\\d.*");
	}
	private int getIntegerInput() {
		int option;
		try {
			String input = sc.nextLine();
			option = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid entry. Please enter an integer.");
		}
		return option;
	}
	public LocalDateTime getDateTimeFromUser() {
		System.out.println("Date:");

		int year = getIntegerInputWithValidation("Enter the year:", 0, Integer.MAX_VALUE);
		int month = getIntegerInputWithValidation("Enter the month (1-12):", 1, 12);
		int dayOfMonth = getIntegerInputWithValidation("Enter the day of the month:", 1, 31);
		int hour = getIntegerInputWithValidation("Enter the hour of the day (0-23):", 0, 23);
		int minute = getIntegerInputWithValidation("Enter the minutes (0-59):", 0, 59);

		return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
	}

	private int getIntegerInputWithValidation(String message, int min, int max) {
		int input;
			do {
				try {

					System.out.println(message);
					input = getIntegerInput();
				}catch (RuntimeException e){
					System.out.println("Error: " + e.getMessage());
					input=-1;
				}
			} while (input < min || input > max);


		return input;
	}
	public void displayMessage(String message) {
		System.out.println(message);
	}

}
