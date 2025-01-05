package Controllers;

import Exceptions.InvalidDataException;
import Exceptions.MissingArgumentException;
import Exceptions.TooManyArgumentsException;
import Interfaces.IActivity;
import Models.Activity;
import Models.Theater;
import Models.Cinema;
import Persistence.DataPersistence;
import Views.ActivityVIew;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {
    public List<IActivity> activities;
    public ActivityVIew activityVIew;
    private static final String USER_DATA_FILE = "activity.json";

    public  ActivityController(){
        this.activityVIew=new ActivityVIew();
        this.activities = DataPersistence.loadData(USER_DATA_FILE,IActivity.class);
        if (activities == null) {
            this.activities= new ArrayList<>();
        }
    }
    public String createActivity(String[] args, String activityType) {
        int argsNum=5;
     try {
             if (args.length < argsNum) {
                 throw new MissingArgumentException("Missing arguments to create the activity.");
             } else if (args.length > argsNum) {
                 throw new TooManyArgumentsException("Too many arguments, look at the example.");
             } else {
                 if (isString(args[0]) || isString(args[1]) || isString(activityType)) {
                     throw new InvalidDataException("Invalid entry. Activity name, description, and activity type must be strings.");
                 }
                 return this.checkRepeatedActivityAndCreate(args,activityType);
             }
         }catch (MissingArgumentException | TooManyArgumentsException | InvalidDataException e){
             return "Error: "+e.getMessage();
         }
    }

    /**
     * Hemos considerado que no se puede crear actividades con el mismo nombre de uno que ya exist.
     *
     */
    private String checkRepeatedActivityAndCreate(String[] args,String activityType){
        IActivity activity;
        for(IActivity existingActivity:activities){
            if(existingActivity.getName().equalsIgnoreCase(args[0])){
                return "Activity already exist";
            }
        }
        activity = activityType(args, activityType);
        if (activity != null) {
            activities.add(activity);
            DataPersistence.saveData(activities, USER_DATA_FILE);
            return activityVIew.renderActivity(activity);
        } else {
            return activityVIew.cannotCreate();
        }
    }
    private boolean isString(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private IActivity activityType(String[] args, String activityType){
        switch (activityType.toLowerCase()) {
                        case "generic" -> {
                            return new Activity(args[0], args[1], Integer.parseInt(args[2]), Double.parseDouble(args[3]), Integer.parseInt(args[4]),activityType);
                        }
                        case "cinema" -> {
                            return new Cinema(args[0], args[1], Integer.parseInt(args[2]), Double.parseDouble(args[3]), Integer.parseInt(args[4]),activityType);
                        }

                        case "theater" -> {
                            return new Theater(args[0], args[1], Integer.parseInt(args[2]), Double.parseDouble(args[3]), Integer.parseInt(args[4]),activityType);
                        }
                        default ->{
                            return null;}
                     }

    }


    public IActivity findActivityByName(String activityName) {
        for (IActivity activity : activities) {
            if (activity.getName().equalsIgnoreCase(activityName)) {
                return activity;
            }
        }
        activityVIew.activityNotFound();
        return null;
    }
    public List<IActivity> getActivities() {
        return activities;
    }
    public List<IActivity> checkActivities(){
        activityVIew.renderListActivities(this.getActivities());
        return this.getActivities();
    }

}
