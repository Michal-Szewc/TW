public class Main {
    public static void main(String[] args) {

        int num = 10;
        int size = 6;

        Monitor buffor = new Monitor(size);

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