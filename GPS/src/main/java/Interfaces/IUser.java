package Interfaces;

import Models.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
@JsonDeserialize(as = User.class)
public interface IUser extends Serializable {
    int getAge();

    void setAge(int age);

    int getMobile();

    void setMobile(int mobile);

    String getPassword();

    void setPassword(String password);

    String getUserName();

    void setUserName(String userName);

    IActivity getM_Activity();

    void setM_Activity(IActivity m_Activity);

    ISocialPlan getM_SocialPlan();

    void setM_SocialPlan(ISocialPlan m_SocialPlan);
    @Override
    boolean equals(Object obj);
}
