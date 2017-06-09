package com.example.progwjavie;

import java.util.Observer;
import java.util.Observable;

/**
 * Write a description of class CortexM0SysTick here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class licznik_SysTick extends Observable {
    // instance variables - replace the example below with your own
    private int SYST_RVR, SYST_CVR;
    private boolean enableFlag, countFlag, tickInt, przerwanie;


    /**
     * Constructor for objects of class CortexM0SysTick
     */
    public licznik_SysTick() {
        SYST_RVR = 10;
        SYST_CVR = 5;
        tickInt = false;
    }

    /**
     * Zmiana wartości rejestru przeładowania, wraz z ograniczeniem do 24 bitów rejestru, ponieważ int jest 32 bitowy.
     */
    public void setRVR(int rejestrRVR) {
        if (SYST_RVR >= 0 && SYST_RVR < (1 << 24))
            this.SYST_RVR = rejestrRVR;

    }

    /**
     * Zmiana wartości rejestru CVR, wraz z ograniczeniem do 24 bitów rejestru, ponieważ int jest 32 bitowy.
     */
    public void setCVR(int rejestrCVR) {

        if (SYST_CVR >= 0 && SYST_CVR < (1 << 24)) {
            countFlag = false;      // rejestr typu boolowskiego wiec w javie trzeba wpisac false a nie zero
            this.SYST_CVR = rejestrCVR;
        }
    }

    public void setTickInt(Boolean set) {
        tickInt = set;
    }

    public int getRVR() {
        return SYST_RVR;

    }

    public int getCVR() {
        return SYST_CVR;

    }

    public boolean getInterrupt() {
        return przerwanie;
    }

    public void setEnableFlag(boolean flag) {
        enableFlag = flag;
    }

    public boolean isEnableFlag() {
        countFlag = false;
        return enableFlag;


    }

    public boolean isCountFlag()        // w konwencji javy zamiast get, dla typu boolowskiego uzywa sie is
    {
        boolean tmp = countFlag;
        countFlag = false;
        return tmp;


    }

    public void impuls() {
        if (!enableFlag) return;

        if (SYST_CVR == 0) {
            SYST_CVR = SYST_RVR;
            countFlag = true;

            if ((countFlag == true) && (tickInt == true)) {
                przerwanie = true;
            } else przerwanie = false;

            countFlag = false;

            setChanged();
            notifyObservers();
            return;

        }

        if (SYST_CVR == 1) {

            countFlag = true;

        }
        SYST_CVR--;

        if ((countFlag == false) && (tickInt == false)) przerwanie = false;
        if ((countFlag == false) && (tickInt == true)) przerwanie = false;
        /* zadanie: rejestrCVR ma być zerowany w momencie gdy jego wartosc wynosi wartosc z rejestruRVR */

        if (SYST_CVR == SYST_RVR) {
            //countFlag = false;
            isCountFlag();
        }
    }

    public String toString() {
        return ("\nenableFlag = " + enableFlag +
                " countFlag = " + countFlag +
                " tickintFlag = " + tickInt +
                "\nCVR = " + SYST_CVR +
                "\nRVR = " + SYST_RVR);

    }


}
