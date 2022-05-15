import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




class AppBody extends Frame implements ActionListener, WindowListener, KeyListener {



    JTextField timeField = new JTextField(10);
    JPanel panel = new JPanel();

    Lsen l = new Lsen(timeField);
    Timer t = new Timer(TIMER_DELAY, l);


    void setTimer(){

        timeField.setEditable(false);
        timeField.setFocusable(false);

        panel.add(new JLabel("Elapsed Time:"));
        panel.add(timeField);

    }
    public void stopTimer(){t.stop();}



    private static final int TIMER_DELAY = 15;
    int taps = 0;
    double BPM = 0;


    // JTextField
    TextField key1 = new TextField("a",2);
    TextField key2 = new TextField("s",2);
    TextField time = new TextField("10",2);
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


    Button b = new Button("Start");;



    // default constructor
    public AppBody(){}

    public AppBody(String title) {

        super(title);
        setLayout(new FlowLayout());

        addWindowListener(this);
        addKeyListener(this);

        add(lKey1);
        add(key1);
        add(lKey2);
        add(key2);
        add(lPodz);
        add(podz);
        add(lTime);
        add(time);
        add(b);
        add(la);
        add(panel);


        b.addActionListener(this);


    }

    public void window(Head.Settings item)
    {
        // create a new frame to store text field and button
        AppBody myWindow = new AppBody("Stream");
        myWindow.setSize(400,200);
        myWindow.setVisible(true);
        run();

    }

    public void run(){


        while(1==1){


            try{


                   // System.out.println(Lsen.timeString);
                    //System.out.println(timeHold);
                    timeHoldRightNow =Lsen.timeString;

                    if(Lsen.timeString.substring(1,3).compareTo(timeHold) == 0){

                        System.out.println("bpm "+BPM);
                        System.out.println("taps "+taps);
                        b.setLabel("Start");
                        b.removeKeyListener(this);
                        t.stop();
                        break;
                    }


            }catch (NullPointerException e) {

            }


        }
    }



    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Start")) {


            b.setLabel("Stop");
            timeHold = time.getText();
            setTimer();



            b.addKeyListener(this);








        }
        if (s.equals("Stop")) {

            b.setLabel("Start");
            t.stop();

            b.removeKeyListener(this);

        }


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

            t.start();

            taps++;

            String podzielnikS = podz.getText();
            int podzielnik = Integer.parseInt(podzielnikS);
            String a = Lsen.timeString;
            double timeS;
            //System.out.println(a);

            try{
               timeS = Double.parseDouble(timeHoldRightNow);
            }catch (NullPointerException gg){
                timeS = 0.000001;
            }


            System.out.println(timeS);

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
        }



        System.out.println(taps);
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




