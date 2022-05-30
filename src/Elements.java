import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Elements {


    private int divisor;
    private int taps = 0;
    private double bpm = 0;

    //icon
    private final Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);

    //timer
    private static final int TIMER_DELAY = 0;
    private Timer timer;
    private double time;

    // JPanel
    private final JPanel panelTime = new JPanel(new FlowLayout());
    private final JPanel panelMain = new JPanel();
    private final JPanel panelResult = new JPanel(new BorderLayout());


    // JTextField, initial value (top panel)
    private final JTextField tKey1 = new JTextField("",2);  //place for 1 character
    private final JTextField tKey2 = new JTextField("",2);  //place for 1 character
    private final JTextField timeField = new JTextField(5);


    // JComboBox
    private final String[] listP= { "1/1","1/2","1/3","1/4","1/5", "1/6","1/7","1/8", "1/9","1/12","1/16"};
    private final JComboBox<String> comboDivisor = new JComboBox<>(listP);

    // JSpinner
    private final SpinnerNumberModel model1 = new SpinnerNumberModel(2.0, 1.0, 100.0, 1.0);
    private final JSpinner spinnerTime = new JSpinner(model1);


    // JLabel, (top panel)
    private final JLabel lKey1 = new JLabel(" Key1 ");
    private final JLabel lKey2 = new JLabel(" Key2 ");
    private final JLabel lTime = new JLabel(" Time ");
    private final JLabel lDiv = new JLabel(" Divisor ");
    private final JLabel lSec = new JLabel(" seconds ");

    // JLabel, (result panel)
    private final JLabel result = new JLabel(
            "<html>" +
                    "Taps: " + taps +
                    "<br/>" +
                    "BPM: " + (int) bpm +
                    "</html>");

    // Buttons
    private final MyButton bStart = new MyButton("Start");
    private final MyButton bMode = new MyButton("Restart"); //future update


    //getters
    protected int getDivisor(){return divisor;}
    protected int getTaps(){return taps;}
    protected double getBPM(){return bpm;}
    protected Image getIcon(){return icon;}
    protected Timer getTimer(){return timer;}
    protected double getTime(){return time;}
    protected JPanel getPanelMain(){return panelMain;}
    protected JPanel getPanelResult(){return panelResult;}
    protected JTextField getTKey1(){return tKey1;}
    protected JTextField getTKey2(){return tKey2;}
    protected JComboBox<String> getComboDivisor(){return comboDivisor;}
    protected JSpinner getSpinnerTime(){return spinnerTime;}
    protected JLabel getResult(){return result;}
    protected MyButton getBStart(){return bStart;}
    protected MyButton getBRestart(){return bMode;} //future update


    // Setters
    protected void setDivisor(int x){
        divisor = x;}
    protected void setTaps(int x){taps = x;}
    protected void setBPM(double x){bpm = x;}
    protected void setTime(double x){time = x;}



    protected void setTimer(){
        // timer manipulation
        TimerBuild timerInit = new TimerBuild(timeField);
        timer = new Timer(TIMER_DELAY, timerInit);
        timeField.setText("000.000");   //tier initial value
        timeField.setEditable(false);
        timeField.setFocusable(false);
    }
    //divisions
    //prepare top panel, placing and design
    protected void setMenu(){

        //"key" text area & label
        tKey1.setDocument(new LimitJTextField(1));
        tKey2.setDocument(new LimitJTextField(1));

        tKey1.setText("b");
        tKey2.setText("n");

        lKey1.setForeground(new Color(	111, 138, 233));
        lKey2.setForeground(new Color(	111, 138, 233));
        lDiv.setForeground(new Color(	111, 138, 233));

        tKey1.setForeground(new Color(	222, 68, 80));
        tKey2.setForeground(new Color(	222, 68, 80));

        tKey1.setBackground(new Color(36, 24, 38));
        tKey2.setBackground(new Color(36, 24, 38));

        tKey1.setBorder(BorderFactory.createEmptyBorder());
        tKey2.setBorder(BorderFactory.createEmptyBorder());



        //divisor (Combo & label)
        for (int i = 0; i < comboDivisor.getComponentCount(); i++)
        {
            if (comboDivisor.getComponent(i) instanceof JComponent) {
                ((JComponent) comboDivisor.getComponent(i)).setBorder(BorderFactory.createEmptyBorder());
            }


            if (comboDivisor.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) comboDivisor.getComponent(i)).setBorderPainted(false);
            }
        }

        comboDivisor.setForeground(new Color(	222, 68, 80));
        comboDivisor.setSelectedIndex(3);
        comboDivisor.setBackground(new Color(36, 24, 38));



        //Time (spinner & label)
        JComponent editor = spinnerTime.getEditor();
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

        spinnerTime.setBorder(BorderFactory.createEmptyBorder());
        lTime.setForeground(new Color(	111, 138, 233));




        panelMain.add(lKey1).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(tKey1).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(lKey2).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(tKey2).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(lDiv).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(comboDivisor).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(lTime).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(spinnerTime).setFont(new Font("Dialog", Font.BOLD, 20));
        panelMain.add(bStart);
        //panelMain.add(bMode);         //FUTURE UPDATE


        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        panelMain.setBackground(new Color(38, 35, 53));
    }

    protected void setResult(){
        //set result panel
        result.setHorizontalAlignment(JLabel.CENTER);
        result.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        result.setFont(new Font("Dialog", Font.BOLD, 20));
        result.setForeground(new Color(	111, 138, 233));

        //set timeField
        timeField.setHorizontalAlignment(JLabel.CENTER);
        timeField.setForeground(new Color(	222, 68, 80));
        timeField.setBackground(new Color(38, 35, 53));
        timeField.setBorder(BorderFactory.createEmptyBorder());


        lSec.setForeground(new Color(	111, 138, 233));

        //set time panel
        panelTime.add(timeField).setFont(new Font("Dialog", Font.BOLD, 20));
        panelTime.add(lSec).setFont(new Font("Dialog", Font.BOLD, 20));
        panelTime.setBackground(new Color(38, 35, 53));
        panelTime.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));


        panelResult.add(result,BorderLayout.NORTH);
        panelResult.add(panelTime,FlowLayout.CENTER);
        panelResult.setBackground(new Color(38, 35, 53));
    }

        //~center~ window
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


