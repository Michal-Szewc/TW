public class Konsumer extends Thread{
    Monitor m;
    private int limit;
    private Integer num;

    Konsumer (Monitor _m, int _limit, int _num){m = _m; limit = _limit; num = _num;}

    public void run(){
        System.out.println("Konsumer run");

        int val = (int)((Math.random() * (limit/2 - 1)) + 1);
        while(m.consume(val, this))
            val = (int)((Math.random() * (limit/2 - 1)) + 1);
    }

    public String getThreadName(){
        return "Konsumer " + num.toString();
    }
}