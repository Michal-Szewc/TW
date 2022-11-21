import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

class Consumer4 extends Thread{
    Monitor m;
    private int limit;
    private Integer num;
    private Random random;

    Consumer4 (Monitor _m, int _limit, int _num, Random _random){m = _m; limit = _limit - 1; num = _num; random = _random;}

    public void run(){
        ThreadMXBean t = ManagementFactory.getThreadMXBean();
        long startTime = t.getThreadCpuTime(Thread.currentThread().getId());

        int val = random.nextInt(limit) + 1;
        while(m.consume(val, this))
            val = random.nextInt(limit) + 1;

        long endTime = t.getThreadCpuTime(Thread.currentThread().getId());

        m.addCpu(endTime - startTime);
    }
}

class Producer4 extends Thread{
    Monitor m;
    private int limit;
    private int count;
    private Integer num;
    private Random random;

    Producer4(Monitor _m, int _limit, int _num, Random _random){m = _m; limit = _limit - 1; count=0; num = _num;random = _random;}

    public void run(){
        ThreadMXBean t = ManagementFactory.getThreadMXBean();
        long startTime = t.getThreadCpuTime(Thread.currentThread().getId());

        int val = random.nextInt(limit) + 1;
        while(m.produced(val, this))
            val = random.nextInt(limit) + 1;

        long endTime = t.getThreadCpuTime(Thread.currentThread().getId());

        m.addCpu(endTime - startTime);
    }

    public void is_produced(){
        count++;
        System.out.println(count);
    }
}