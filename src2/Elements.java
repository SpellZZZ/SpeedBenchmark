
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Elements {
    protected boolean isRunning = false;
    protected String podzielnikTemp;
    protected int podzielnik;
    protected int taps = 0;
    protected double bpm = 0;

    protected Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);

    // timer manipulation
    protected TimerBuild timerInit;
    protected Timer timer;
    protected static final int TIMER_DELAY = 0;
    protected String timeTempRightNow;
    protected double time;
    protected double timePreviousRun;



    // JPanel

    protected JPanel panelTime = new JPanel(new FlowLayout());
    protected JPanel panelMain = new JPanel();
    protected JPanel panelResult = new JPanel(new BorderLayout());


    // JTextField, initial value (top panel)
    protected JTextField tKey1 = new JTextField("",2);
    protected JTextField tKey2 = new JTextField("",2);
    protected JTextField timeField = new JTextField(5);



    // JComboBox
    //JTextField tPodz = new JTextField("4",2);
    private final String[] listP= { "1/1","1/2","1/3","1/4","1/5", "1/6","1/7","1/8", "1/9","1/12","1/16"};
    protected JComboBox<String> tPodz = new JComboBox<>(listP);


    // JSpinner
    private final SpinnerNumberModel model1 = new SpinnerNumberModel(2.0, 1.0, 100.0, 1.0);
    protected JSpinner tTime = new JSpinner(model1);

    // JLabel, (top panel)
    private final JLabel lKey1 = new JLabel(" Key1 ");
    private final JLabel lKey2 = new JLabel(" Key2 ");
    private final JLabel lTime = new JLabel(" Time ");
    private final JLabel lPodz = new JLabel(" Divisor ");
    private final JLabel lSec = new JLabel(" seconds ");

    protected JLabel result = new JLabel(
            "<html>" +
                    "Taps: " + taps +
                    "<br/>" +
                    "BPM: " + bpm +
                    "</html>");


    protected Button bStart = new Button("Start");
    protected Button rRestart = new Button("Restart");








    protected void setTimer(){
        timerInit = new TimerBuild(timeField);
        timer = new Timer(TIMER_DELAY, timerInit);
        timeField.setText("000.000");
        timeField.setEditable(false);
        timeField.setFocusable(false);
    }
    protected void setMenu(){

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




        panelMain.add(lKey1).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(tKey1).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(lKey2).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(tKey2).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(lPodz).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(tPodz).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(lTime).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(tTime).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(bStart);
        panelMain.add(rRestart);


        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        panelMain.setBackground(new Color(38, 35, 53));

    }
    protected void setResult(){

        result.setHorizontalAlignment(JLabel.CENTER);
        result.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        timeField.setHorizontalAlignment(JLabel.CENTER);
        timeField.setForeground(new Color(	222, 68, 80));
        timeField.setBackground(new Color(38, 35, 53));
        timeField.setBorder(BorderFactory.createEmptyBorder());

        result.setFont(new Font("Dialog", Font.BOLD, 20));
        result.setForeground(new Color(	111, 138, 233));
        lSec.setForeground(new Color(	111, 138, 233));


        panelTime.add(timeField).setFont(new Font("Dialog", Font.BOLD, 20));
        panelTime.add(lSec).setFont(new Font("Dialog", Font.BOLD, 20));
        panelTime.setBackground(new Color(38, 35, 53));
        panelTime.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));


        panelResult.add(result,BorderLayout.NORTH);
        panelResult.add(panelTime,FlowLayout.CENTER);



        panelResult.setBackground(new Color(38, 35, 53));
    }


    protected void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x/2, y/2);
    }



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