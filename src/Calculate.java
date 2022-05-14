public class Calculate {
    public static double getBPM(int taps, int podzielnik, int time){
            double wynik = 0;
            double  t = taps,
                    p = podzielnik,
                    times = time;

            wynik = (2*times*t)/p;


        return wynik;
    }
}
