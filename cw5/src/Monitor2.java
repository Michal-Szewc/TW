import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor2 extends Monitor {

    ReentrantLock lock = new ReentrantLock();

    boolean prod_w;

    Condition first_prod  = lock.newCondition();
    Condition rest_prod = lock.newCondition();

    boolean con_w;

    Condition first_con = lock.newCondition();
    Condition rest_con = lock.newCondition();

    Monitor2(int _limit, int _operations_limit){
        buffor = 0 ;
        limit = _limit;
        sum = 0;
        operations_limit = _operations_limit;
        startTime = System.nanoTime();
    }

    public  boolean produced(int val, Producent p){
        try {
            lock.lock();

            while (prod_w)
                rest_prod.await();

            prod_w = true;
            while (buffor > limit - val)
                first_prod.await();
            prod_w = false;

            buffor += val;
            ++sum;

            first_con.signal();
            rest_prod.signal();
        } catch (Exception e) {
            System.out.println("producent error");
        } finally {
            lock.unlock();
            return sum < operations_limit;
        }
    }

    public  boolean consume(int val, Konsumer c){
        try {
            lock.lock();

            while (con_w)
                rest_con.await();

            con_w = true;
            while (buffor < val)
                first_con.await();
            con_w = false;

            buffor -= val;
            ++sum;

            first_prod.signal();
            rest_con.signal();
        } catch (Exception e) {
            System.out.println("consumer error");
        } finally {
            lock.unlock();
            return sum < operations_limit;
        }
    }

    public void getTime(){
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000);
    }
}