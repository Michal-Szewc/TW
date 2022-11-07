import java.util.Random;

public class Konsumer extends Thread{
    IMonitor m;
    private int limit;
    private Integer num;
    private Random random;

    Konsumer (IMonitor _m, int _limit, int _num, Random _random){m = _m; limit = _limit; num = _num; random = _random;}

    public void run(){
        //System.out.println("Konsumer run");

        int val = (int)((random.nextDouble() * (limit - 1)) + 1);
        while(m.consume(val, this))
            val = (int)((random.nextDouble() * (limit - 1)) + 1);
    }

    public String getThreadName(){
        return "Konsumer " + num.toString();
    }
}