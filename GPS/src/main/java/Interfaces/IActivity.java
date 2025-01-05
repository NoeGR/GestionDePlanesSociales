package Interfaces;

import Models.Activity;
import Models.Cinema;
import Models.Theater;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "activityType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cinema.class, name = "cinema"),
        @JsonSubTypes.Type(value = Theater.class, name = "theater"),
})
@JsonTypeName("generic")
public interface IActivity extends Serializable {
 @JsonCreator
 static IActivity create() {

  return new Activity();
 }
   double calculateDiscount(int age, double cost);
    String getActivityType();
    void setActivityType(String activityType);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    int getDuration();
    void setDuration(int duration);

    double getCost();
    void setCost(double cost);

    int getCapacity();
    void setCapacity(int capacity);

    @Override
    boolean equals(Object obj);
}
