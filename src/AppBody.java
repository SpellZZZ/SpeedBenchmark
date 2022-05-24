import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class AppBody extends Frame implements ActionListener, KeyListener {

private
    boolean isRunning = false;
    String podzielnikTemp;
    int podzielnik;
    int taps = 0;
    double bpm = 0;



    // timer manipulation
    TimerBuild timerInit;
    Timer timer;
    static final int TIMER_DELAY = 0;
    String timeTemp;
    String timeTempRightNow;
    double time;
    double timePreviousRun;

    JFrame window;

    // JPanel
    JPanel panelTime = new JPanel();
    JPanel panelMain = new JPanel();
    JPanel panelResult = new JPanel();

    // JTextField, initial value (top panel)
    JTextField tKey1 = new JTextField("b",2);
    JTextField tKey2 = new JTextField("n",2);
    JTextField tTime = new JTextField("2",2);
    JTextField tPodz = new JTextField("4",2);
    JTextField timeField = new JTextField(5);

    // JLabel, (top panel)
    JLabel lKey1 = new JLabel("Key1");
    JLabel lKey2 = new JLabel("Key2");
    JLabel lTime = new JLabel("Time");
    JLabel lPodz = new JLabel("Divisor");
    JLabel result = new JLabel(
            "<html>" +
                    "Taps: " + taps +
                    "<br/>" +
                    "BPM: " + bpm +
                    "</html>");


    Button bStart = new Button("Start");
    Button rRestart = new Button("Restart");

////////////////////////////////////






    public AppBody() {

        //Chart ex = new Chart();
        //ex.setVisible(true);

        ChartPanel x = Chart.initUI();

        window =  new JFrame();

        centreWindow(window);

        setTimer();
        setMenu();
        panelResult.add(result);

        panelMain.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelResult.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTime.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelMain.setPreferredSize(new Dimension(400,50));
        panelResult.setPreferredSize(new Dimension(400,50));
        panelTime.setPreferredSize(new Dimension(400,50));

        window.add(panelMain, BorderLayout.NORTH);
        window.add(panelResult,BorderLayout.CENTER);
        window.add(panelTime,BorderLayout.SOUTH);
        window.add(x,BorderLayout.WEST);

        bStart.addActionListener(this);
        rRestart.addActionListener(this);

        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setTimer(){
        timerInit = new TimerBuild(timeField);
        timer = new Timer(TIMER_DELAY, timerInit);
        timeField.setText("000.000");
        timeField.setEditable(false);
        timeField.setFocusable(false);
        panelTime.add(timeField);
        panelTime.setBackground(Color.LIGHT_GRAY);
    }
    public void setMenu(){
        panelMain.add(lKey1);
        panelMain.add(tKey1);
        panelMain.add(lKey2);
        panelMain.add(tKey2);
        panelMain.add(lPodz);
        panelMain.add(tPodz);
        panelMain.add(lTime);
        panelMain.add(tTime);
        panelMain.add(bStart);
        panelMain.add(rRestart);
        panelMain.setBackground(Color.LIGHT_GRAY);
        //panelMain.setFont(new Font("Comic Sans",Font.BOLD,25));
    }
    public void stop(){
        timer.stop();
    }
    public void timerRestart(){
        isRunning = false;
        taps = 0;
        stop();
        bStart.setLabel("Start");
        bStart.removeKeyListener(this);

    }
    public void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        //if (e.getSource() == bStart)
        if (s.equals("Start")) {

            setTimer();
            bStart.setLabel("Stop");
            timeTemp = tTime.getText();
            bStart.addKeyListener(this);
            isRunning = true;

            podzielnikTemp = tPodz.getText();
            podzielnik = Integer.parseInt(podzielnikTemp);


            Thread t = new Thread(() -> {
                if( isRunning ){
                    for(;;){
                        try{
                            timeTempRightNow = TimerBuild.timeString;
                            time = Double.parseDouble(timeTempRightNow);
                        } catch (NullPointerException gg){
                            time = 0.000001;
                        }
                        bpm = Calculate.getBPM(taps,podzielnik, time);
                        bpm = Math.round(bpm);

                        result.setText("<html>" +
                                "Taps: " + taps +
                                "<br/>" +
                                "BPM: " + bpm +
                                "</html>");

                        String timeFromField = tTime.getText();
                        if(time >= Double.parseDouble(timeFromField) && timePreviousRun != time){
                            timePreviousRun = time;
                            System.out.println("bpm "+ bpm);
                            System.out.println("taps "+taps);
                            timerRestart();
                            break;
                        }
                    }
                }


            });
            t.start();
        }
        if (s.equals("Stop")) {

            bStart.setLabel("Start");
            stop();
            bStart.removeKeyListener(this);
            isRunning = false;

        }
        if (s.equals("Restart")) {
            timerRestart();
        }


    }
    @Override
    public void keyReleased (KeyEvent e) {

        int keyI = e.getKeyCode();
        char keyC = (char)keyI;

        String key = String
                .valueOf(keyC)
                .toLowerCase();


        String k1 = tKey1
                .getText()
                .toLowerCase();

        String k2 = tKey2
                .getText()
                .toLowerCase();


        if (k1.compareTo(key) == 0 || k2.compareTo(key) == 0) {

            if(!timer.isRunning()) timer.start();

            taps++;
        }
    }

    public void keyPressed(KeyEvent e) {}
    public void keyTyped (KeyEvent e) {}
}
