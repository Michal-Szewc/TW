import java.util.Random;

public class Producent extends Thread{
    private int limit;
    private Random rand;
    private Proxy proxy;

    Producent(Proxy _proxy, Random _rand, int _limit){
        proxy = _proxy;
        rand = _rand;
        limit = _limit - 1;
    }

    public void run(){
        int val;
        while(true){
            val = rand.nextInt(limit) + 1;
            try{
                BufforFuture result = proxy.produce(val);
                while(!result.isDone())
                    sleep(20);
                System.out.println("produced: " + val);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
