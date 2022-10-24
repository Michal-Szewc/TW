import java.util.concurrent.locks.*;

public class Monitor {
    private int buffor;
    private int limit;

    boolean four_cond;

    Lock lock = new ReentrantLock();

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

                while (prod_w)
                    rest_prod.await();

                prod_w = true;
                while (buffor > limit - val)
                    first_prod.await();



                buffor+= val;
                //System.out.println(buffor);
                p.is_produced();

                prod_w = false;

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

    public  void consume(int val){
        if(four_cond){
            try {
                lock.lock();

                while (con_w)
                    rest_con.await();

                con_w = true;
                while (buffor < val)
                    first_con.await();
                buffor-=val;
                //System.out.println(buffor);

                con_w = false;

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
