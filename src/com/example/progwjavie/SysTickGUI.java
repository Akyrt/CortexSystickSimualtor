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
    private JButton oneImp, kImp, knobReset;
    private JTextField rejRVR, rejCVR, enterkImp, regSetCVR, regSetRVR, interrupt, stateImp, stateBurstImp, stateDelayTime;
    private JRadioButton enable, count, tickInt, genOnOFF, modeSet;
    int kNumber;
    private licznik_SysTick myDemoCounter;
    PulseGenerator generate;
    private JSlider sliderTimer;
    private JLabel etTimer;
    private Knob setDelayKnob360, setDelayKnob3600, setBurstKnob360, setBurstKnob3600;
    Integer newBurst360, newBurst3600, newDelay360, newDelay3600;
    Font font, fontDescriptions, fontCVRRVR;


    /**
     * Constructor for objects of class SysTickGUI
     */
    public SysTickGUI() {
        myDemoCounter = new licznik_SysTick();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        designGUI();

        // setSize(500, 500);
        setResizable(true);    // blokuje możliwość powiększania okna
        setVisible(true);
        makeGUI();


        generate = new PulseGenerator();
        generate.addActionListener(this);
        generate.setPulseCount(5);
        newBurst360 = 0;
        newBurst3600 = 0;
        newDelay360 = 0;
        newDelay3600 = 0;
        pack();


    }

    public void makeGUI() {


        setLayout(new BorderLayout());

        JPanel pGorny, pDolny, pCentrum, pLewy, pPrawy; // deklaracja obiektów klasy JPanel do podziału okna na części

        font = new Font("SansSerif", Font.BOLD, 20);    // ustawienia czcionki w programie
        fontDescriptions = new Font("SansSerif", Font.BOLD, 15);    // ustawienia czcionki w programie
        fontCVRRVR = new Font("SansSerif", Font.BOLD, 30);    // ustawienia czcionki w programie

        /*************** Panel górny ********************************/
        pGorny = new JPanel();
        pGorny.setBackground(Color.YELLOW);             // ustalenie koloru tła części górnej okna
        add(pGorny, BorderLayout.NORTH);                // ustawienie w oknie górnej części
        JLabel etName = new JLabel(" Symulator licznika CortexM0 SysTick ");    // inicjalizacja obiektu etName wraz z zawartością
        etName.setFont(font);
        etName.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_alarm.png")); // ustawienie ikony
        pGorny.add(etName);

        /***************** Panel lewy *******************************/
        pLewy = new JPanel();
        pLewy.setBorder(BorderFactory.createLineBorder(Color.BLACK));   // ustawienie krawędzi bordera
        add(pLewy, BorderLayout.WEST);      // wydzielenie lewej części obszaru okna
        pLewy.setBackground(Color.CYAN);
        pLewy.setLayout(new GridLayout(5, 2)); // podzielenie części na wiersze i kolumny
        // 1 wiersz
        JLabel etCVR = new JLabel("CVR", SwingConstants.CENTER);
        etCVR.setFont(fontDescriptions);
        pLewy.add(etCVR);
        JLabel etRVR = new JLabel("RVR", SwingConstants.CENTER);
        etRVR.setFont(fontDescriptions);
        pLewy.add(etRVR);
        // 2 wiersz
        JLabel wpiszCVR = new JLabel("");
        wpiszCVR.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_settings.png"));
        wpiszCVR.setFont(fontDescriptions);

        pLewy.add(wpiszCVR);
        JLabel wpiszRVR = new JLabel("Ustaw: ", SwingConstants.CENTER);
        wpiszRVR.setFont(fontDescriptions);
        pLewy.add(wpiszRVR);
        // 3 wiersz
        regSetCVR = new JTextField(4);  // ustawienie pola tekstowego do wpisania wartości rejestru CVR
        regSetCVR.setToolTipText(" Wpisz wartość startową rejestru SYST_CVR, a następnie wciśnij Enter. "); // wyświetlenie podpowiedzi po najechaniu na pole textowe
        regSetCVR.setFont(font);    // ustawienie czcionki
        regSetCVR.setHorizontalAlignment(SwingConstants.CENTER);    // wyśrodkowanie napisu
        regSetCVR.addActionListener(this);
        pLewy.add(regSetCVR);
        regSetRVR = new JTextField(4);
        regSetRVR.setFont(font);
        regSetRVR.setHorizontalAlignment(SwingConstants.CENTER);
        regSetRVR.setToolTipText(" Wpisz wartość rejestru SYST_RVR, a następnie wciśnij Enter. Od tej wartości zliczane są impulsy przesyłane do licznika. ");
        regSetRVR.addActionListener(this);
        pLewy.add(regSetRVR);
        // 4 wiersz
        JLabel stanRVR = new JLabel(" ");
        stanRVR.setIcon(new ImageIcon("D:\\IntelliJ\\IntelliJ_Workspace\\licznik_SysTick\\icons\\ic_update.png"));
        //  String pathhh = SysTickGUI.class.getClassLoader().getResource("/icons/ic_update.png").toString();
        // stanRVR.setIcon(new ImageIcon(pathhh));
        pLewy.add(stanRVR);
        JLabel stanCVR = new JLabel("Stan:", SwingConstants.CENTER);
        stanCVR.setFont(fontDescriptions);

        pLewy.add(stanCVR);

        // 5 wiersz
        rejCVR = new JTextField(4);
        rejCVR.setPreferredSize(new Dimension(50, 50));
        rejCVR.setFont(fontCVRRVR);
        rejCVR.setToolTipText(" Wyświetla aktualny stan rejestru SYST_CVR licznika. ");
        rejCVR.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rejCVR.setEditable(false);
        pLewy.add(rejCVR);
        rejRVR = new JTextField(4);
        rejRVR.setFont(fontCVRRVR);
        rejRVR.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rejRVR.setEditable(false);
        rejRVR.setToolTipText(" Wyświetla aktualny stan rejestru SYST_RVR licznika. ");
        pLewy.add(rejRVR);

        /*************** Panel centrum********************/


        pCentrum = new JPanel();
        pCentrum.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(pCentrum, BorderLayout.CENTER);
        pCentrum.setLayout(new GridLayout(5, 2));
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
        etEnable.setFont(fontDescriptions);
        pCentrum.add(etEnable);
        enable = new JRadioButton();
        enable.setHorizontalAlignment(SwingConstants.CENTER);
        enable.setToolTipText("Zaznacz aby uaktywnić bit EnableFlag. ");
        enable.setBackground(Color.ORANGE);
        enable.setOpaque(true);
        enable.addActionListener(this);
        pCentrum.add(enable);
        // 3 wiersz
        JLabel etTick = new JLabel("TickInt", SwingConstants.CENTER);
        etTick.setFont(fontDescriptions);
        pCentrum.add(etTick);
        tickInt = new JRadioButton();
        tickInt.setHorizontalAlignment(SwingConstants.CENTER);
        tickInt.setBackground(Color.ORANGE);
        tickInt.addActionListener(this);
        tickInt.setToolTipText(" Zaznacz aby uaktywnić przerwania licznika SysTick w momencie zliczenia wartości do 0. ");
        pCentrum.add(tickInt);
        // 4 wiersz
        JLabel etGenerator = new JLabel("Generator ON/OFF ", SwingConstants.CENTER);
        etGenerator.setFont(fontDescriptions);
        pCentrum.add(etGenerator);
        genOnOFF = new JRadioButton();
        genOnOFF.setHorizontalAlignment(SwingConstants.CENTER);
        genOnOFF.setToolTipText("Uruchamia generator impulsów. ");
        genOnOFF.setBackground(Color.ORANGE);
        genOnOFF.addActionListener(this);
        pCentrum.add(genOnOFF);

        // 5 wiersz

        JLabel setModeLabel = new JLabel("Mode Continous/Burst ", SwingConstants.CENTER);
        pCentrum.add(setModeLabel);
        setModeLabel.setFont(fontDescriptions);
        modeSet = new JRadioButton();
        modeSet.setToolTipText("Uruchamia tryb burst, czyli tryb wysyłania paczki impulsów.\n Po wysłaniu paczki impulsów generacja jest zatrzymywana. ");
        modeSet.setHorizontalAlignment(SwingConstants.CENTER);
        modeSet.setBackground(Color.ORANGE);
        modeSet.addActionListener(e -> {
            if (modeSet.isSelected()) {
                generate.setMode(PulseSource.BURST_MODE);
            } else generate.setMode(PulseSource.CONTINOUS_MODE);

        });
        pCentrum.add(modeSet);


        /*************** Panel prawy ***********************/
        pPrawy = new JPanel();
        add(pPrawy, BorderLayout.EAST);
        pPrawy.setBackground(Color.RED);
        pPrawy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pPrawy.setLayout(new GridLayout(6, 2));
        // 1 wiersz
        JLabel etInterrupt = new JLabel("Stan przerwania ", SwingConstants.CENTER);
        etInterrupt.setFont(fontDescriptions);
        pPrawy.add(etInterrupt);
        interrupt = new JTextField();
        interrupt.setFont(font);
        interrupt.setHorizontalAlignment(SwingConstants.CENTER);
        interrupt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        interrupt.setEditable(false);
        pPrawy.add(interrupt);
        // 2 wiersz
        etTimer = new JLabel("Stan licznika", SwingConstants.CENTER);
        etTimer.setFont(fontDescriptions);
        pPrawy.add(etTimer);

        sliderTimer = new JSlider();
        sliderTimer.setMaximum(10);
        sliderTimer.setToolTipText("Przedstawia graficznie ile pozostało impulsów do wyzerowania. ");
        sliderTimer.setValue(myDemoCounter.getCVR());
        sliderTimer.setBackground(Color.CYAN);
        sliderTimer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pPrawy.add(sliderTimer);

        // 3 wiersz
        JLabel etCount = new JLabel("CountFlag", SwingConstants.CENTER);
        etCount.setFont(fontDescriptions);
        etCount.setToolTipText("CountFlag zwraca 1 gdy SysTick zliczy do 0. ");
        pPrawy.add(etCount);
        count = new JRadioButton();
        count.setHorizontalAlignment(SwingConstants.CENTER);
        count.setBackground(Color.RED);
        pPrawy.add(count);
// 4 wiersz
        JLabel zakr_1 = new JLabel("Zakresy: 0 - 360 ", SwingConstants.CENTER);
        zakr_1.setFont(fontDescriptions);
        pPrawy.add(zakr_1);

        JLabel zakr_2 = new JLabel("  0 - 3600 ", SwingConstants.CENTER);
        zakr_2.setFont(fontDescriptions);
        pPrawy.add(zakr_2);
// 5 wiersz potencjometry od opcji ustawiającej czas pomiędzy kolejnymi impulsami

        setDelayKnob360 = new Knob();
        setDelayKnob360.setMessage("Set delay:");
        setDelayKnob360.setToolTipText("Ustawia opóźnienie z którym będzie wysłany kolejny impuls w ms. ");
        setDelayKnob360.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setDelayKnob360.addActionListener(e -> {
            if (newDelay3600 <= 3600 && newDelay360 <= 3600) {
                newDelay360 = newDelay3600 + setDelayKnob360.getKnobValue();
                generate.setPulseDelay(newDelay360);
                stateDelayTime.setText(newDelay360.toString());
            } else {
                newDelay3600 = 0;
                newDelay360 = 0;
            }
        });
        pPrawy.add(setDelayKnob360);

        setDelayKnob3600 = new Knob();
        setDelayKnob3600.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setDelayKnob3600.setToolTipText("Ustawia opóźnienie z którym będzie wysłany kolejny impuls w ms. ");
        setDelayKnob3600.addActionListener(e -> {
            if (newDelay3600 <= 3600 && newDelay360 <= 3600) {

                newDelay3600 = newDelay360 + setDelayKnob3600.getKnobValue() * 10;
                generate.setPulseDelay(newDelay3600);
                stateDelayTime.setText(newDelay3600.toString());
            } else {
                newDelay3600 = 0;
                newDelay360 = 0;
            }
        });
        pPrawy.add(setDelayKnob3600);

        // 6 wiersz potencjometry od opcji BURST
        setBurstKnob360 = new Knob();
        setBurstKnob360.setMessage("Set burst:");
        setBurstKnob360.setToolTipText("Ustawia wielkość paczki impulsów dla trybu BURST. ");
        setBurstKnob360.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBurstKnob360.addActionListener(e -> {
            if (newBurst3600 <= 3600 && newBurst360 <= 3600) {
                newBurst360 = newBurst3600 + setBurstKnob360.getKnobValue();
                generate.setPulseCount(newBurst360);
                stateBurstImp.setText(newBurst360.toString());
            } else {
                newBurst360 = 0;
                newBurst3600 = 0;
            }
        });
        pPrawy.add(setBurstKnob360);

        setBurstKnob3600 = new Knob();
        setBurstKnob3600.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBurstKnob3600.setToolTipText("Ustawia wielkość paczki impulsów dla trybu BURST. ");
        setBurstKnob3600.addActionListener(e -> {
            if (newBurst3600 <= 3600 && newBurst360 <= 3600) {
                newBurst3600 = newBurst360 + setBurstKnob3600.getKnobValue() * 10;
                generate.setPulseCount(newBurst3600);
                stateBurstImp.setText(newBurst3600.toString());
            } else {
                newBurst360 = 0;
                newBurst3600 = 0;
            }
        });
        pPrawy.add(setBurstKnob3600);


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

        knobReset = new JButton();
        knobReset.setText(" Reset gałek ");
        knobReset.setToolTipText(" Ustawia gałki w pozycji początkowej.");
        knobReset.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        knobReset.addActionListener(e -> {

            setDelayKnob360.resetKnoob();
            setDelayKnob3600.resetKnoob();
            setBurstKnob360.resetKnoob();
            setBurstKnob3600.resetKnoob();
            newBurst360 = 0;
            newBurst3600 = 0;
            newDelay360 = 0;
            newDelay3600 = 0;
            genOnOFF.setSelected(false);
            generate.halt();
            modeSet.setSelected(false);
            generate.setMode(PulseSource.CONTINOUS_MODE);
            generate.setPulseCount(newBurst3600);
            stateBurstImp.setText(newBurst3600.toString());
            generate.setPulseCount(newDelay3600);
            stateDelayTime.setText(newDelay3600.toString());
            displayState();
        });
        pDolny.add(knobReset);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int o = JOptionPane.showConfirmDialog(
                        e.getWindow(),
                        "Chcesz zakoczńczyć?",
                        "Finish Application Dialog",
                        JOptionPane.YES_NO_OPTION
                );
                if (o == JOptionPane.YES_OPTION) {
                    generate.killGenThread(); // zamyka dzialanie generatora przy wyjsciu z programu

                    dispose();

                } else if (o == JOptionPane.NO_OPTION) {
                    generate.delay = 10;
                }
                ;

            }
        });

        validate();

    }


    void displayState() {
        rejCVR.setText("" + myDemoCounter.getCVR());
        rejCVR.setHorizontalAlignment(SwingConstants.CENTER);
        stateImp.setText("" + myDemoCounter.getCVR());
        stateImp.setHorizontalAlignment(SwingConstants.CENTER);
        rejRVR.setText("" + myDemoCounter.getRVR());
        rejRVR.setHorizontalAlignment(SwingConstants.CENTER);
        count.setSelected(myDemoCounter.isCountFlag());
        count.setHorizontalAlignment(SwingConstants.CENTER);
        sliderTimer.setValue(myDemoCounter.getCVR());
        etTimer.setText("Stan licznika " + myDemoCounter.getCVR() + " impulsów.");

        if (myDemoCounter.getInterrupt() == true) {
            interrupt.setText(" ** Przerwanie ** ");
            interrupt.setHorizontalAlignment(SwingConstants.CENTER);
            interrupt.setBackground(Color.ORANGE);

        } else if (myDemoCounter.getInterrupt() == false) {
            interrupt.setText(" Brak przerwania ");
            interrupt.setHorizontalAlignment(SwingConstants.CENTER);
            interrupt.setBackground(Color.WHITE);
        }
    }

    void designGUI() {

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            generate.killGenThread();
            dispose();
            System.exit(0);
        });
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e->{
            //1. Create the frame.
            JFrame frame = new JFrame("About");

//2. Optional: What happens when the frame closes?
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//3. Create components and put them in the frame.

            JTextArea labell = new JTextArea();

            labell.setText("\n Symulator licznika System tick timer (SysTick), procesora ARM Cortex M0. \n\n Autor: Akyrt \n Date:  30.05.2017 \n");
            labell.setFont(font);
            labell.setEditable(false);

            frame.getContentPane().add(labell, BorderLayout.CENTER);

            frame.setResizable(false);
//4. Size the frame.
            frame.pack();

//5. Show it.
            frame.setVisible(true);
        });

        fileMenu.add(aboutItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        setLayout(new BorderLayout());
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
            Integer sCVR = Integer.parseInt(regSetCVR.getText());
            myDemoCounter.setCVR(sCVR);


        }

        if (e.getSource() == regSetRVR) {
            Integer sRVR = Integer.parseInt(regSetRVR.getText());
            myDemoCounter.setRVR(sRVR);
            if (sRVR <= 10) {
                sliderTimer.setMaximum(10);
            } else if (sRVR <= 100) {
                sliderTimer.setMaximum(100);
            } else if (sRVR <= 1000) {
                sliderTimer.setMaximum(1000);
            } else if (sRVR <= 10000) {
                sliderTimer.setMaximum(10000);
            }

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


