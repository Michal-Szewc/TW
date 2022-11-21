import java.util.Random;

public class Producer extends Thread{

    private int sleep_time;
    private int limit;
    private Random rand;
    private Proxy proxy;
    boolean working;
    long n_jobs;

    Producer(Proxy _proxy, Random _rand, int _limit, int _sleep_time){
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
                BufferFuture result = proxy.produce(val);
                while(!result.isDone() && working) {
                    sleep(sleep_time);
                    ++n_jobs;
                }
                // System.out.println("produced: " + val);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    long getJobs(){return  n_jobs;}

    public void done(){working = false;}
}
