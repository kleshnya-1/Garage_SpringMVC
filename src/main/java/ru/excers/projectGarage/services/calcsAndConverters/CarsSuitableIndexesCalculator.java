package ru.excers.projectGarage.services.calcsAndConverters;

public class CarsSuitableIndexesCalculator {





    public double executeIndexCalculation(double indexOfClientParkMoneyRelationship
            , double diffCoeffForCorrection, double price, double salaryForWeek){



        if (indexOfClientParkMoneyRelationship == 0) indexOfClientParkMoneyRelationship =0.000000001;
        if (price == 0) price =0.000000001;
        if (diffCoeffForCorrection == 0) diffCoeffForCorrection =0.000000001;
        if (salaryForWeek == 0) salaryForWeek =0.000000001;
        double indexOfClientParkMoneyRelationshipMirror = indexOfClientParkMoneyRelationship-1;
        if (indexOfClientParkMoneyRelationshipMirror<0)
            indexOfClientParkMoneyRelationshipMirror = -indexOfClientParkMoneyRelationshipMirror;


        return ((indexOfClientParkMoneyRelationship/price)+ (indexOfClientParkMoneyRelationshipMirror/(salaryForWeek*diffCoeffForCorrection)));

    }
}
