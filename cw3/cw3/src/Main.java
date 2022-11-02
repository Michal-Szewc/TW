public class Main {
    public static void main(String[] args) {

        int num = 2;
        int rand_number = 2;

        boolean big_producer = false;

        Monitor buffor = new Monitor( 2* rand_number, true);

        Producent[] p = new Producent[num];

        Konsumer[] k = new Konsumer[num];

        if (big_producer) {

            k[0] = new Konsumer(buffor, rand_number, 0);

            for (int i = 1; i < num; i++) {
                p[i] = new Producent(buffor, rand_number, false, i);
                k[i] = new Konsumer(buffor, rand_number, i);
            }

            p[0] = new Producent(buffor, rand_number, true, 0);
        }
        else{
            for (int i = 0; i < num; i++) {
                p[i] = new Producent(buffor, rand_number, false, i);
                k[i] = new Konsumer(buffor, rand_number, i);
            }
        }

        for(int i=0;i<num;i++){
            p[i].start();
            k[i].start();
        }

    }
}