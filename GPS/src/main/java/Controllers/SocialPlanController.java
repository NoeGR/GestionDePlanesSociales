package Controllers;

import Exceptions.InvalidDataException;
import Exceptions.MissingArgumentException;
import Exceptions.TooManyArgumentsException;
import Interfaces.IActivity;
import Interfaces.ISocialPlan;
import Interfaces.IUser;
import Models.SocialPlan;
import Persistence.DataPersistence;
import Views.SocialPlanView;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class SocialPlanController {
	private static final String USER_DATA_FILE = "socialPlan.json";
	public List<ISocialPlan> m_SocialPlan;

	public SocialPlanView socialPlanView;
	public SocialPlanController(){
		this.socialPlanView= new SocialPlanView();
		this.m_SocialPlan = DataPersistence.loadData(USER_DATA_FILE,ISocialPlan.class);
		if (m_SocialPlan == null) {
			this.m_SocialPlan=new ArrayList<>();
		}
	}
	public String createSocialPlan(String[] args, IUser currentUser, LocalDateTime startTime){
		int argsNum=3;
		try{
		if(args.length<argsNum){
			throw new MissingArgumentException("There are lack of arguments to create the social plan.");
		}else if(args.length>argsNum){
			throw new TooManyArgumentsException("Too many arguments, note the example.");
		}else {
			return this.checkRepeatedPlanAndCreate(args,currentUser,startTime);
		}
		}catch (MissingArgumentException | TooManyArgumentsException | InvalidDataException e){
			return "Error: "+ e.getMessage();
		}
	}
	private String checkRepeatedPlanAndCreate(String[] args, IUser owner,LocalDateTime startTime) {

		try {
				Integer.parseInt(args[0]);
				Integer.parseInt(args[1]);
				throw new InvalidDataException("Invalid entry. The plan name and the meeting point must be a string.");
		} catch (NumberFormatException e) {
				try {
					return this.saveSocialPlan(args, owner, startTime);
				}catch (NumberFormatException e1){
					throw new InvalidDataException("Invalid entry. You entered a string instead of an integer.");
				}

		}
	}

	/**
	 *
	 *Hemos considerado que no se puede crear un plan con el mismo nombre que otro existente.
	 *
	 */
	private String saveSocialPlan(String[] args, IUser owner,LocalDateTime startTime){
		for (ISocialPlan socialPlan : m_SocialPlan) {
			if (socialPlan.getName().equalsIgnoreCase(args[0])) {
				return "Plan already exists";
			}
		}
		ISocialPlan newSPlan = new SocialPlan(args[0], startTime, args[1], Integer.parseInt(args[2]));
		newSPlan.setOwner(owner);
		m_SocialPlan.add(newSPlan);
		DataPersistence.saveData(m_SocialPlan, USER_DATA_FILE);
		return socialPlanView.renderSocialPlan(newSPlan);
	}
	public void addActivityToPlan(String planName, IActivity activity, IUser logged) {
		ISocialPlan socialPlan = findSocialPlan(planName);
		if( socialPlan == null ){
				socialPlanView.noExist();
		}else if (socialPlan.getOwner().equals(logged) && activity!=null && !socialPlan.getActivities().contains(activity)) {
			socialPlan.addActivity(activity);
			if(socialPlan.getCapacity()>activity.getCapacity()){
				socialPlan.setCapacity(activity.getCapacity());
			}
			DataPersistence.saveData(m_SocialPlan, USER_DATA_FILE);
			socialPlanView.renderPlanAndActivities(socialPlan,socialPlan.getActivities());
		} else {
			if(socialPlan.getActivities().contains(activity)){
				socialPlanView.activityAlreadyExist();
			} else if (!socialPlan.getOwner().equals(logged)) {
				socialPlanView.noPlanOwner();
			}
		}
	}
	public String removeSocialPlan(String planName, IUser userLogged){
		ISocialPlan socialPlan = findSocialPlan(planName);
		if (socialPlan != null && socialPlan.getOwner().equals(userLogged)) {
			m_SocialPlan.remove(socialPlan);
			DataPersistence.saveData(m_SocialPlan, USER_DATA_FILE);
			return "Social plan deleted successfully.";
		} else {
			return "Unable to delete the social plan. Please check the plan name and your ownership.";
		}
	}
	public List<ISocialPlan> checkMyPlans(IUser userLogged){
			List<ISocialPlan> myPlanList=new ArrayList<>();
			for (ISocialPlan socialPlan:m_SocialPlan){
				if(socialPlan.getOwner().equals(userLogged)){
					myPlanList.add(socialPlan);
				}
			}
			socialPlanView.showMyPlans(myPlanList);
			return myPlanList;
	}
	public void checkCostSubscription(String planName, IUser participant){
		ISocialPlan myPlan = findSocialPlan(planName);
		if (myPlan != null && myPlan.getParticipants().contains(participant)) {
			double realCost=0.0,totalDiscount=0.0,totalCost = 0.0;
			if(!myPlan.getActivities().isEmpty()) {
					for (IActivity activity : myPlan.getActivities()) {
						realCost+=activity.getCost();
						totalDiscount+=activity.calculateDiscount(participant.getAge(), activity.getCost());
						double activityCost = activity.getCost() - activity.calculateDiscount(participant.getAge(), activity.getCost());
						totalCost += activityCost;
					}
					socialPlanView.renderCostSubscription(myPlan, totalCost,totalDiscount,realCost);
				}
		} else {
			socialPlanView.notAvailable();
		}

	}

	public List<ISocialPlan> checkPlansSubscription(IUser userLogged){
		List<ISocialPlan> myPlans = new ArrayList<>();
		for (ISocialPlan socialPlan : m_SocialPlan) {
			if (socialPlan.getParticipants().contains(userLogged)) {
				myPlans.add(socialPlan);
			}
		}
		socialPlanView.renderListSubscriptions(myPlans);
		return myPlans;
	}
	public ISocialPlan findSocialPlan(String planName) {
		for (ISocialPlan socialPlan : m_SocialPlan) {
			if (socialPlan.getName().equalsIgnoreCase(planName)) {
				return socialPlan;
			}
		}
		return null;
	}

	/**
	 *
	 * @param socialPlan hemos considerado que cuando un plan social no tiene actividades, nadie pueda unirse a él.
	 *
	 */
	public void joinPlan(ISocialPlan socialPlan, IUser userLogged) {
		if(socialPlan!=null){
			if (socialPlan.getActivities().isEmpty()) {
				socialPlanView.unableToJoin();
			} else if (isAvailability(socialPlan, userLogged) && !isPlanFull(socialPlan)) {
				socialPlan.addParticipant(userLogged);
				socialPlan.setCapacity(socialPlan.getCapacity()-1);
				DataPersistence.saveData(m_SocialPlan,USER_DATA_FILE);
				socialPlanView.joined(userLogged.getUserName());
			} else {
				this.joinPlanErrorMessage(socialPlan,userLogged);
			}
		}else {
			socialPlanView.checkPlanName();
		}
	}
	private void joinPlanErrorMessage(ISocialPlan socialPlan,IUser userLogged){
		if(!isFuturePlan(socialPlan)){
			socialPlanView.isOldPlan();
		}else if(!isAvailability(socialPlan,userLogged)){
			socialPlanView.isNotAvailabilityToJoin();
		}else {
			socialPlanView.isPlanFullToJoin();
		}
	}
	public void leavePlan(String planName, IUser userLogged){
		ISocialPlan socialPlan=findSocialPlan(planName);
		if(socialPlan!=null){
				if(isFuturePlan(socialPlan) && socialPlan.getParticipants().contains(userLogged)){
					socialPlan.getParticipants().remove(userLogged);
					socialPlan.setCapacity(socialPlan.getCapacity()+1);
					DataPersistence.saveData(m_SocialPlan,USER_DATA_FILE);
					socialPlanView.leave(userLogged.getUserName());
				}else {
					socialPlanView.notLeave();
				}
		}else {
			socialPlanView.checkPlanName();
		}
	}
	private boolean isAvailability(ISocialPlan socialPlan,IUser userLogged) {
		if(this.plansSubscriptionList(userLogged).isEmpty() && this.isFuturePlan(socialPlan)){
			return true;
		}else{
			if(!socialPlan.getOwner().equals(userLogged) && !socialPlan.getParticipants().contains(userLogged)){
				for (ISocialPlan existingPlan : this.plansSubscriptionList(userLogged)) {
					if (!isTimeOverlap(existingPlan, socialPlan)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean isTimeOverlap(ISocialPlan socialPlan1, ISocialPlan socialPlan2) {
		return (socialPlan1.getStartTime().isBefore(socialPlan2.getEndTime()) && socialPlan1.getEndTime().isAfter(socialPlan2.getStartTime()));
	}
	private boolean isPlanFull(ISocialPlan socialPlan) {
		return socialPlan.getNumParticipants() == socialPlan.getCapacity();
	}
	public List<ISocialPlan> showAvailablePlansNotEnrolled(IUser user) {
		List<ISocialPlan> availablePlans = new ArrayList<>();
		for(ISocialPlan socialPlan: m_SocialPlan){
				if(!socialPlan.getParticipants().contains(user)){
					availablePlans.add(socialPlan);
				}
			}
		socialPlanView.renderPlanList(availablePlans);
		return availablePlans;
	}

	private List<ISocialPlan> plansSubscriptionList(IUser userLogged){
		List<ISocialPlan> myPlans = new ArrayList<>();
		for (ISocialPlan socialPlan : m_SocialPlan) {
			if (socialPlan.getParticipants().contains(userLogged)) {
				myPlans.add(socialPlan);
			}
		}
		return myPlans;
	}
	public void checkFullListOfPlans(){
		socialPlanView.renderFullPlanList(m_SocialPlan);
	}

	/**
	 *
	 *  Este método muestra la lista disponible de planes y la ordenación se hace en relación al "promedio" de los puntos obtenidos del plan.
	 *  Por otra parte, en el caso de igualdad de promedio, los planes se ordenaran por la fecha.
	 */
	public void plansListAvailable(IUser userLogged) {
		List<ISocialPlan> availablePlans = new ArrayList<>();
		for(ISocialPlan socialPlan : m_SocialPlan){
			if(isFuturePlan(socialPlan) && !isPlanFull(socialPlan) && !socialPlan.getParticipants().contains(userLogged)){
				availablePlans.add(socialPlan);
			}
		}
		List<ISocialPlan> orderPlansList=this.orderPlans(availablePlans,userLogged);
		socialPlanView.renderListSocialPlanDetails(orderPlansList);
	}
	private boolean isFuturePlan(ISocialPlan socialPlan) {
		return socialPlan.getDate().isAfter(LocalDateTime.now());
	}

	private List<ISocialPlan> orderPlans(List<ISocialPlan> plansToOrder,IUser userLogged) {

		return plansToOrder.stream().filter(socialPlan -> socialPlan.getOwner().equals(userLogged))
				.sorted(Comparator.comparingDouble(ISocialPlan::getAverageRating).reversed().thenComparing(ISocialPlan::getDate))
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @param userLogged Hemos considerado que si el usuario logeado es el propietario, no puede puntuar su propio plan.
	 * @param rating Hemos decido establecer un mínimo=1 y un máximo=5 en la puntuación del plan.
	 */
	public void ratePlan(String planName, IUser userLogged, int rating) {
		ISocialPlan socialPlan = findSocialPlan(planName);
		if (socialPlan != null ){
			if(!socialPlan.getOwner().equals(userLogged)){
				if(!this.isFuturePlan(socialPlan) && socialPlan.getParticipants().contains(userLogged) && !socialPlan.getRatings().containsKey(userLogged.getUserName())) {
					this.validRating(socialPlan,userLogged,rating);
				}else {
					this.rateProblems(socialPlan,userLogged);
				}
			}else {
					socialPlanView.noOwnerRating();
			}
		} else {
			socialPlanView.checkPlanName();
		}
	}
	private void rateProblems(ISocialPlan socialPlan,IUser userLogged){
		if(this.isFuturePlan(socialPlan)){
			socialPlanView.isFuturePlan();
		} else if (!socialPlan.getParticipants().contains(userLogged)) {
			socialPlanView.notSubscribed();
		}else {
			socialPlanView.repeatedRating();
		}
	}
	private void validRating(ISocialPlan socialPlan,IUser userLogged, int rating){
		int minRating=1,maxRating=5;
		if (rating >= minRating && rating <= maxRating) {
			socialPlan.addRating(userLogged.getUserName(), rating);
			this.updatePlanRating(socialPlan);
			DataPersistence.saveData(m_SocialPlan, USER_DATA_FILE);
			socialPlanView.planRatedSuccessfully();
		} else {
			socialPlanView.invalidRating();
		}
	}
	private void updatePlanRating(ISocialPlan socialPlan) {
		if (!socialPlan.getRatings().isEmpty()) {
			double averageRating = socialPlan.getRatings().values().stream().mapToInt(Integer::intValue).average().orElse(0.0);
			socialPlan.setRating((int) Math.round(averageRating));
		}
	}
	//CONSULTA OPCIONAL 1
	public void checkPlanActivities(String planName){
		ISocialPlan plan=findSocialPlan(planName);
		socialPlanView.renderActivitiesInSocialPlan(plan.getActivities(),plan.getName());
	}

	//CONSULTA OPCIONAL 2
	public int countActivitiesInPlan(String planName){
		ISocialPlan socialPlan=findSocialPlan(planName);
		return socialPlan.getNumActivities();
	}
	//CONSULTA OPCIONAL 3
	public List<ISocialPlan> getPlansContainActivity(IActivity activity){
		List<ISocialPlan> newPlanList= new ArrayList<>();
		for (ISocialPlan socialPlan : m_SocialPlan) {
				if(socialPlan.getActivities().contains(activity)){
					newPlanList.add(socialPlan);
				}
			}

		return newPlanList;
	}

}//end SocialPlanController