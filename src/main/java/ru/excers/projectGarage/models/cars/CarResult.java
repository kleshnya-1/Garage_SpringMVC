package ru.excers.projectGarage.models.cars;


public class CarResult extends Car {
    private String driverName;
    private double earnedThisWeek;

    private boolean is_male;



    public void setDriver_id(int id){

        super.setDriver_id(id) ;
    }


    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setEarnedThisWeek(double earnedThisWeek) {
        this.earnedThisWeek = earnedThisWeek;
    }

    public double getEarnedThisWeek() {
        return earnedThisWeek;
    }

    @Override
    public String toString() {

        return super.toString()+"\n";
    }


    public boolean isIs_male() {
        return is_male;
    }

    public void setIs_male(boolean is_male) {
        this.is_male = is_male;
    }
}
