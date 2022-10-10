public class Konsumer extends Thread{
    Monitor m;

    Konsumer (Monitor _m){m = _m;}

    public void run(){
        System.out.println("Konsumer run");

        while(true) {
            m.consume();
        }
    }
}
