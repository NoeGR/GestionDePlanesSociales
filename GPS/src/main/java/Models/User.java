package Models;
import Interfaces.IActivity;
import Interfaces.ISocialPlan;
import Interfaces.IUser;
import java.util.Objects;

public class User implements IUser {
    private static final Long serialVersionUID = 1L;

    private int age;
    private int mobile;
    private String password;
    private String userName;
    public IActivity m_Activity;
    public ISocialPlan m_SocialPlan;


    public User(){

    }

    public User(int age, int mobile, String name, String password){
            this.age=age;
            this.mobile=mobile;
            this.userName=name;
            this.password=password;
    }

    public int getAge() {
        return age;
    }

    public IActivity getM_Activity() {
        return m_Activity;
    }

    public void setM_Activity(IActivity m_Activity) {
        this.m_Activity = m_Activity;
    }

    public ISocialPlan getM_SocialPlan() {
        return m_SocialPlan;
    }

    public void setM_SocialPlan(ISocialPlan m_SocialPlan) {
        this.m_SocialPlan = m_SocialPlan;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IUser otherUser = (IUser) obj;
        return Objects.equals(userName, otherUser.getUserName()) &&
                Objects.equals(password, otherUser.getPassword()) &&
                age == otherUser.getAge() && mobile==otherUser.getMobile();
    }


}//end User