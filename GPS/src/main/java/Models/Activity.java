package Models;

import Interfaces.IActivity;
import com.fasterxml.jackson.annotation.*;
import java.util.Objects;


public class Activity implements IActivity{
    private static final Long serialVersionUID = 1L;
    private String name;
    private String description;
    private int duration;
    private double cost;
    private int capacity;
    @JsonProperty("activityType")
    private String activityType;

    public Activity(){

    }
    public Activity(String name, String description, int duration, double cost, int capacity, String activityType) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
        this.capacity = capacity;
        this.activityType=activityType;
    }
    public double calculateDiscount(int age, double cost){
        return 0.0;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IActivity otherActivity = (IActivity) obj;
        return Objects.equals(name, otherActivity.getName()) &&
                duration == otherActivity.getDuration() &&
                Objects.equals(description, otherActivity.getDescription()) &&
                cost==otherActivity.getCost() &&
                capacity==otherActivity.getCapacity();
    }

}
