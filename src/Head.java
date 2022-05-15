import java.util.Scanner;
import java.util.Set;

public class Head {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        Settings item = new Settings();

        AppBody obj = new AppBody();
                obj.window(item);




    }


    public static class  Settings{
        public int time = 10; //sec
        public String key1 = "z";
        public String key2 = "x";

    }

}
