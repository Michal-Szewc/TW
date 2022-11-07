import java.util.Random;

public class Producent extends Thread{
    IMonitor m;
    private int limit;
    private int count;
    private Integer num;
    private Random random;

    Producent(IMonitor _m, int _limit, int _num, Random _random){m = _m; limit = _limit; count=0; num = _num;random = _random;}

    public void run(){
        //System.out.println("producer run");

        int val = (int)((random.nextDouble() * (limit - 1)) + 1);
        while(m.produced(val, this))
            val = (int)((random.nextDouble() * (limit - 1)) + 1);
    }

    public void is_produced(){
        count++;
        System.out.println(count);
    }

    public String getThreadName(){
        return "Producent " + num.toString();
    }
}