package com.example.progwjavie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Akyrt on 24.04.2017.
 */
public class PulseGenerator extends Thread implements PulseSource {

    int delay = 100;
    byte mode;
    boolean on = true;
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
    }

    public void setMode(byte mode) {
    }

    public byte getMode() {
        return 0;
    }

    public void halt() {
        on = false;

    }   // zatrzymaj generację


    public void setPulseDelay(int ms) {
    }

    public int getPulseDelay() {
        return 0;
    }

    public void setPulseCount(int burst) {
    }

    public void run() {

        while (true) {
            while (on) {
// fire up pulse event
// test mode & check if last pulse
// sleep for a moment
                // System.out.printf("\nI am a thread, my name is : " + getName());

                if (actionListener != null)
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "one impulse"));


                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
