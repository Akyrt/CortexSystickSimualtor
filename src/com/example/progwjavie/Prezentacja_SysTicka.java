package com.example.progwjavie;

import java.util.Observer;
import java.util.Observable;

/**
 * Write a description of class Prezentacja_SysTicka here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


public class Prezentacja_SysTicka implements Observer {
    // instance variables - replace the example below with your own
    private licznik_SysTick myDemoCounter;

    /**
     * Constructor for objects of class Prezentacja_SysTicka
     */
    public Prezentacja_SysTicka() {

        myDemoCounter = new licznik_SysTick();
        myDemoCounter.addObserver(this);
        makeCounterShow(); // metoda reprezentująca działanie licznika

    }


    /******************************************************************
     * metoda main - klasa wiodąca
     */
    public static void main(String[] arg) {
        // new Prezentacja_SysTicka();
        new SysTickGUI();
    }

    /**************************************************************
     * Metoda makeCounterShow() - odpowiedzialna jest za prezentacje działania licznika Systick
     *
     */
    public void makeCounterShow() {
        komunikat("Start prezentacji licznika SysTick");

        komunikat("Stan licznika: " + myDemoCounter);

        myDemoCounter.setEnableFlag(true);
        komunikat("Przesylam serię 4 impulsów");
        for (int i = 0; i < 4; i++) {
            myDemoCounter.impuls();
            komunikat("po " + (i + 1) + " - tym impulsie stan aktualny = " + myDemoCounter);
        }
        komunikat("Przesylam kolejną serię 4 impulsów");
        for (int i = 0; i < 5; i++) {
            myDemoCounter.impuls();
            komunikat("po " + (i + 1) + " - tym impulsie stan aktualny= " + myDemoCounter);
        }


    }

    public void update(Observable subject, Object arg) {
        komunikat("---------- Nastapilo przerwanie ---------- ");
    }


    /*
     * Metoda stworzona na potrzeby szybszego wyświetlania znaków
     */void komunikat(String wiadomosc) {
        System.out.println(wiadomosc);
    }


}




