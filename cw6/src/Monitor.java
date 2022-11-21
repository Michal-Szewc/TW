import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor{
    int buffor;
    int limit;
    long operations_limit;
    long startTime;
    long startTime2;
    long cpuTime;

    int sum;

    ReentrantLock lock_p = new ReentrantLock();
    ReentrantLock lock_c = new ReentrantLock();
    ReentrantLock lock_w = new ReentrantLock();

    Condition prod  = lock_w.newCondition();
    Condition con = lock_w.newCondition();

    Monitor(){}
    Monitor(int _limit, long _operations_limit){
        buffor = 0 ;
        limit = _limit;
        sum = 0;
        operations_limit = _operations_limit;
        startTime = System.nanoTime();
        startTime2 = System.currentTimeMillis();
        cpuTime = 0;
    }

    public  boolean produced(int val, Producer4 p){
        try {
            lock_p.lock();

            lock_w.lock();

            while (buffor > limit - val)
                prod.await();

            buffor += val;
            ++sum;

            con.signal();

        } catch (Exception e) {
            System.out.println("producent error");
        } finally {
            lock_w.unlock();
            lock_p.unlock();
            return sum < operations_limit;
        }
    }

    public  boolean consume(int val, Consumer4 c){
        try {
            lock_c.lock();

            lock_w.lock();

            while (buffor < val)
                con.await();

            buffor -= val;
            ++sum;

            prod.signal();
        } catch (Exception e) {
            System.out.println("consumer error");
        } finally {
            lock_w.unlock();
            lock_c.unlock();
            return sum < operations_limit;
        }
    }

    public void getTime(){
        System.out.println("czas procesora " + cpuTime/1000000 + " ms");
        long endTime = System.nanoTime();
        System.out.println("czas rzeczywisty " + (endTime - startTime)/1000000 + " ms");
    }

    public void addCpu(long time){
        cpuTime += time;
    }
}