public class Producent extends Thread{
    Monitor m;
    private int limit;
    private int count;
    private Integer num;

    Producent(Monitor _m, int _limit, int _num){m = _m; limit = _limit; count=0; num = _num;}

    public void run(){
        System.out.println("producer run");

        int val = (int)((Math.random() * (limit - 1)) + 1);
        while(m.produced(val, this))
            val = (int)((Math.random() * (limit - 1)) + 1);
    }

    public void is_produced(){
        count++;
        System.out.println(count);
    }

    public String getThreadName(){
        return "Producent " + num.toString();
    }
}