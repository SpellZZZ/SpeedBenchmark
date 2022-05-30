public class Calculate {
    public static double getBPM(int taps, int podzielnik, double time){

        return (double) taps / time *60/ (double) podzielnik;

    }
}
