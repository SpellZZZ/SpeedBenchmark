public class Calculate {
    public static double getBPM(int taps, int podzielnik, double time){
            double wynik;
            double  t = taps,
                    p = podzielnik,
                    times = time;

            wynik = t/times*60/p;


        return wynik;
    }
}
