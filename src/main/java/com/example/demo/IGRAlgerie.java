package com.example.demo;

public class IGRAlgerie implements IGRStrategy {

    public double calculerIGR(double salaireBrutMensuel) {

        double salaireAnnuel = salaireBrutMensuel * 12;
        return salaireAnnuel * 0.35;

    }
}
