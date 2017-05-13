package com.example.progwjavie;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Akyrt 13-May-17.
 */

public class Knob extends Component implements MouseListener {
    int azymut = 0;
    int newAzymut;
    boolean pressed = false;
    boolean entered = false;
    ActionListener actionListener;//Refers to a list of ActionListener objects

    public Knob() { // konstruktor bezargumentowy
        this(0);
    }//end of constructor

    public Knob(int azymut) { // konstruktor
        this.azymut = this.newAzymut = 0;
        addMouseListener(this);
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

    public void paint(Graphics g) {
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
        int alfa = 90 - azymut;
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
        g.drawString(new Integer(newAzymut).toString(), w / 2 - 10, h / 2 - 5);
    }

    public void setAzymut(int newAzymut) {
        this.newAzymut = newAzymut;
        repaint();
    }

    public int getAzymut() {
        return (newAzymut);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent me) {
        pressed = true;
        repaint();
    }

    public void mouseReleased(MouseEvent me) {
        int x, y;
        if (pressed == true) {
            pressed = false;
            x = me.getX();
            y = me.getY();
            double yy = (getSize().height / 2.0 - y);
            double xx = (x - getSize().width / 2.0);
            newAzymut = 90 - (int) (180 / Math.PI * Math.atan(yy / xx));
            if (xx < 0) newAzymut += 180;
            azymut = newAzymut;
            repaint();
            if (actionListener != null) actionListener.actionPerformed(new
                    ActionEvent(this, ActionEvent.ACTION_PERFORMED, " ??? "));
        } // ignorujemy zwolnienie przycisku poza komponentem!
    }
}