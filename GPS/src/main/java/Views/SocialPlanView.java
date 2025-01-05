package Views;


import Interfaces.IActivity;
import Interfaces.ISocialPlan;
import java.util.List;

public class SocialPlanView {

	public SocialPlanView(){
	}
	public void showMyPlans (List<ISocialPlan> socialPlanList){
		System.out.println("You own the following plans: ");
		if(socialPlanList.isEmpty()){
			System.out.println("You can't add activities because you don't own any plans.");
		}else {
			for (ISocialPlan socialPlan:socialPlanList){
				System.out.println(socialPlan.getName());
			}
		}
	}
	public void noExist(){
		System.out.println("You cannot add activities. The plan does not exist.");
	}
	public void activityAlreadyExist(){
		System.out.println("Activity already exists in the plan.");
	}
	public void noPlanOwner(){
		System.out.println("you are not the owner.");
	}
	public String renderSocialPlan(ISocialPlan socialPlan){
		return "Social plan created. "+ "Owner:"+socialPlan.getOwner().getUserName()+";"+" Plan Name:"+socialPlan.getName()+";"+" Date:"+
				socialPlan.getDate()+";"+" MeetingPoint:"+socialPlan.getMeetingPoint()+";"+" Capacity:"+
				socialPlan.getCapacity()+";"+" Duration:"+socialPlan.calculateTotalTime()+";"+" Cost:"+socialPlan.getPlanCost();
	}
	public void renderPlanAndActivities(ISocialPlan socialPlan,List<IActivity> activities){
		System.out.println("Activity added to the plan successfully. "+ "Owner:"+socialPlan.getOwner().getUserName()+";"+" Plan Name:"+socialPlan.getName()+";"+" Date:"+
				socialPlan.getDate()+";"+" MeetingPoint:"+socialPlan.getMeetingPoint()+";"+" Capacity:"+
				socialPlan.getCapacity()+";"+" Duration:"+socialPlan.calculateTotalTime()+";"+" Cost:"+socialPlan.getPlanCost());
		System.out.print("Activities: ");
		for (IActivity activity: activities){
			System.out.print(activity.getName()+"; ");
		}
		System.out.println();
	}
	public void renderListSocialPlanDetails(List<ISocialPlan> socialPlanList){
		System.out.println("Available plans:");
		if(!socialPlanList.isEmpty()){
		for(ISocialPlan socialPlan:socialPlanList){
			System.out.println("-Plan Name: " + socialPlan.getName()+"\n" + "-Rating:" +socialPlan.getAverageRating()+
					"\n"+"-Date:" +socialPlan.getDate()+
					"\n" + "-Meeting Point: " + socialPlan.getMeetingPoint() +
					"\n" + "-Vacancy: " + socialPlan.getVacancy());
		}}else {
			System.out.println("There are no plans available.");
		}
	}
	public void renderActivitiesInSocialPlan(List<IActivity> activities, String planName){
		int cont=0;
		System.out.println("The "+planName+" plan contains the following activities: ");
		if(!activities.isEmpty()){
			for(IActivity activity:activities){
				cont++;
				System.out.println(cont+"\n" +"-Activity name: "+activity.getName()+"\n"+"-Description: "+activity.getDescription()+
						"\n"+"-Duration: "+ activity.getDuration()+"\n"+"-Cost: "+activity.getCost()+
						"\n"+"-Capacity: "+activity.getCapacity());
			}
		}else {
			System.out.println("The plan has no activities.");
		}

	}
	public void renderListSubscriptions(List<ISocialPlan> plans){
		System.out.println("Name of the plans you are subscribed to:");
		if(plans.isEmpty()){
			System.out.println("You are not subscribed to any plan.");
		}else {
			for(ISocialPlan socialPlan:plans){
				System.out.println(socialPlan.getName());
			}
		}
	}
	public void renderPlanList(List<ISocialPlan> plans){
		System.out.println("List of existing plans you are not enrolled in: ");
		if(!plans.isEmpty()){
			for(ISocialPlan socialPlan:plans){
				System.out.println(socialPlan.getName());
			}
		}else {
			System.out.println("There are no plans.\n");
		}
	}
	public void renderFullPlanList(List<ISocialPlan> plans){
		System.out.println("List of existing plans: ");
		if(!plans.isEmpty()){
			for(ISocialPlan socialPlan:plans){
				System.out.println(socialPlan.getName());
			}
		}else {
			System.out.println("There are no plans.\n");
		}
	}
	public void notAvailable(){
		System.out.println("Unable to retrieve cost information. Please check the plan name and your subscription.");

	}
	public void unableToJoin(){
		System.out.println("Unable to join the social plan. The plan has no activities.");
	}
	public void joined(String name){
		System.out.println(name + " joined the social plan successfully.");
	}
	public void leave(String name){
		System.out.println(name + " left the plan.");
	}
	public void notLeave(){
		System.out.println(" You can't leave the plan because it is old.");
	}
	public void isNotAvailabilityToJoin(){
		System.out.println("Unable to join the social plan. There is an overlap in schedules .");
	}
	public void isOldPlan(){
		System.out.println("Unable to join the social plan. The plan is old.");
	}
	public void isPlanFullToJoin(){
		System.out.println("Unable to join the social plan. The plan is full.");
	}
	public void checkPlanName(){
		System.out.println("Unable to join the social plan. Check the plan name.");
	}
	public void renderCostSubscription(ISocialPlan socialPlan, double cost,double discount,double realCost){
		System.out.println("Plan Name: "+socialPlan.getName()+"\nUndiscounted cost:"+realCost+"€"+"\n"+"Discount applied:"+discount+"€\n"+"TOTAL COST: "+ cost+"€\n");
	}
	public void planRatedSuccessfully(){
		System.out.println("The plan has been rated");
	}
	public void invalidRating(){
		System.out.println("The rating must be between 1 and 5.");
	}
	public void isFuturePlan(){
		System.out.println("You cannot rate the plan because it is for the future.");

	}
	public void notSubscribed(){
		System.out.println("You cannot rate the plan because you were not subscribed to this plan.");
	}
	public void repeatedRating(){
		System.out.println("You cannot rate the plan more than once");
	}
	public void noOwnerRating(){
		System.out.println("You cannot rate the plan because you are the owner");
	}
}//end socialPlanView