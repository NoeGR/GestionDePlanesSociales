package Models;
import Interfaces.IActivity;
import Interfaces.ISocialPlan;
import Interfaces.IUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialPlan implements ISocialPlan{
    private static final Long serialVersionUID = 1L;
    private String name;
    private LocalDateTime date ;
    private String meetingPoint;
    private int capacity;
    private int rating;
    public List<IActivity> activities;
    public List<IUser> participants;
    private Map<String, Integer> ratings;
    public IUser owner;
    private static  final int displacementBetweenActivities=20;
    public SocialPlan(){

    }

    public SocialPlan(String name, LocalDateTime date, String meetingPoint, int capacity) {
        this.name = name;
        this.date = date;
        this.meetingPoint = meetingPoint;
        this.capacity = capacity;
        this.participants=new ArrayList<>();
        this.activities=new ArrayList<>();
        this.ratings=new HashMap<>();
    }

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void addRating(String user, int value) {
        this.ratings.put(user, value);
    }
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }
    public int getNumParticipants(){
        return this.participants.size();
    }
    public int getNumActivities(){
       return this.activities.size();
    }
    public IUser getOwner() {
        return owner;
    }

    public void setOwner(IUser owner) {
        this.owner = owner;
    }


    public List<IActivity> getActivities() {
        return activities;
    }

    public List<IUser> getParticipants() {
        return participants;
    }
    public void addActivity(IActivity activity) {
        this.activities.add(activity);
    }
    public LocalDateTime getStartTime(){
        return date;
    }
    public LocalDateTime getEndTime(){
        return date.plusMinutes((long) this.calculateTotalTime());
    }

    public void addParticipant(IUser user) {
        this.participants.add(user);
    }

    public void setActivities(List<IActivity> activities) {
        this.activities = activities;
    }

    public void setParticipants(List<IUser> participants) {
        this.participants = participants;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getVacancy() {
        return getCapacity()-participants.size();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @return Calculamos y devolvemos el promedio de todas las puntuaciones que tiene un plan.
     */
    @Override
    public double getAverageRating() {
        double sumEmptyRatings = 0.0;
        Map<String, Integer> ratings = this.getRatings();
        if (ratings.isEmpty()) {
            return sumEmptyRatings;
        }
        int sumRatings = ratings.values().stream().mapToInt(Integer::intValue).sum();
        return (double) sumRatings / ratings.size();
    }
    public double getPlanCost(){
            double totalCost = 0.0;
            if(!getActivities().isEmpty()) {
                for (IActivity activity : activities) {
                    totalCost += activity.getCost();
                }
            }
            return totalCost;

    }
    public double calculateTotalTime(){
       double totalTime=0.0;
       for (int i=0; i< activities.size(); i++){
           totalTime+=activities.get(i).getDuration();
           if (i < activities.size() - 1) {
               totalTime += displacementBetweenActivities;
           }
       }
        return totalTime;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ISocialPlan otherSocialPlan = (ISocialPlan) obj;
        return Objects.equals(name, otherSocialPlan.getName()) &&
                Objects.equals(date, otherSocialPlan.getDate()) &&
                Objects.equals(meetingPoint, otherSocialPlan.getMeetingPoint()) &&
                capacity == otherSocialPlan.getNumParticipants() &&
                Objects.equals(activities, otherSocialPlan.getActivities()) &&
                Objects.equals(participants, otherSocialPlan.getParticipants()) &&
                Objects.equals(owner, otherSocialPlan.getOwner());
    }
}
