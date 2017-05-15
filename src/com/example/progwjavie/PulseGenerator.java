package com.example.progwjavie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Akyrt on 24.04.2017.
 */
public class PulseGenerator extends Thread implements PulseSource {

    int delay = 100;
    long burstCount; // liczba impulsow w trybie paczkowym
    long burst;
    byte mode;
    boolean on = false, genAlive = true;
    ActionListener actionListener;//Refers to a list of ActionListener objects

    //
    public PulseGenerator() {
        //  super("Tom ");
        mode = PulseSource.CONTINOUS_MODE;
        start();
    }


    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }//end addActionListener()

    // public void addActionListener(ActionListener pl) {
    //  }        // upraszczamy (było PulseListener)

    public void removeActionListener(ActionListener pl) {
    }    // upraszczamy (było PulseListener)

    public void trigger() {
        on = true;
        burstCount = burst;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public byte getMode() {
        return 0;
    }

    public void halt() {
        on = false;


    }   // zatrzymaj generację


    public void setPulseDelay(int ms) {
        delay = ms;
    }


    public int getPulseDelay() {
        return delay;
    }

    public void setPulseCount(int burst) {
        this.burst = burst;
    }

    public boolean isOn(){
        return on;
    }
    public void run() {

        while (genAlive) {
            if (on) {
// fire up pulse event
// test mode & check if last pulse
// sleep for a moment
                // System.out.printf("\nI am a thread, my name is : " + getName());

                if (mode == PulseGenerator.BURST_MODE) {
                    burstCount--;
                    if (burstCount == 0) {
                        on = false;
                    }
                }

                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "one impulse"));
                }

                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public void killGenThread() {

        genAlive = false;

    }
}
