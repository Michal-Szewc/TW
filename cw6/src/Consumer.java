import java.util.Random;

public class Consumer  extends Thread{
    private int limit;
    private Random rand;
    private Proxy proxy;

    Consumer(Proxy _proxy, Random _rand, int _limit){
        proxy = _proxy;
        rand = _rand;
        limit = _limit - 1;
    }

    public void run(){
        int val;
        while(true){
            val = rand.nextInt(limit) + 1;
            try{
                BufforFuture result = proxy.consume(val);
                while(!result.isDone())
                    sleep(20);
                System.out.println("consumed: " + val);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
