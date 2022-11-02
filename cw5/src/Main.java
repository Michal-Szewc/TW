public class Main {
    public static void main(String[] args) {

        int num = 10;
        int rand_number = 2;

        Monitor buffor = new Monitor( 2* rand_number, 1000000);

        Producent[] p = new Producent[num];

        Konsumer[] k = new Konsumer[num];

        for (int i = 0; i < num; i++) {
            p[i] = new Producent(buffor, rand_number, i);
            k[i] = new Konsumer(buffor, rand_number, i);
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
        System.out.println("milisekund");

    }
}