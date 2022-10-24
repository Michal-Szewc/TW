public class Main {
    public static void main(String[] args) {

        int num = 3;
        int rand_number = 20;

        Monitor buffor = new Monitor( 2* rand_number, true);

        Producent[] p = new Producent[num];

        Konsumer[] k = new Konsumer[num];

        k[0] = new Konsumer(buffor, rand_number);

        for(int i=1; i<num;i++){
            p[i] = new Producent(buffor, rand_number/4, false);
            k[i] = new Konsumer(buffor, rand_number);
        }

        p[0] = new Producent(buffor, rand_number, true);

        for(int i=0;i<num;i++){
            p[i].start();
            k[i].start();
        }

    }
}