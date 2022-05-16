import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




class AppBody extends Frame implements ActionListener, WindowListener, KeyListener {


    public static boolean isRunning = false;
    double timeS;
    String podzielnikS;
    int podzielnik;

    double timeFromFieldOld;

    JTextField timeField = new JTextField(5);
    JPanel panel = new JPanel();
    JPanel panelMain = new JPanel();

    Lsen l ;
    Timer t ;


    void setTimer(){

        l = new Lsen(timeField);
        t = new Timer(TIMER_DELAY, l);
        timeField.setEditable(false);
        timeField.setFocusable(false);
        panel.add(timeField);


    }
    void setMenu(){
        panelMain.add(lKey1);
        panelMain.add(key1);
        panelMain.add(lKey2);
        panelMain.add(key2);
        panelMain.add(lPodz);
        panelMain.add(podz);
        panelMain.add(lTime);
        panelMain.add(time);
        panelMain.add(b);
        panelMain.add(r);
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }



    private static final int TIMER_DELAY = 15;
    int taps = 0;
    double BPM = 0;


    // JTextField
    TextField key1 = new TextField("a",2);
    TextField key2 = new TextField("s",2);
    TextField time = new TextField("2",2);
    TextField podz = new TextField("4",2);
    public static String timeHold;
    public static String timeHoldRightNow;



    // label to display text
    JLabel lKey1 = new JLabel("Key1");
    JLabel lKey2 = new JLabel("Key2");
    JLabel lTime = new JLabel("Time");
    JLabel lPodz = new JLabel("Podzielnik");


    JLabel la = new JLabel(
                "<html>" +
                    "<br/>" +
                    "<br/>" +
                    "Your Results:" +
                    "<br/>" +
                    "Taps: " + taps +
                    "<br/>" +
                    "BPM: " + BPM +
                    "</html>");


////////////////////////////////////


    Button b = new Button("Start");
    Button r = new Button("Restart");



    // default constructor
    public AppBody(){}

    public AppBody(String title) {

        super(title);
        setLayout(new FlowLayout());

        addWindowListener(this);
        addKeyListener(this);

        setMenu();

        add(panelMain).setBounds(10,10,100,10);
        add(la).setBounds(10,10,100,10);
        add(panel);


        b.addActionListener(this);
        r.addActionListener(this);



    }

    public void window(Head.Settings item) {
        // create a new frame to store text field and button
        AppBody myWindow = new AppBody("Stream");
        myWindow.setSize(500,300);
        myWindow.setVisible(true);

        //System.out.println(timeHoldRightNow );
    }



    public void actionPerformed(ActionEvent e) {


        String s = e.getActionCommand();

        if (s.equals("Start")) {


            b.setLabel("Stop");
            timeHold = time.getText();
            setTimer();

            b.addKeyListener(this);

            podzielnikS = podz.getText();
            podzielnik = Integer.parseInt(podzielnikS);

            isRunning = true;



            Thread t = new Thread() {
                public void run(){
                    if( isRunning == true ){

                        for(;;){


                            try{
                                timeHoldRightNow = Lsen.timeString;
                                timeS = Double.parseDouble(timeHoldRightNow);
                            } catch (NullPointerException gg){
                                timeS = 0.000001;
                            }

                            BPM = Calculate.getBPM(taps,podzielnik,timeS);
                            BPM = Math.round(BPM);

                            la.setText("<html>" +
                                    "<br/>" +
                                    "<br/>" +
                                    "Your Results:" +
                                    "<br/>" +
                                    "Taps: " + taps +
                                    "<br/>" +
                                    "BPM: " + BPM +
                                    "</html>");


                            String timeFromField = time.getText();
                            if(timeS >= Double.parseDouble(timeFromField) && timeFromFieldOld != timeS){
                                timeFromFieldOld = timeS;
                                System.out.println("bpm "+BPM);
                                System.out.println("taps "+taps);
                                timerRestart();
                                break;
                            }



                            }
                }


                }
            };
            t.start();

        }
        if (s.equals("Stop")) {

            b.setLabel("Start");
            stop();

            b.removeKeyListener(this);
            isRunning = false;

        }
        if (s.equals("Restart")) {
            timerRestart();
        }


    }


    public void stop(){
        t.stop();
    }
    public void timerRestart(){
        isRunning = false;
        taps = 0;
        stop();
        b.setLabel("Start");
        b.removeKeyListener(this);
        setTimer();
    }


    public void keyPressed(KeyEvent e) {

        int keyI = e.getKeyCode();
        char keyC = (char)keyI;

        String key = String
                .valueOf(keyC)
                .toLowerCase();


        String k1 = key1
                .getText()
                .toLowerCase();

        String k2 = key2
                .getText()
                .toLowerCase();


        if (k1.compareTo(key) == 0 || k2.compareTo(key) == 0) {

            if(!t.isRunning()) t.start();

            taps++;
        }
    }

    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }


    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}


    public void keyReleased (KeyEvent e) {}
    public void keyTyped (KeyEvent e) {}
}

