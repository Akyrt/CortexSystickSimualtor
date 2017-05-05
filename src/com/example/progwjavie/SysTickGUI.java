package com.example.progwjavie;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Akyrt on 2017-04-13.
 */
public class SysTickGUI extends JFrame implements ActionListener {
    // instance variables - replace the example below with your own
    JButton oneImp, kImp;
    JTextField rejRVR, rejCVR, enterkImp, rejSetCVR, rejSetRVR, interrupt, stanImp;
    JRadioButton enable, count, tickInt, genOnOFF;
    int kNumber;
    private licznik_SysTick myDemoCounter;
    PulseGenerator generate;

    /**
     * Constructor for objects of class SysTickGUI
     */
    public SysTickGUI() {
        myDemoCounter = new licznik_SysTick();

        setSize(500, 500);
        setResizable(false);    // blokuje możliwość powiększania okna
        setVisible(true);
        makeGUI();
        generate = new PulseGenerator();
        generate.addActionListener(this);

    }

    public void makeGUI() {

        // FlowLayout , GridLayout, BorderLayout
        setLayout(new BorderLayout());

        JPanel pGorny, pDolny, pCentrum, pLewy, pPrawy; // deklaracja obiektów klasy JPanel do podziału okna na części

        /*************** Panel górny ********************************/
        pGorny = new JPanel();
        pGorny.setBackground(Color.YELLOW);             // ustalenie koloru tła części górnej okna
        add(pGorny, BorderLayout.NORTH);                // ustawienie w oknie górnej części
        JLabel etName = new JLabel(" Symulator licznika CortexM0 SysTick ");    // inicjalizacja obiektu etName wraz z zawartością
        // etName.setIcon(new ImageIcon("D:\\Bluej\\Workspace\\210090\\Systick\\Systick\\ic_alarm.png"));
        etName.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_alarm.png")); // ustawienie ikony
        pGorny.add(etName);

        /***************** Panel lewy *******************************/
        pLewy = new JPanel();
        add(pLewy, BorderLayout.WEST);      // wydzielenie lewej części obszaru okna
        pLewy.setBackground(Color.CYAN);
        pLewy.setLayout(new GridLayout(5, 2)); // podzielenie części na wiersze i kolumny
        // 1 wiersz
        JLabel etCVR = new JLabel("CVR", SwingConstants.CENTER);
        pLewy.add(etCVR);
        JLabel etRVR = new JLabel("RVR", SwingConstants.CENTER);
        pLewy.add(etRVR);
        // 2 wiersz
        JLabel wpiszCVR = new JLabel("");
        // wpiszCVR.setIcon(new ImageIcon("D:\\Bluej\\Workspace\\210090\\Systick\\Systick\\ic_settings.png"));
        wpiszCVR.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_settings.png"));

        pLewy.add(wpiszCVR);
        JLabel wpiszRVR = new JLabel("Ustaw: ", SwingConstants.CENTER);

        pLewy.add(wpiszRVR);
        // 3 wiersz
        rejSetCVR = new JTextField(4);  // ustawienie pola tekstowego do wpisania wartości rejestru CVR
        rejSetCVR.setToolTipText(" Wpisz wartość startową rejestru SYST_CVR, a następnie wciśnij Enter. "); // wyświetlenie podpowiedzi po najechaniu na pole textowe
        rejSetCVR.addActionListener(this);
        pLewy.add(rejSetCVR);
        rejSetRVR = new JTextField(4);
        rejSetRVR.setToolTipText(" Wpisz wartość rejestru SYST_RVR, a następnie wciśnij Enter. Od tej wartości zliczane są impulsy przesyłane do licznika. ");
        rejSetRVR.addActionListener(this);
        pLewy.add(rejSetRVR);
        // 4 wiersz
        JLabel stanRVR = new JLabel(" ");
        // stanRVR.setIcon(new ImageIcon("D:\\Bluej\\Workspace\\210090\\Systick\\Systick\\ic_update.png"));
        stanRVR.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_update.png"));
        pLewy.add(stanRVR);
        JLabel stanCVR = new JLabel("Stan:", SwingConstants.CENTER);
        pLewy.add(stanCVR);

        // 5 wiersz
        rejCVR = new JTextField(4);
        rejCVR.setPreferredSize(new Dimension(50, 50));
        rejCVR.setToolTipText(" Wyświetla aktualny stan rejestru SYST_CVR licznika. ");
        pLewy.add(rejCVR);
        rejRVR = new JTextField(4);
        rejRVR.setToolTipText(" Wyświetla aktualny stan rejestru SYST_RVR licznika. ");
        pLewy.add(rejRVR);

        /*************** Panel centrum********************/

        pCentrum = new JPanel();
        add(pCentrum, BorderLayout.CENTER);
        pCentrum.setLayout(new GridLayout(4, 2));
        pCentrum.setBackground(Color.ORANGE);
        // 1 wiersz
        JLabel nic = new JLabel(" ");
        pCentrum.add(nic);
        JLabel ikona = new JLabel();
        // ikona.setIcon(new ImageIcon("D:\\Bluej\\Workspace\\210090\\Systick\\Systick\\ic_check.png"));
        ikona.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_check.png"));
        pCentrum.add(ikona);
        // 2 wiersz
        JLabel etEnable = new JLabel("EnableFlag", SwingConstants.CENTER);
        pCentrum.add(etEnable);
        enable = new JRadioButton();
        enable.setToolTipText("Zaznacz aby uaktywnić bit EnableFlag. ");
        enable.setBackground(Color.ORANGE);
        pCentrum.add(enable);
        // 3 wiersz
        JLabel etTick = new JLabel("TickInt", SwingConstants.CENTER);
        pCentrum.add(etTick);
        tickInt = new JRadioButton();
        tickInt.setBackground(Color.ORANGE);
        tickInt.addActionListener(this);
        tickInt.setToolTipText(" Zaznacz aby uaktywnić przerwania licznika SysTick w momencie zliczenia wartości do 0. ");
        pCentrum.add(tickInt);
        // 4 wiersz
        JLabel etGenerator = new JLabel("Generator ON/OFF ", SwingConstants.CENTER);
        pCentrum.add(etGenerator);
        genOnOFF = new JRadioButton();
        genOnOFF.setBackground(Color.ORANGE);
        genOnOFF.addActionListener(this);
        pCentrum.add(genOnOFF);

        /*************** Panel prawy ***********************/
        pPrawy = new JPanel();
        add(pPrawy, BorderLayout.EAST);
        pPrawy.setBackground(Color.RED);
        pPrawy.setLayout(new GridLayout(2, 2));
        // 1 wiersz
        JLabel etInterrupt = new JLabel("Stan przerwania ", SwingConstants.CENTER);
        pPrawy.add(etInterrupt);
        interrupt = new JTextField();
        pPrawy.add(interrupt);
        // 2 wiersz
        JLabel etCount = new JLabel("CountFlag", SwingConstants.CENTER);
        etCount.setToolTipText("CountFlag zwraca 1 gdy SysTick zliczy do 0. \n Ustawia się na 0 w momencie odczytania z niego, jego wartości oraz wpisania wartości do rejestru CVR. ");
        pPrawy.add(etCount);
        count = new JRadioButton();
        count.setBackground(Color.RED);
        pPrawy.add(count);
        enable.addActionListener(this);

        /*************** Panel dolny **********************/
        pDolny = new JPanel();
        add(pDolny, BorderLayout.SOUTH);
        pDolny.setBackground(Color.GREEN);
        oneImp = new JButton(" Pojedyńczy impuls ");
        oneImp.setToolTipText(" Po naciśnięciu przesyła jeden impuls to licznika SysTick. ");
        oneImp.addActionListener(this);
        pDolny.add(oneImp);

        kImp = new JButton(" K impulsów ");
        kImp.setToolTipText("Po naciśnięciu tego przycisku wysyłasz K inpulsów do licznika SysTick. "); // ustawia chmurkę wyswietlajaca sie po najechaniu kursorem na klawisz
        kImp.addActionListener(this);
        pDolny.add(kImp);
        enterkImp = new JTextField(3);
        enterkImp.setToolTipText("Wpisz ilość impulsów które chcesz wysłać a następnie wciśnij Enter. ");
        enterkImp.addActionListener(this);
        pDolny.add(enterkImp);

        JLabel etStanImp = new JLabel("Stan impulsów:");
        pDolny.add(etStanImp);
        stanImp = new JTextField(3);
        pDolny.add(stanImp);


        validate();

    }

    void displayState() {
        rejCVR.setText("" + myDemoCounter.getCVR());
        stanImp.setText("" + myDemoCounter.getCVR());
        rejRVR.setText("" + myDemoCounter.getRVR());
        count.setSelected(myDemoCounter.isCountFlag());
        if (myDemoCounter.getInterrupt() == true) {
            interrupt.setText(" ** Przerwanie ** ");
            interrupt.setBackground(Color.ORANGE);

        } else if (myDemoCounter.getInterrupt() == false) {
            interrupt.setText(" Brak przerwania ");
            interrupt.setBackground(Color.WHITE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == oneImp) {       // warunek sprawdzajacy czy zostal klikniety Pojedyńczy impuls
            myDemoCounter.impuls();
            rejCVR.setText(" " + myDemoCounter.getCVR());
        }
        if (e.getSource() == enable) {       // warunek sprawdzajacy czy zostal zaznaczony radiobutton EnableFlag
            myDemoCounter.setEnableFlag(enable.isSelected());
            rejCVR.setText(" " + myDemoCounter.getCVR());
        }


        if (e.getSource() == kImp) {
            for (int i = 0; i <= kNumber; i++) {
                myDemoCounter.impuls();
            }
            displayState();

        }
        if (e.getSource() == enterkImp) {
            kNumber = (Integer.parseInt(enterkImp.getText())) - 1;

        }

        if (e.getSource() == rejSetCVR) {
            myDemoCounter.setCVR(Integer.parseInt(rejSetCVR.getText()));
        }

        if (e.getSource() == rejSetRVR) {
            myDemoCounter.setRVR(Integer.parseInt(rejSetRVR.getText()));

        }

        if (e.getSource() == tickInt) {
            myDemoCounter.setTickInt(tickInt.isSelected());
        }
        if (e.getSource() == genOnOFF) {
            boolean gState = genOnOFF.isSelected();
            if (gState == true) generate.trigger();
            else generate.halt();



        }


        if (e.getSource() == generate) {       // warunek sprawdzajacy czy zostal klikniety Pojedyńczy impuls
            myDemoCounter.impuls();
            rejCVR.setText(" " + myDemoCounter.getCVR());
        }
        displayState();

    }
}


