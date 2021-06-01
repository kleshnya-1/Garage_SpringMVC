package ru.excers.projectGarage.models;

import lombok.Data;

@Data
public class EconomicBlock implements Comparable<EconomicBlock> {


    private  double suitableIndex=0;//определяет степень насколько заказ подходит
    private double priceForOrder;
    private double driverEarnedThisWeek;

    public EconomicBlock(double suitableIndex, double priceForOrder, double driverEarnedThisWeek) {
        this.suitableIndex = suitableIndex;
        this.priceForOrder = priceForOrder;
        this.driverEarnedThisWeek = driverEarnedThisWeek;

    }


    public void setSuitableIndex(double suitableIndex) {
        this.suitableIndex = suitableIndex;
    }

    public void setPriceForOrder(double priceForOrder) {
        this.priceForOrder = priceForOrder;
    }
    public void setDriverEarnedThisWeek(double driverEarnedThisWeek) {
        this.driverEarnedThisWeek = driverEarnedThisWeek;
    }

    @Override
    public int compareTo(EconomicBlock o) {
            if (this.suitableIndex<o.suitableIndex) return 1 ;
            if (this.suitableIndex>=o.suitableIndex) return -1;
            else  return 0;

    }

    }



