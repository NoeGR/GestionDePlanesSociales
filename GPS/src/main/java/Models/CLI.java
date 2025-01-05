package Models;

import Interfaces.IActivity;
import Interfaces.ISocialPlan;
import Interfaces.IUser;
import Views.GPSView;
import Controllers.SocialPlanController;
import Controllers.UserController;
import Controllers.ActivityController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public class CLI {
    public UserController m_UserController;
    public SocialPlanController m_SocialPlanController;
    public ActivityController m_ActivityController;
    public GPSView m_GPSView;

    public CLI(){
        this.m_GPSView= new GPSView();
        this.m_SocialPlanController= new SocialPlanController();
        this.m_UserController= new UserController();
        this.m_ActivityController= new ActivityController();
    }
    public void Operations() {
        m_GPSView.welcome();
        String command;
        do {
                    System.out.print("gps" + (m_UserController.getCurrentUser() != null ? "-" + m_UserController.getCurrentUser().getUserName() : "") + "> ");
                    command = m_GPSView.getCommandInput() ;
                        if (m_UserController.getCurrentUser() == null) {
                            this.operationsBeforeLogin(command);
                        } else {
                            this.operationsAfterLogin(command);
                        }

        } while (!Objects.requireNonNull(command).equalsIgnoreCase("exit") );
    }
    private void operationsBeforeLogin(String command){
        switch (command) {
            case "signup" -> this.signUpOperation();
            case "login" -> this.loginOperation();
            case "exit" -> m_GPSView.displayMessage("Exiting the App");
            default -> m_GPSView.displayMessage("Invalid command or you are not logged in to perform this action, try again."+"\n");
        }
    }
    private void operationsAfterLogin(String command){
        switch (command) {
            case "create-act" -> this.createActivityOperation();
            case "create-plan" -> this.createPlanOperation();
            case "add-act" -> this.addActivityOperation();
            case "remove-plan" -> this.removeSocialPlanOperation();
            case "check-avail-plans" -> this.checkAvailablePlans();
            case "check-plans-subs" -> this.checkPlansSubscriptionOperation();
            case "check-plan-cost" -> this.checkCostSubscriptionOperation();
            case "rate-plan" -> this.ratePlanOperation();
            case "join-plan" -> this.joinPlanOperation();
            case "leave-plan" -> this.leavePlanOperation();
            case "check-plan-act" -> this.checkPlanActivitiesOperation();
            case "count-act" -> this.countActivitiesInPlanOperation();
            case "plan-contain-act" -> this.getPlansContainActivityOperation();
            case "logout" -> m_UserController.logout();
            default -> m_GPSView.displayMessage("Invalid command, try again.");

        }
    }
    private void signUpOperation() {
        m_GPSView.userLeadInput();
        m_GPSView.promptForUserDetails();
        String[] userArgs = m_GPSView.getUserInput();
        String createUserResult = m_UserController.createUser(userArgs);
        m_GPSView.displayMessage(createUserResult);
    }

    private void loginOperation() {
        m_GPSView.loginLeadInput();
        m_GPSView.promptForLoginDetails();
        String[] loginArgs = m_GPSView.getUserInput();
        m_UserController.login(loginArgs);

    }

    private void createActivityOperation(){
        m_GPSView.activityLeadInput();
        m_GPSView.promptForActivityDetails();
        String[] activityArgs = m_GPSView.getUserInput();
        m_GPSView.displayMessage("Enter activity type (generic/cinema/theater): ");
        String activityType = m_GPSView.getUserInput()[0];
        m_GPSView.displayMessage(m_ActivityController.createActivity(activityArgs, activityType));
    }
    private void createPlanOperation(){
        String[] socialPlanArgs;
        m_GPSView.planLeadInput();
        m_GPSView.promptForSocialPlanDetails();
        socialPlanArgs = m_GPSView.getUserInput();
        IUser currentUser=m_UserController.getCurrentUser();
        LocalDateTime startTime = m_GPSView.getDateTimeFromUser();
        String createSocialPlanResult = m_SocialPlanController.createSocialPlan(socialPlanArgs,currentUser,startTime);
        m_GPSView.displayMessage(createSocialPlanResult);
    }
    private void addActivityOperation(){
        IUser currentUser= m_UserController.getCurrentUser();
        if(!m_SocialPlanController.checkMyPlans(currentUser).isEmpty()){
            m_GPSView.displayMessage("name of the plan to which you want to add an activity:");
            String planName=m_GPSView.getStringInput();
            m_GPSView.addActivitiesInfo();
            if (!m_ActivityController.checkActivities().isEmpty()){
                m_GPSView.displayMessage("Choose the name of the activity you want to add:");
                String activityName=m_GPSView.getStringInput();
                IActivity activity=m_ActivityController.findActivityByName(activityName);
                m_SocialPlanController.addActivityToPlan(planName,activity,currentUser);
            }
        }
    }
    private void removeSocialPlanOperation() {
        IUser currentUser= m_UserController.getCurrentUser();
        List<ISocialPlan> auxList=m_SocialPlanController.checkMyPlans(currentUser);
        if(!auxList.isEmpty()){
        m_GPSView.promptForSocialPlanName();
        String planNameToDelete = m_GPSView.getStringInput();
        IUser userLogged = m_UserController.getCurrentUser();
        String deleteResult = m_SocialPlanController.removeSocialPlan(planNameToDelete, userLogged);
        m_GPSView.displayMessage(deleteResult);
        }
    }
    private void checkAvailablePlans(){
        IUser currentUser= m_UserController.getCurrentUser();
        m_SocialPlanController.plansListAvailable(currentUser);
    }
    private void checkPlansSubscriptionOperation() {
        IUser currentUser = m_UserController.getCurrentUser();
        m_SocialPlanController.checkPlansSubscription(currentUser);
    }

    private void checkCostSubscriptionOperation() {
        IUser currentUser = m_UserController.getCurrentUser();
        if(!m_SocialPlanController.checkPlansSubscription(currentUser).isEmpty()){
        m_GPSView.promptForSocialPlanName();
        String planNameToCheckCost = m_GPSView.getStringInput();
        m_SocialPlanController.checkCostSubscription(planNameToCheckCost, currentUser);
        }
    }

    private void joinPlanOperation() {
        IUser currentUser = m_UserController.getCurrentUser();
        m_SocialPlanController.checkPlansSubscription(currentUser);
        if(!m_SocialPlanController.showAvailablePlansNotEnrolled(currentUser).isEmpty()){
            m_GPSView.promptForSocialPlanName();
            String planNameToJoin = m_GPSView.getStringInput();
            ISocialPlan planToJoin = m_SocialPlanController.findSocialPlan(planNameToJoin);
            m_SocialPlanController.joinPlan(planToJoin, currentUser);
        }
    }
    private void leavePlanOperation() {
        IUser currentUser = m_UserController.getCurrentUser();
        m_SocialPlanController.checkPlansSubscription(currentUser);
        m_GPSView.promptForSocialPlanName();
        String planName = m_GPSView.getStringInput();
        m_SocialPlanController.leavePlan(planName, currentUser);

    }
    private void ratePlanOperation(){
        IUser currentUser= m_UserController.getCurrentUser();
        m_SocialPlanController.checkFullListOfPlans();
        m_GPSView.displayMessage("Enter the name of the plan you want to rate.");
        String planName=m_GPSView.getStringInput();
        m_GPSView.displayMessage("The rating must be between 1 and 5.");
        int rating=m_GPSView.getOptionFromUser();
        m_SocialPlanController.ratePlan(planName,currentUser,rating);
    }

    //CONSULTA OPCIONAL 1
    private void checkPlanActivitiesOperation(){
        m_SocialPlanController.checkFullListOfPlans();
        m_GPSView.displayMessage("Enter the name of the plan to see the activities it contains:");
        String planName= m_GPSView.getStringInput();
        m_SocialPlanController.checkPlanActivities(planName);
    }
    //CONSULTA OPCIONAL 2
    private void countActivitiesInPlanOperation(){
        m_SocialPlanController.checkFullListOfPlans();
        m_GPSView.displayMessage("Enter the name of the plan for which you want to know the number of activities:");
        String planName= m_GPSView.getStringInput();
        m_GPSView.displayMessage(planName+ "has "+m_SocialPlanController.countActivitiesInPlan(planName)+" activities");
    }
    //CONSULTA OPCIONAL 3
    private void getPlansContainActivityOperation(){
        m_ActivityController.checkActivities();
        String activityName= m_GPSView.getStringInput();
        IActivity activity=m_ActivityController.findActivityByName(activityName);
        if (activity!=null){
        m_GPSView.renderPlansList(m_SocialPlanController.getPlansContainActivity(activity),activity.getName());
        }
    }

}