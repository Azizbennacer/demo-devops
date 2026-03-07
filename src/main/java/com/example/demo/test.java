package com.example.demo;

import java.util.Scanner;

public class test {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Choisir le pays:");
        System.out.println("1 - Tunisie");
        System.out.println("2 - Algerie");

        int choix = sc.nextInt();

        IGRStrategy strategy;

        if (choix == 1) {
            strategy = new IGRTunisie();
        } else {
            strategy = new IGRAlgerie();
        }

        Employe emp = new Employe("123456", 3000, strategy);

        System.out.println("Salaire net: " + emp.getSalaireNetMensuel());
    }
}
