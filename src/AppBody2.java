import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.text.*;


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
    String timeTempRightNow;
    double time;
    double timePreviousRun;

    JFrame window;

    // JPanel
    JPanel panelTime = new JPanel();
    JPanel panelMain = new JPanel();
    JPanel panelResult = new JPanel();

    // JTextField, initial value (top panel)
    JTextField tKey1 = new JTextField("",2);
    JTextField tKey2 = new JTextField("",2);
    JTextField timeField = new JTextField(5);



    // JComboBox
    //JTextField tPodz = new JTextField("4",2);
    String[] listP= { "1/1","1/2","1/3","1/4","1/5", "1/6","1/7","1/8", "1/9","1/12","1/16"};
    JComboBox<String> tPodz = new JComboBox<>(listP);


    // JSpinner
    SpinnerNumberModel model1 = new SpinnerNumberModel(2.0, 1.0, 10.0, 1.0);
    JSpinner tTime = new JSpinner(model1);

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


    ChartPanel graph;

////////////////////////////////////


    public AppBody(){


         graph = Chart.initUI();

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
        window.add(graph,BorderLayout.WEST);

        bStart.addActionListener(this);
        rRestart.addActionListener(this);
        tPodz.addActionListener(this);

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

        tKey1.setDocument(new LimitJTextField(1));
        tKey2.setDocument(new LimitJTextField(1));
        tPodz.setSelectedIndex(3);



        tKey1.setText("b");
        tKey2.setText("n");
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
            bStart.addKeyListener(this);
            isRunning = true;


            podzielnikTemp=  Objects.requireNonNull(tPodz.getSelectedItem()).toString();

            switch (podzielnikTemp) {
                case "1/1" -> podzielnik = 1;
                case "1/2" -> podzielnik = 2;
                case "1/3" -> podzielnik = 3;
                case "1/5" -> podzielnik = 5;
                case "1/6" -> podzielnik = 6;
                case "1/7" -> podzielnik = 7;
                case "1/8" -> podzielnik = 8;
                case "1/9" -> podzielnik = 9;
                case "1/12" -> podzielnik = 12;
                case "1/16" -> podzielnik = 16;
                default -> podzielnik = 4;
            }




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


                        String timeFromField ;//= tTime.getText();
                        try {
                            tTime.commitEdit();
                        } catch ( java.text.ParseException p ) {
                            p.printStackTrace();
                        }
                        timeFromField = String.valueOf(tTime.getValue());

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

            if(!timer.isRunning())
            {
                timer.start();

            }


            if(taps > 0){
                Chart.addValue(bpm, time);
            } else if(taps == 0) {
                Chart.clear();
            }

            taps++;

        }
    }

    public void keyTyped (KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}


}


class LimitJTextField extends PlainDocument {
    private final int max;
    LimitJTextField(int max) {
        super();
        this.max = max;
    }
    public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text == null)
            return;
        if ((getLength() + text.length()) <= max) {
            super.insertString(offset, text, attr);
        }
    }
}
