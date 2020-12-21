package com.example.beverageapp;

public class calculatePrice {
    int orange, energy, soda, ice, cock, coffee;
    double orangePrice, energyPrice, sodaPrice, icePrice, cockPrice, coffeePrice;

    public calculatePrice(int orange, int energy, int soda, int ice, int cock, int coffee) {
        this.orange = orange;
        this.energy = energy;
        this.soda = soda;
        this.ice = ice;
        this.cock = cock;
        this.coffee = coffee;
    }
    public String format(double item){
        String form =(String) String.format("%.2f", item);
        return form;
    }

    public String getOrange() {
        orangePrice = 2.30 * orange;
        String formOrange = format(orangePrice);
        return formOrange;
    }

    public String getEnergy() {
        energyPrice = 2.50 * energy;
        String formEnergy = format(energyPrice);
        return formEnergy;
    }

    public String getSoda() {
        sodaPrice = 2.00 * soda;
        String formSoda = format(sodaPrice);
        return formSoda;
    }

    public String getIce() {
        icePrice = 1.50 * ice;
        String formIce = format(icePrice);
        return formIce;
    }

    public String getCock() {
        cockPrice = 3.20 * cock;
        String formCock = format(cockPrice);
        return formCock;
    }

    public String getCoffee() {
        coffeePrice = 3.00 * coffee;
        String formCoffee = format(coffeePrice);
        return formCoffee;
    }
    public String getTotal(){
        double total = orangePrice + energyPrice + sodaPrice + icePrice + cockPrice + coffeePrice;
        String formTotal = format(total);
        return  formTotal;
    }
}
