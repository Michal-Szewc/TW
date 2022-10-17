import java.util.concurrent.locks.*;

public class Monitor {
    private int buffor;
    private int limit;

    Lock lock = new ReentrantLock();
    Condition consumed  = lock.newCondition();
    Condition produced = lock.newCondition();

    Monitor(int _limit){
        buffor = 0 ;
        limit = _limit;
    }

    public  void produced(int val){
        try {
            lock.lock();
            while (buffor > limit - val)
                consumed.await();
            buffor+= val;
            System.out.println(buffor);

            produced.signal();
        } catch (Exception e) {
            System.out.println("producent error");
        } finally {
            lock.unlock();
        }
    }

    public  void consume(int val){
        try {
            lock.lock();
            while (buffor < val)
                produced.await();
            buffor-=val;
            System.out.println(buffor);

            consumed.signal();
        } catch (Exception e) {
            System.out.println("consumer error");
        } finally {
            lock.unlock();
        }
    }

    public int getLimit(){return limit;}
}
