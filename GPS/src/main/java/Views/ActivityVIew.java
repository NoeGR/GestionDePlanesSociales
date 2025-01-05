package Views;


import Interfaces.IActivity;
import java.util.List;

public class ActivityVIew {

    public ActivityVIew(){

    }
    public void renderListActivities(List<IActivity> activities){
        System.out.println("available activities:");
        if(!activities.isEmpty()){
            for(IActivity activity:activities){
                System.out.println(activity.getName());
            }
        }else {
            System.out.println("There are no activities");
        }
    }
    public String renderActivity(IActivity activity){
        return "Activity created successfully: "+activity.getName()+";"+activity.getDescription()+";"+
                activity.getDuration()+";"+activity.getCapacity()+";"+activity.getCost();
    }
    public void activityNotFound(){
        System.out.println("Activity not found. Please check the activity name and try again.");
    }
    public String cannotCreate(){
        return "The activity has not been created, due to the type of data entered in certain fields or because that type of activity cannot be created.\n";
    }

}
