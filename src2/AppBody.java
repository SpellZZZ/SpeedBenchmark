import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import javax.swing.*;


class AppBody extends Elements  implements ActionListener, KeyListener {


    public AppBody(){

        ChartPanel graph = Chart.initUI();
        JFrame window = new JFrame();

        setTimer();
        setMenu();
        setResult();

        window.add(panelMain, BorderLayout.NORTH);
        window.add(panelResult, BorderLayout.CENTER);
        window.add(graph, BorderLayout.SOUTH);

        bStart.addActionListener(this);
        rRestart.addActionListener(this);
        tPodz.addActionListener(this);


        centreWindow(window);
        window.setIconImage(icon);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }







    private void timerRestart(){
        isRunning = false;
        stop();
        bStart.setLabel("Start");
        bStart.removeKeyListener(this);
    }

    private void stop(){timer.stop();}



    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        //if (e.getSource() == bStart)
        if (s.equals("Start")) {

            timerRestart();
            taps = 0;
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


            }


            );
            t.start();
        }
        if (s.equals("Stop")) {

            bStart.setLabel("Start");
            //stop();
            timerRestart();
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


