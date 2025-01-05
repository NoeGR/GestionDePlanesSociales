package Models;
public class Theater extends Activity {
    private static final int maxAgeYoungDiscountApplicable=26;
    private static final int minAgeRetireeDiscountApplicable=65;
    private static final double youngDiscount= 0.5;
    private static final double retireeDiscount= 0.7;
    public Theater(){
    }
    public Theater(String name, String description,int duration, double cost, int capacity,String activityType ){
        super(name,description,duration,cost,capacity,activityType);
    }


    private double theaterDiscount(int age, double cost){
     double discount=0.0;
     if(age<=maxAgeYoungDiscountApplicable){
         discount=cost*youngDiscount;
     } else if (age>=minAgeRetireeDiscountApplicable) {
         discount=cost*retireeDiscount;
     }
     return discount;
    }

    @Override
    public double calculateDiscount(int age, double cost) {
        return this.theaterDiscount(age,cost);
    }

}
