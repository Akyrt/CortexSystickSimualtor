package com.example.progwjavie;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Akyrt on 2017-04-13.
 */
public class SysTickGUI extends JFrame implements ActionListener {
    // instance variables - replace the example below with your own
    private JButton oneImp, kImp;
    private JTextField rejRVR, rejCVR, enterkImp, regSetCVR, regSetRVR, interrupt, stateImp, stateBurstImp, stateDelayTime;
    private JRadioButton enable, count, tickInt, genOnOFF, modeSet;
    private Integer bImp;
    int kNumber;
    private licznik_SysTick myDemoCounter;
    PulseGenerator generate;
    private JSlider sliderBurstMode, sliderSetDelay, sliderTimer;
    private JLabel etTimer;
    private Knob setDelayKnob, setBurstKnob;

    //JTextField howManyBurstImpulse;

    /**
     * Constructor for objects of class SysTickGUI
     */
    public SysTickGUI() {
        myDemoCounter = new licznik_SysTick();

        // setSize(500, 500);
        setResizable(false);    // blokuje możliwość powiększania okna
        setVisible(true);
        makeGUI();
        generate = new PulseGenerator();
        generate.addActionListener(this);
        generate.setPulseCount(5);
        pack();


    }

    public void makeGUI() {
        setLayout(new BorderLayout());

        JPanel pGorny, pDolny, pCentrum, pLewy, pPrawy; // deklaracja obiektów klasy JPanel do podziału okna na części

        /*************** Panel górny ********************************/
        pGorny = new JPanel();
        pGorny.setBackground(Color.YELLOW);             // ustalenie koloru tła części górnej okna
        add(pGorny, BorderLayout.NORTH);                // ustawienie w oknie górnej części
        JLabel etName = new JLabel(" Symulator licznika CortexM0 SysTick ");    // inicjalizacja obiektu etName wraz z zawartością
        etName.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_alarm.png")); // ustawienie ikony
        pGorny.add(etName);

        /***************** Panel lewy *******************************/
        pLewy = new JPanel();
        pLewy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
        wpiszCVR.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_settings.png"));

        pLewy.add(wpiszCVR);
        JLabel wpiszRVR = new JLabel("Ustaw: ", SwingConstants.CENTER);

        pLewy.add(wpiszRVR);
        // 3 wiersz
        regSetCVR = new JTextField(4);  // ustawienie pola tekstowego do wpisania wartości rejestru CVR
        regSetCVR.setToolTipText(" Wpisz wartość startową rejestru SYST_CVR, a następnie wciśnij Enter. "); // wyświetlenie podpowiedzi po najechaniu na pole textowe
        regSetCVR.addActionListener(this);
        pLewy.add(regSetCVR);
        regSetRVR = new JTextField(4);
        regSetRVR.setToolTipText(" Wpisz wartość rejestru SYST_RVR, a następnie wciśnij Enter. Od tej wartości zliczane są impulsy przesyłane do licznika. ");
        regSetRVR.addActionListener(this);
        pLewy.add(regSetRVR);
        // 4 wiersz
        JLabel stanRVR = new JLabel(" ");
        stanRVR.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_update.png"));
        pLewy.add(stanRVR);
        JLabel stanCVR = new JLabel("Stan:", SwingConstants.CENTER);
        pLewy.add(stanCVR);

        // 5 wiersz
        rejCVR = new JTextField(4);
        rejCVR.setPreferredSize(new Dimension(50, 50));
        rejCVR.setToolTipText(" Wyświetla aktualny stan rejestru SYST_CVR licznika. ");
        rejCVR.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rejCVR.setEditable(false);
        pLewy.add(rejCVR);
        rejRVR = new JTextField(4);
        rejRVR.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rejRVR.setEditable(false);
        rejRVR.setToolTipText(" Wyświetla aktualny stan rejestru SYST_RVR licznika. ");
        pLewy.add(rejRVR);

        /*************** Panel centrum********************/


        pCentrum = new JPanel();
        pCentrum.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(pCentrum, BorderLayout.CENTER);
        pCentrum.setLayout(new GridLayout(7, 2));
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
        enable.setOpaque(true);
        enable.addActionListener(this);
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

        // 5 wiersz

        JLabel setModeLabel = new JLabel("Mode Continous/Burst ", SwingConstants.CENTER);
        pCentrum.add(setModeLabel);

        modeSet = new JRadioButton();
        modeSet.setBackground(Color.ORANGE);
        modeSet.addActionListener(e -> {
            if (modeSet.isSelected()) {
                generate.setMode(PulseSource.BURST_MODE);
            } else generate.setMode(PulseSource.CONTINOUS_MODE);

        });
        pCentrum.add(modeSet);
        // 6 wiersz
        JLabel setDelayLabel = new JLabel("Ustaw opoznienie: ", SwingConstants.CENTER);
        setDelayLabel.setToolTipText("Ustawia opoźnienie pomiędzy kolejnymi impulsami. ");
        pCentrum.add(setDelayLabel);

        JLabel setBurstLabel = new JLabel("Ustaw wielkość paczki: ", SwingConstants.CENTER);
        setBurstLabel.setToolTipText("Ustawia wielkość paczki impulsów. ");

        pCentrum.add(setBurstLabel);

        // 7 wiersz
        sliderSetDelay = new JSlider(0, 10000, 1);
        sliderSetDelay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sliderSetDelay.setBackground(Color.ORANGE);
        sliderSetDelay.addChangeListener(e -> {
            Integer delayTime = sliderSetDelay.getValue();

            generate.setPulseDelay(delayTime);
            sliderSetDelay.setToolTipText("Opoznienie: " + delayTime.toString() + " ms.");
            stateDelayTime.setText(delayTime.toString());

        });
        pCentrum.add(sliderSetDelay);


        sliderBurstMode = new JSlider(0, 1000, 1);
        sliderBurstMode.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sliderBurstMode.setBackground(Color.ORANGE);
        sliderBurstMode.addChangeListener(e -> {
            bImp = sliderBurstMode.getValue();
            generate.setPulseCount(bImp);
            sliderBurstMode.setToolTipText("Paczka: " + bImp.toString() + " impulsow.");
            stateBurstImp.setText(bImp.toString());
        });

        pCentrum.add(sliderBurstMode);


        /*************** Panel prawy ***********************/
        pPrawy = new JPanel();
        add(pPrawy, BorderLayout.EAST);
        pPrawy.setBackground(Color.RED);
        pPrawy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pPrawy.setLayout(new GridLayout(4, 2));
        // 1 wiersz
        JLabel etInterrupt = new JLabel("Stan przerwania ", SwingConstants.CENTER);
        pPrawy.add(etInterrupt);
        interrupt = new JTextField();
        interrupt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        interrupt.setEditable(false);
        pPrawy.add(interrupt);
        // 2 wiersz
        etTimer = new JLabel("Stan licznika", SwingConstants.CENTER);
        pPrawy.add(etTimer);

        sliderTimer = new JSlider();
        sliderTimer.setValue(myDemoCounter.getCVR());
        sliderTimer.setBackground(Color.CYAN);
        sliderTimer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pPrawy.add(sliderTimer);

        // 3 wiersz
        JLabel etCount = new JLabel("CountFlag", SwingConstants.CENTER);
        etCount.setToolTipText("CountFlag zwraca 1 gdy SysTick zliczy do 0. \n Ustawia się na 0 w momencie odczytania z niego, jego wartości oraz wpisania wartości do rejestru CVR. ");
        pPrawy.add(etCount);
        count = new JRadioButton();
        count.setBackground(Color.RED);
        pPrawy.add(count);
// 4 wiersz potencjometry
        setDelayKnob = new Knob();
        setDelayKnob.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setDelayKnob.addActionListener(e -> {
            Integer newDelay = setDelayKnob.getKnobValue();
            generate.setPulseDelay(newDelay);
            stateDelayTime.setText(newDelay.toString());
        });
        pPrawy.add(setDelayKnob);

        setBurstKnob= new Knob();
        setBurstKnob.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBurstKnob.addActionListener(e -> {
            Integer newBurst = setBurstKnob.getKnobValue();
            generate.setPulseCount(newBurst);
            stateBurstImp.setText(newBurst.toString());
        });
        pPrawy.add(setBurstKnob);


        /*************** Panel dolny **********************/
        pDolny = new JPanel();
        add(pDolny, BorderLayout.SOUTH);
        pDolny.setBackground(Color.GREEN);
        oneImp = new JButton(" Pojedyńczy impuls ");
        oneImp.setToolTipText(" Po naciśnięciu przesyła jeden impuls to licznika SysTick. ");
        oneImp.addActionListener(this);
        oneImp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pDolny.add(oneImp);

        kImp = new JButton(" K impulsów ");
        kImp.setToolTipText("Po naciśnięciu tego przycisku wysyłasz K inpulsów do licznika SysTick. "); // ustawia chmurkę wyswietlajaca sie po najechaniu kursorem na klawisz
        kImp.addActionListener(this);
        kImp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pDolny.add(kImp);
        enterkImp = new JTextField(3);
        enterkImp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        enterkImp.setToolTipText("Wpisz ilość impulsów które chcesz wysłać a następnie wciśnij Enter. ");
        enterkImp.addActionListener(this);
        pDolny.add(enterkImp);

        JLabel etStanImp = new JLabel("Stan impulsów:");
        pDolny.add(etStanImp);
        stateImp = new JTextField(3);
        stateImp.setEditable(false);
        stateImp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pDolny.add(stateImp);

        JLabel dT = new JLabel("Opoznienie: ");
        pDolny.add(dT);

        stateDelayTime = new JTextField(4);
        stateDelayTime.setEditable(false);
        stateDelayTime.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pDolny.add(stateDelayTime);

        JLabel bI = new JLabel("Burst: ");
        pDolny.add(bI);

        stateBurstImp = new JTextField(3);
        stateBurstImp.setEditable(false);
        stateBurstImp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pDolny.add(stateBurstImp);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                generate.killGenThread(); // zamyka dzialanie generatora przy wyjsciu z programu
            }
        });

        validate();

    }

    void displayState() {
        rejCVR.setText("" + myDemoCounter.getCVR());
        stateImp.setText("" + myDemoCounter.getCVR());
        rejRVR.setText("" + myDemoCounter.getRVR());
        count.setSelected(myDemoCounter.isCountFlag());
        sliderTimer.setValue(myDemoCounter.getCVR());
        etTimer.setText("Stan licznika " + myDemoCounter.getCVR() + " impulsów.");

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
            sliderTimer.setValue(myDemoCounter.getCVR());
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

        if (e.getSource() == regSetCVR) {
            myDemoCounter.setCVR(Integer.parseInt(regSetCVR.getText()));
        }

        if (e.getSource() == regSetRVR) {
            myDemoCounter.setRVR(Integer.parseInt(regSetRVR.getText()));

        }

        if (e.getSource() == tickInt) {
            myDemoCounter.setTickInt(tickInt.isSelected());
        }
        if (e.getSource() == genOnOFF) {
            boolean gState = genOnOFF.isSelected();

            if (gState == true) {
                //generate.setMode(PulseSource.BURST_MODE);
                generate.trigger();
            } else generate.halt();


        }


        if (e.getSource() == generate) {       // warunek sprawdzajacy czy zostal klikniety Pojedyńczy impuls
            myDemoCounter.impuls();
            rejCVR.setText(" " + myDemoCounter.getCVR());
            if (!generate.isOn()) {
                genOnOFF.setSelected(false);
            }
        }
        displayState();

    }
}


