import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int num = 10;
        int rand_number = 2;

        /*

        Jako argument podajemu Monitor lub Monitor2

        Monitor to zagnieżdżone locki, a Monitor2 to jeden lock i 4 conditions

        pomiary wykazują, że daną liczbę operacji zagnieżdżone locki wykonują około 5 razy szybciej (dla tych samych wartości losowanych przez thready)

        dla miliona operacji Monitor miał czas: 741 ms
                                    a Monitor2: 3847 ms

        */

        IMonitor buffor = new Monitor2( 2* rand_number, 1000000);

        Producent[] p = new Producent[num];

        Konsumer[] k = new Konsumer[num];

        int offset = 0;

        for (int i = 0; i < num; i++) {
            p[i] = new Producent(buffor, rand_number, i, new Random(i + offset));
            k[i] = new Konsumer(buffor, rand_number, i, new Random(i + offset + 1000));
        }

        for(int i=0;i<num;i++){
            p[i].start();
            k[i].start();
        }

        for(int i=0;i<num;i++){
            try {
                p[i].join();
                k[i].join();
            } catch (Exception e){
                System.out.println("Ups");
            }
        }

        buffor.getTime();

    }
}