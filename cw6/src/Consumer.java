import java.util.Random;

public class Consumer  extends Thread{
    private int limit;
    private int sleep_time;
    private Random rand;
    private Proxy proxy;
    boolean working;
    long n_jobs;

    Consumer(Proxy _proxy, Random _rand, int _limit, int _sleep_time){
        proxy = _proxy;
        rand = _rand;
        limit = _limit - 1;
        sleep_time = _sleep_time;
        working = true;
        n_jobs = 0;
    }

    public void run(){
        int val;
        while(working){
            val = rand.nextInt(limit) + 1;
            try{
                BufferFuture result = proxy.consume(val);
                while(!result.isDone() && working) {
                    sleep(20);
                    ++n_jobs;
                }
                // System.out.println("consumed: " + val);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    long getJobs(){return  n_jobs;}

    public void done(){working = false;}
}
