public class Konsumer extends Thread{
    Monitor m;
    private int limit;

    Konsumer (Monitor _m, int _limit){m = _m; limit = _limit;}

    public void run(){
        System.out.println("Konsumer run");

        while(true) {
            int val = (int)((Math.random() * (limit/2 - 1)) + 1);
            m.consume(val);
        }
    }
}
