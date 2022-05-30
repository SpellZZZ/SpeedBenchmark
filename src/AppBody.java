import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;


class AppBody extends Elements  implements ActionListener, KeyListener {
    private boolean isRunning = false;
    private String timeTempRightNow;
    private double timePreviousRun = 0;


    public AppBody(){

        JFrame window = new JFrame();    //window
        ChartPanel graph = Chart.initUI();  //xy chart

        //prepare panels
        setTimer();
        setMenu();
        setResult();

        //placing panels
        window.add(getPanelMain(), BorderLayout.NORTH);
        window.add(getPanelResult(), BorderLayout.CENTER);
        window.add(graph, BorderLayout.SOUTH);

        //listeners
        getBStart().addActionListener(this);
        getBRestart().addActionListener(this);
        getComboDivisor().addActionListener(this);


        centreWindow(window);
        window.setIconImage(getIcon());
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }






    //after one round restart timer / stop timer
    private void timerRestart(){
        isRunning = false;
        stop();
        getBStart().setLabel("Start");
        getBStart().removeKeyListener(this);
    }

    //stopping method
    private void stop(){getTimer().stop();}



    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        //if (e.getSource() == bStart)
        if (s.equals("Start")) {

            timerRestart(); //before next run restart timer / result panel
            setTimer();     //prepare timer
            setTaps(0);
            getBStart().setLabel("Stop ");             //change button label
            getBStart().addKeyListener(this);       //add key listener, after elapsed time listener is removed
            isRunning = true;


            String podzielnikTemp = Objects.requireNonNull(getComboDivisor().getSelectedItem()).toString(); //read combo

            switch (podzielnikTemp) {
                case "1/1" -> setDivisor(1);
                case "1/2" -> setDivisor(2);
                case "1/3" -> setDivisor(3);
                case "1/5" -> setDivisor(5);
                case "1/6" -> setDivisor(6);
                case "1/7" -> setDivisor(7);
                case "1/8" -> setDivisor(8);
                case "1/9" -> setDivisor(9);
                case "1/12" -> setDivisor(12);
                case "1/16" -> setDivisor(16);
                default -> setDivisor(4);
            }




            Thread t = new Thread(() -> {
                if( isRunning ){
                    for(;;){

                        try{        //try to read timer
                            timeTempRightNow = TimerBuild.timeString;
                            setTime(Double.parseDouble(timeTempRightNow));
                        } catch (NullPointerException gg){
                            setTime(0.0001);
                        }

                        setBPM(Calculate.getBPM(getTaps(), getDivisor(), getTime()));   //calculations

                                    //update label
                        getResult().setText("<html>" +
                                "Taps: " + getTaps() +
                                "<br/>" +
                                "BPM: " + (int) getBPM() +
                                "</html>");


                            //read timer and time limit, stop program if
                        String timeFromField ;
                        timeFromField = String.valueOf(getSpinnerTime().getValue());
                        //2nd condition prevent bug
                        if(getTime() >= Double.parseDouble(timeFromField) && timePreviousRun != getTime()){
                            timePreviousRun = getTime();
                            //System.out.println("bpm "+ getBPM());     //debug
                            //System.out.println("taps "+getTaps());
                            timerRestart();
                            break;
                        }
                    }
                }
            }


            );  //thread end
            t.start();
        }
        if (s.equals("Stop ")) {

            getBStart().setLabel("Start");
            timerRestart();
            isRunning = false;

        }
        if (s.equals("Restart")) {  //todo
            timerRestart();
        }
    }
    @Override
    public void keyReleased (KeyEvent e) {
        //read keys, then toLowerCase, then compare with keyReleased
        int keyI = e.getKeyCode();
        char keyC = (char)keyI;

        String key = String
                .valueOf(keyC)
                .toLowerCase();


        String k1 = getTKey1()
                .getText()
                .toLowerCase();

        String k2 = getTKey2()
                .getText()
                .toLowerCase();


        if (k1.compareTo(key) == 0 || k2.compareTo(key) == 0) {

            //if timer isn't running, run it
            if(!getTimer().isRunning()) {getTimer().start();}


            //start drawing graph after 2nd click, if equal 0 clear graph
            if(getTaps() > 0){
                Chart.addValue(getBPM(), getTime());
            } else if(getTaps() == 0) {
                Chart.clear();
            }

            setTaps(getTaps()+1);

        }
    }

    public void keyTyped (KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}






}
