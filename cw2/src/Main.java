public class Main {
    public static void main(String[] args) {

        int num = 3;

        Monitor buffor = new Monitor();

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