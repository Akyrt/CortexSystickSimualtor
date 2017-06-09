package com.example.progwjavie;

import java.awt.event.ActionListener;

/**
 * Created by Akyrt on 24.04.2017.
 */
public interface PulseSource {
    final static byte BURST_MODE = 0;
    final static byte CONTINOUS_MODE = 1;

    void addActionListener(ActionListener pl);        // upraszczamy (było PulseListener)

    void removeActionListener(ActionListener pl);    // upraszczamy (było PulseListener)

    void trigger();

    void setMode(byte mode);

    byte getMode();

    void halt();    // zatrzymaj generację

    void setPulseDelay(int ms);

    int getPulseDelay();

    void setPulseCount(int burst);
}