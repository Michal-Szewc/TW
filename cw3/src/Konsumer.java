public class Konsumer extends Thread{
    Monitor m;

    Konsumer (Monitor _m){m = _m;}

    public void run(){
        System.out.println("Konsumer run");

        while(true) {
            int val = (int)((Math.random() * (m.getLimit()/2 - 1)) + 1);
            m.consume(val);
        }
    }
}
