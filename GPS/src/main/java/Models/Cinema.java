package Models;
public class Cinema extends Activity {
    private static final int maxAgeYoungDiscountApplicable=21;
    private static final double youngDiscount= 0.5;
    public Cinema(){

    }
    public Cinema(String name, String description,int duration, double cost, int capacity, String activityType ){
        super(name,description,duration,cost,capacity,activityType);
    }


    private double cinemaDiscount(int age, double cost){
        double discount=0.0;
        if(age<=maxAgeYoungDiscountApplicable){
            discount=cost*youngDiscount;
        }
        return discount;
    }

    @Override
    public double calculateDiscount(int age, double cost) {
        return this.cinemaDiscount(age,cost);
    }

}
