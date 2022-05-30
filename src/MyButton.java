import javax.swing.*;
import java.awt.*;


public class MyButton extends JButton {

    public MyButton(String text){
        super(text);
        this.setBackground(new Color(	197, 51, 131));
        this.setFont(new Font("Dialog", Font.BOLD, 20));
        this.setForeground(new Color(255, 255,255));
        this.setBorderPainted(false);
        this.setFocusable(true);
    }

    @Override
    public void setLabel(String txt){
        this.setText(txt);
    }


}
