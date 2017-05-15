package com.example.progwjavie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Akyrt 13-May-17.
 */

// dodac implementacje MouseMotionListener aby dodac imitację przeciągania myszy(kręcenie gałką a nie klikanie)
public class Knob extends JComponent implements MouseMotionListener {
    int value = 0;
    int newValue;
    boolean pressed = false;
    boolean entered = false;
    ActionListener actionListener;//Refers to a list of ActionListener objects

    public Knob() { // konstruktor bezargumentowy
        this(0);
    }//end of constructor

    public Knob(int value) { // konstruktor
        this.value = this.newValue = 0;
       // addMouseListener(this);
       addMouseMotionListener(this);
    }//end of constructor

    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }//end addActionListener()

    //-----------------------------------------------------------------------
    //The following method removes ActionListener objects from the list
    // described above
    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }//end removeActionListener

    public Dimension getMinimumSize() {//overridden getMinimumSize()
        return new Dimension(100, 100);
    }//end getMinimumSize()

    public Dimension getPreferredSize() {//overridden getPreferredSize()
        return new Dimension(100, 100);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Dimension d = getSize();
        int r, w, h;
        r = Math.min(d.height, d.width);
        h = d.height;
        w = d.width;
        g.setColor(Color.gray);
        g.fillOval((w - r) / 2, (h - r) / 2, r - 1, r - 1);
        g.setColor(Color.green);
        if (entered) g.fillOval((w - r - 2) / 2, (h - r - 2) / 2, r - 5, r - 5);
        g.setColor(Color.red);
        int alfa = 90 - value;
        g.drawLine(w / 2, h / 2,
                w / 2 + (int) (r / 2 * Math.cos(alfa * Math.PI / 180)),
                h / 2 - (int) (r / 2 * Math.sin(alfa * Math.PI / 180)));
        // paint c.d.
        setFont(new Font("Helvetica", Font.BOLD, 14));
        g.drawString("S", w / 2 - 4, (h + r) / 2 - 4);
        g.drawString("W", (w - r) / 2, h / 2 + 5);
        g.setColor(Color.black);
        g.fill3DRect(w / 2 - 20, h / 2 - 20, 40, 20, false);
        g.setColor(Color.red);
        setFont(new Font("Helvetica", Font.BOLD, 16));
        g.drawString(new Integer(newValue).toString(), w / 2 - 10, h / 2 - 5);
    }

    public void setValue(int newValue) {
        this.newValue = newValue;
        repaint();
    }

    public int getKnobValue() {
        return (newValue);
    }

    public void mouseEntered(MouseEvent me) {
        entered = true;
        repaint();
    }

    public void mouseExited(MouseEvent me) {
        entered = false;
        pressed = false;
        repaint();
    }

   // @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent me) {
        pressed = true;
        repaint();
    }

    /*public void mouseReleased(MouseEvent me) {
        int x, y;
        if (pressed == true) {
            pressed = false;
            x = me.getX();
            y = me.getY();
            double yy = (getSize().height / 2.0 - y);
            double xx = (x - getSize().width / 2.0);
            newValue = 90 - (int) (180 / Math.PI * Math.atan(yy / xx));
            if (xx < 0) newValue += 180;
            value = newValue;
            repaint();
            if (actionListener != null) actionListener.actionPerformed(new
                    ActionEvent(this, ActionEvent.ACTION_PERFORMED, " ??? "));
        } // ignorujemy zwolnienie przycisku poza komponentem!
    }*/

   @Override
    public void mouseDragged(MouseEvent me) {
        pressed = true;
        repaint();
        int x, y;
        if (pressed == true) {
            pressed = false;
            x = me.getX();
            y = me.getY();
            double yy = (getSize().height / 2.0 - y);
            double xx = (x - getSize().width / 2.0);
            newValue = 90 - (int) (180 / Math.PI * Math.atan(yy / xx));
            if (xx < 0) newValue += 180;
            value = newValue;
            repaint();
            if (actionListener != null) actionListener.actionPerformed(new
                    ActionEvent(this, ActionEvent.ACTION_PERFORMED, " ??? "));
        } // ignorujemy zwolnienie przycisku poza komponentem!
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}