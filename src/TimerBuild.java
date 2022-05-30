import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class TimerBuild implements ActionListener {
    public static final int MSECS_PER_SEC = 1000;
    public static final int SECS_PER_MIN = 60;

   private static final String TIME_FORMAT = "%03d.%03d";   //format

    private long startTime;
    private final JTextField timeField;

    public TimerBuild(JTextField timeField) {
        this.timeField = timeField;
    }   //initial value

    static String timeString;

    public void actionPerformed(ActionEvent e) {
        //if start time == long 0 get start time from sys (timer start)
        if (startTime == 0L) {
            startTime = System.currentTimeMillis();
        } else {
            long currentTime = System.currentTimeMillis();  //get current time, then current - start time
            int diffTime = (int) (currentTime - startTime);

            int mSecs = diffTime % MSECS_PER_SEC;       //prepare format
            diffTime /= MSECS_PER_SEC;

            int sec = diffTime % SECS_PER_MIN;

            String time = String.format(TIME_FORMAT, sec, mSecs);

            timeString = time;
            timeField.setText(time);
        }
    }
}
