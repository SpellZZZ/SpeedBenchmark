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
    JPanel panelResult = new JPanel();

    Lsen l ;
    Timer t ;


    void setTimer(){
        l = new Lsen(timeField);
        t = new Timer(TIMER_DELAY, l);
        timeField.setText("000.000");
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
        //panelMain.setLayout(new FlowLayout());
    }



    private static final int TIMER_DELAY = 15;
    int taps = 0;
    double BPM = 0;


    // JTextField
    JTextField key1 = new JTextField("b",2);
    JTextField key2 = new JTextField("n",2);
    JTextField time = new JTextField("2",2);
    JTextField podz = new JTextField("4",2);

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
                    "Taps: " + taps +
                    "<br/>" +
                    "BPM: " + BPM +
                    "</html>");



////////////////////////////////////


    Button b = new Button("Start");
    Button r = new Button("Restart");

    JFrame window;

    // default constructor
    // public AppBody(){}

    public AppBody(/*String title*/) {

        panelResult.add(la);
        //AppBody myWindow = new AppBody();
        window =  new JFrame();
        setTimer();

        centreWindow(window);
        //super(title);

        //window =  new JFrame();

        //window.setLayout(new FlowLayout());

        window.setLayout(new BorderLayout(20, 15));

        window.addWindowListener(this);
        window.addKeyListener(this);

        setMenu();

        panelMain.setLayout(new FlowLayout());
        panelResult.setLayout(new FlowLayout());
        panel.setLayout(new FlowLayout());

        window.add(panelMain, BorderLayout.NORTH);
        window.add(panelResult,BorderLayout.CENTER);
        window.add(panel,BorderLayout.SOUTH);
        window.setSize(500,300);
        centreWindow(window);

        b.addActionListener(this);
        r.addActionListener(this);


        window.setVisible(true);
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }


   /* public void window(Head.Settings item) {
        new AppBody("Stream");
        //AppBody myWindow = new AppBody();
       /* myWindow.setSize(500,300);
        myWindow.setVisible(true);
        centreWindow(myWindow);
    }*/



    public void actionPerformed(ActionEvent e) {


        String s = e.getActionCommand();

        if (s.equals("Start")) {


            b.setLabel("Stop");
            timeHold = time.getText();


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