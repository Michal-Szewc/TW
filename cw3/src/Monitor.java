import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor {
    private int buffor;
    private int limit;

    boolean four_cond;

    final boolean has_waiters = true;

    ReentrantLock lock = new ReentrantLock();

    boolean prod_w;

    Condition first_prod  = lock.newCondition();
    Condition rest_prod = lock.newCondition();

    boolean con_w;

    Condition first_con = lock.newCondition();
    Condition rest_con = lock.newCondition();

    Monitor(int _limit, boolean _four_cond){
        buffor = 0 ;
        limit = _limit;
        four_cond = _four_cond;
        prod_w = false;
        con_w = false;
    }

    public  void produced(int val, Producent p){
        if(four_cond){
            try {
                lock.lock();

                if(has_waiters) {
                    System.out.println(p.getThreadName() + " awaits for first");

                    while (lock.hasWaiters(first_prod))
                        rest_prod.await();

                    System.out.println(p.getThreadName() + " is first");

                    while (buffor > limit - val)
                        first_prod.await();

                    System.out.println(p.getThreadName() + " produced " + val);
                }
                else{
                    while (prod_w)
                        rest_prod.await();

                    prod_w = true;
                    while (buffor > limit - val)
                        first_prod.await();
                    prod_w = false;

                    p.is_produced();
                }

                buffor += val;
                //System.out.println(buffor);

                first_con.signal();
                rest_prod.signal();
            } catch (Exception e) {
                System.out.println("producent error");
            } finally {
                lock.unlock();
            }
        }
        else{
            try {
                lock.lock();

                while (buffor > limit - val)
                    first_prod.await();



                buffor+= val;
                //System.out.println(buffor);

                p.is_produced();

                first_con.signal();
            } catch (Exception e) {
                System.out.println("producent error");
            } finally {
                lock.unlock();
            }
        }
    }

    public  void consume(int val, Konsumer c){
        if(four_cond){
            try {
                lock.lock();

                if(has_waiters) {
                    System.out.println(c.getThreadName() + " awaits for first");

                    while (lock.hasWaiters(first_con))
                        rest_con.await();

                    System.out.println(c.getThreadName() + " is first");

                    while (buffor < val)
                        first_con.await();

                    System.out.println(c.getThreadName() + " consumed " + val);
                }
                else {
                    while (con_w)
                        rest_con.await();

                    con_w = true;
                    while (buffor < val)
                        first_con.await();
                    con_w = false;
                }
                buffor -= val;
                //System.out.println(buffor);

                first_prod.signal();
                rest_con.signal();
            } catch (Exception e) {
                System.out.println("consumer error");
            } finally {
                lock.unlock();
            }
        }
        else{
            try {
                lock.lock();

                while (buffor < val)
                    first_con.await();
                buffor-=val;
                //System.out.println(buffor);

                first_prod.signal();
            } catch (Exception e) {
                System.out.println("consumer error");
            } finally {
                lock.unlock();
            }
        }
    }

    public int getLimit(){return limit;}
}