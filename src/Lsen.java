import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

class Lsen implements ActionListener {
    public static final int MSECS_PER_SEC = 1000;
    public static final int SECS_PER_MIN = 60;
    public static final int MIN_PER_HR = 60;
   // private static final String TIME_FORMAT = "%02d:%02d:%02d:%03d";
     //private static final String TIME_FORMAT = "%03d:%03d";
   private static final String TIME_FORMAT = "%03d.%03d";

    private long startTime;
    private JTextField timeField;

    public Lsen(JTextField timeField) {
        this.timeField = timeField;
    }

    static String timeString;

    public void actionPerformed(ActionEvent e) {
        if (startTime == 0L) {
            startTime = System.currentTimeMillis();
        } else {
            long currentTime = System.currentTimeMillis();
            int diffTime = (int) (currentTime - startTime);

            int mSecs = diffTime % MSECS_PER_SEC;
            diffTime /= MSECS_PER_SEC;

            int sec = diffTime % SECS_PER_MIN;
            diffTime /= SECS_PER_MIN;



            String time = String.format(TIME_FORMAT, sec, mSecs);
            //System.out.println("Time: " + time);
            timeString = time;
            timeField.setText(time);
        }
    }
}
/*
public class Count {
    public static final int TIMER_DELAY = 15;

    public static JPanel getTimer() {
        final JTextField timeField = new JTextField(10);
        timeField.setEditable(false);
        timeField.setFocusable(false);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Elapsed Time:"));
        panel.add(timeField);

        Lsen l = new Lsen(timeField);
        Timer t = new Timer(TIMER_DELAY, l);
        t.start();
        JOptionPane.showMessageDialog(null, panel);
        t.stop();
    }
}*/