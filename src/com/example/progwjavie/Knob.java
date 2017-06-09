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
    private double theta;
    private static final int radius = 50;
    private static final int spotRadius = 10;
    String message;
    ActionListener actionListener;//Refers to a list of ActionListener objects

    public Knob() { // konstruktor bezargumentowy
        this(0);
    }//end of constructor

    public Knob(int value) { // konstruktor
        this.value = this.newValue = 0;
       // addMouseListener(this);
       addMouseMotionListener(this);
        message = new String(" ");
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
        g.setColor(Color.black);
      //  g.fill3DRect(w / 2 - 20, h / 2 - 20, 40, 20, false);
      //  g.setColor(Color.red);
        setFont(new Font("Helvetica", Font.ITALIC, 12));
        g.drawString(message, 5, 15);
        Point pt = getSpotCenter();
        int xc = (int)pt.getX();
        int yc = (int)pt.getY();
        g.setColor(Color.GREEN);
        g.fillOval(xc+40, yc-10,
                2*spotRadius, 2*spotRadius);

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
    public void setMessage(String msg) {
        message = msg;
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
            theta = Math.atan2(xx,yy);
            newValue = 90 - (int) (180 / Math.PI * Math.atan(yy / xx));
            if (xx < 0) newValue += 180;
            value = newValue;

            repaint();
            if (actionListener != null) actionListener.actionPerformed(new
                    ActionEvent(this, ActionEvent.ACTION_PERFORMED, " ??? "));
        } // ignorujemy zwolnienie przycisku poza komponentem!
    }
public void resetKnoob(){
       value = 0;
       theta = 0;
       repaint();
}
    private Point getSpotCenter() {

        // Calculate the center point of the spot RELATIVE to the
        // center of the of the circle.

        int r = 50 - 10;

        int xcp = (int)(r * Math.sin(theta));
        int ycp = (int)(r * Math.cos(theta));

        // Adjust the center point of the spot so that it is offset
        // from the center of the circle.  This is necessary becasue
        // 0,0 is not actually the center of the circle, it is  the
        // upper left corner of the component!
        int xc = radius + xcp;
        int yc = radius - ycp;

        // Create a new Point to return since we can't
        // return 2 values!
        return new Point(xc,yc);
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}