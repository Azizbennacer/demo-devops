package com.example.demo;

public class IGRTunisie implements IGRStrategy {

    public double calculerIGR(double salaireBrutMensuel) {

        double salaireAnnuel = salaireBrutMensuel * 12;

        if (salaireAnnuel < 20000)
            return salaireAnnuel * 0.05;

        else if (salaireAnnuel < 120000)
            return salaireAnnuel * 0.20;

        else
            return salaireAnnuel * 0.42;
    }
}