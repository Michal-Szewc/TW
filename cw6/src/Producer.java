import java.util.Random;

public class Producer extends Client{

    private Scheduler scheduler;
    private int val;
    private int limit;
    private Random random;

    Producer(Scheduler _scheduler, int _limit, Random _random){scheduler = _scheduler; limit = _limit; val = 0; random = _random; is_working = false;}

    public void run(){
        Future future = new Future();
        while(true){
            if(is_working){
                if(future.is_done()){
                    is_working = false;
                }
            }
            else{
                val = random.nextInt(limit + 1);
                future = scheduler.produce(val);
            }
            hard_work();
        }
    }
}
