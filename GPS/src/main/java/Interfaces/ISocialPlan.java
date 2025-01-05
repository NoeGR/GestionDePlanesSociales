package Interfaces;

import Models.SocialPlan;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonDeserialize(as = SocialPlan.class)
public interface ISocialPlan extends Serializable {
        String getName();
        LocalDateTime getDate();
        Map<String, Integer> getRatings();
        double getAverageRating();
        int getNumParticipants();
        int getNumActivities();
        String getMeetingPoint();
        int getCapacity();
        List<IActivity> getActivities();
        List<IUser> getParticipants();
        IUser getOwner();
        int getVacancy();
        double getPlanCost();
        LocalDateTime getStartTime();
        LocalDateTime getEndTime();
        void setOwner(IUser owner);
        void setCapacity(int capacity);
        void setName(String name);
        void addRating(String user, int value);
        void setRating(int rating);
        void addActivity(IActivity activity);
        void addParticipant(IUser user);
        double calculateTotalTime();
        @Override
        boolean equals(Object obj);
        void setMeetingPoint(String meetingPoint);
        void setDate(LocalDateTime date);
        void setParticipants(List<IUser> participants);
        void setActivities(List<IActivity> activities);
        int getRating();

}
