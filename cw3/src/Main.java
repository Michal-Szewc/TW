public class Main {
    public static void main(String[] args) {

        int num = 3;
        int buffor_size = 5;

        Monitor buffor = new Monitor(buffor_size);

        Producent[] p = new Producent[num];

        Konsumer[] k = new Konsumer[num];

        for(int i=0; i<num;i++){
            p[i] = new Producent(buffor);
            k[i] = new Konsumer(buffor);
        }

        for(int i=0;i<num;i++){
            p[i].start();
            k[i].start();
        }

    }
}