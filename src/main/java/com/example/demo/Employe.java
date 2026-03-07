package com.example.demo;

public class Employe {

    private String cin;
    private double salaireBrutMensuel;

    private IGRStrategy strategy;

    public Employe(String cin, double salaireBrutMensuel, IGRStrategy strategy) {
        this.cin = cin;
        this.salaireBrutMensuel = salaireBrutMensuel;
        this.strategy = strategy;
    }

    public double calculerIGR() {
        return strategy.calculerIGR(salaireBrutMensuel);
    }

    public double getSalaireNetMensuel() {
        return salaireBrutMensuel - calculerIGR()/12;
    }
}