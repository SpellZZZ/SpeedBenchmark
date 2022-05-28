import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;

import static java.awt.Color.WHITE;


class AppBody extends Frame implements ActionListener, KeyListener {

private
    boolean isRunning = false;
    String podzielnikTemp;
    int podzielnik;
    int taps = 0;
    double bpm = 0;

    Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);

    // timer manipulation
    TimerBuild timerInit;
    Timer timer;
    static final int TIMER_DELAY = 0;
    String timeTempRightNow;
    double time;
    double timePreviousRun;

    JFrame window;

    // JPanel

    JPanel panelTime = new JPanel(new FlowLayout());
    JPanel panelMain = new JPanel();
    JPanel panelResult = new JPanel(new BorderLayout());


    // JTextField, initial value (top panel)
    JTextField tKey1 = new JTextField("",2);
    JTextField tKey2 = new JTextField("",2);
    JTextField timeField = new JTextField(5);



    // JComboBox
    //JTextField tPodz = new JTextField("4",2);
    String[] listP= { "1/1","1/2","1/3","1/4","1/5", "1/6","1/7","1/8", "1/9","1/12","1/16"};
    JComboBox<String> tPodz = new JComboBox<>(listP);


    // JSpinner
    SpinnerNumberModel model1 = new SpinnerNumberModel(2.0, 1.0, 100.0, 1.0);
    JSpinner tTime = new JSpinner(model1);

    // JLabel, (top panel)
    JLabel lKey1 = new JLabel(" Key1 ");
    JLabel lKey2 = new JLabel(" Key2 ");
    JLabel lTime = new JLabel(" Time ");
    JLabel lPodz = new JLabel(" Divisor ");
    JLabel lSec = new JLabel(" seconds ");
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
        graph.setBackground(new Color(38, 35, 53));

        setTimer();
        setMenu();
        setResult();


        window.add(panelMain, BorderLayout.NORTH);
        window.add(panelResult, BorderLayout.CENTER);
        window.add(graph, BorderLayout.SOUTH);

        bStart.addActionListener(this);
        rRestart.addActionListener(this);
        tPodz.addActionListener(this);

        window.setIconImage(icon);
        window.setResizable(false);
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
    }
    public void setMenu(){

        tKey1.setDocument(new LimitJTextField(1));
        tKey2.setDocument(new LimitJTextField(1));
        tPodz.setSelectedIndex(3);
        tKey1.setText("b");
        tKey2.setText("n");

        lKey1.setForeground(new Color(	111, 138, 233));
        lKey2.setForeground(new Color(	111, 138, 233));
        lPodz.setForeground(new Color(	111, 138, 233));
        lTime.setForeground(new Color(	111, 138, 233));

        tKey1.setForeground(new Color(	222, 68, 80));
        tKey2.setForeground(new Color(	222, 68, 80));
        tPodz.setForeground(new Color(	222, 68, 80));

        tKey1.setBackground(new Color(36, 24, 38));
        tKey2.setBackground(new Color(36, 24, 38));
        tPodz.setBackground(new Color(36, 24, 38));

        JComponent editor = tTime.getEditor();
        int n = editor.getComponentCount();
        for (int i=0; i<n; i++)
        {
            Component c = editor.getComponent(i);
            if (c instanceof JTextField)
            {
                c.setForeground(new Color(	222, 68, 80));
                c.setBackground(new Color(36, 24, 38));

            }
        }


        tKey1.setBorder(BorderFactory.createEmptyBorder());
        tKey2.setBorder(BorderFactory.createEmptyBorder());
        tTime.setBorder(BorderFactory.createEmptyBorder());


        for (int i = 0; i < tPodz.getComponentCount(); i++)
        {
            if (tPodz.getComponent(i) instanceof JComponent) {
                ((JComponent) tPodz.getComponent(i)).setBorder(BorderFactory.createEmptyBorder());
            }


            if (tPodz.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) tPodz.getComponent(i)).setBorderPainted(false);
            }
        }




        panelMain.add(lKey1).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(tKey1).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(lKey2).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(tKey2).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(lPodz).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(tPodz).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(lTime).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(tTime).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelMain.add(bStart);
        panelMain.add(rRestart);


        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        panelMain.setBackground(new Color(38, 35, 53));

    }
    public void setResult(){

        result.setHorizontalAlignment(JLabel.CENTER);
        result.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        timeField.setHorizontalAlignment(JLabel.CENTER);
        timeField.setForeground(new Color(	222, 68, 80));
        timeField.setBackground(new Color(38, 35, 53));
        timeField.setBorder(BorderFactory.createEmptyBorder());

        result.setFont(new Font("Tahoma", Font.BOLD, 20));
        result.setForeground(new Color(	111, 138, 233));
        lSec.setForeground(new Color(	111, 138, 233));


        panelTime.add(timeField).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelTime.add(lSec).setFont(new Font("Tahoma", Font.BOLD, 20));
        panelTime.setBackground(new Color(38, 35, 53));
        panelTime.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));


        panelResult.add(result,BorderLayout.NORTH);
        panelResult.add(panelTime,FlowLayout.CENTER);



        panelResult.setBackground(new Color(38, 35, 53));


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

class RoundBtn extends Rectangle implements Border
{
    private int r;
    RoundBtn(int r) {
        this.r = r;
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }
    public boolean isBorderOpaque() {
        return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, r, r);
    }
}
