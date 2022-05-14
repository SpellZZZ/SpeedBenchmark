import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;




class AppBody extends Frame implements ActionListener, WindowListener, KeyListener {

    int taps = 0;
    double BPM = 0;
    int timeS = 5;

    // JTextField
    TextField key1 = new TextField(2);
    TextField key2 = new TextField(2);
    TextField time = new TextField(2);
    TextField podz = new TextField(2);



    // label to display text
    JLabel lKey1 = new JLabel("Key1");
    JLabel lKey2 = new JLabel("Key2");
    JLabel lTime = new JLabel("Time");
    JLabel lPodz = new JLabel("Podzielnik");


    JLabel l = new JLabel(
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

    //TextField text = new TextField(20);
    Button b = new Button("Start");;
    //private int numClicks = 0;


    // default constructor
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
        add(l);


        b.addActionListener(this);
        b.addKeyListener(this);

    }

    public static void window(Head.Settings item)
    {
        // create a new frame to store text field and button

        AppBody myWindow = new AppBody("Stream");
        myWindow.setSize(400,200);
        myWindow.setVisible(true);

    }



    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Start")) {


            b.setLabel("Stop");
            //l.setText(
                   // "<html>Your Keys: " + key1.getText() +" "+ key2.getText() +"<br/>Time: "+ time.getText()+"</html>" );


        }
        if (s.equals("Stop")) {

            b.setLabel("Start");




        }


    }


    public void keyPressed(KeyEvent e) {

        int keyI = e.getKeyCode();
        //System.out.println(key1.getText() +" "+ key2.getText() +" "+keyI);
        char keyC = (char)keyI;
        //
        String key = String
                .valueOf(keyC)
                .toLowerCase();
        //System.out.println(key1.getText() +" "+ key2.getText() +" "+keyC);
        //System.out.println(key1.getText() +" "+ key2.getText() +" "+ key);


        String k1 = key1
                .getText()
                .toLowerCase();
        String k2 = key2
                .getText()
                .toLowerCase();


        if (k1.compareTo(key) == 0) {
            taps++;
            String podzielnikS = podz.getText();
            int podzielnik = Integer.parseInt(podzielnikS);

            BPM = Calculate.getBPM(taps,podzielnik,timeS);

            l.setText("<html>" +
                    "<br/>" +
                    "<br/>" +
                    "Your Results:" +
                    "<br/>" +
                    "Taps: " + taps +
                    "<br/>" +
                    "BPM: " + BPM +
                    "</html>");
        }

        if (k2.compareTo(key) == 0) {
            taps++;
            l.setText("<html>" +
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


/* //ur
List<double> result = GetErrorStatisticsArray(score.HitErrors);

hint = String.Format("Accuracy:\nError: {0:0.00}ms - {1:0.00}ms avg\nUnstable Rate: {2:0.00}\n", result[0], result[1], result[4] * 10, result[6], result[5]);

// input is a list of (user_hit_time - correct_time)
internal List<double> GetErrorStatisticsArray(List<int> list)
{
    if (list == null || list.Count == 0)
        return null;
    List<double> result = new List<double>(4);
    double total = 0, _total = 0, totalAll = 0;
    int count = 0, _count = 0;
    int max = 0, min = int.MaxValue;
    for (int i = 0; i < list.Count; i++)
    {
        if (list[i] > max)
            max = list[i];
        if (list[i] < min)
            min = list[i];
        totalAll += list[i];
        if (list[i] >= 0)
        {
            total += list[i];
            count++;
        }
        else
        {
            _total += list[i];
            _count++;
        }
    }
    double avarage = totalAll / list.Count;
    double variance = 0;
    for (int i = 0; i < list.Count; i++)
    {
        variance += Math.Pow(list[i] - avarage, 2);
    }
    variance = variance / list.Count;
    result.Add(_count == 0 ? 0 : _total / _count); //0
    result.Add(count == 0 ? 0 : total / count); //1
    result.Add(avarage); //2
    result.Add(variance); //3
    result.Add(Math.Sqrt(variance)); //4
    result.Add(max); //5
    result.Add(min); //6
    return result;
}
*/