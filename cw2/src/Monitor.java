public class Monitor {
    private int buffor;
    private int limit;

    Monitor(int _limit){
        buffor = _limit/2;
        limit = _limit;
    }

    public synchronized void produced(){
        while(buffor == limit){
            try{
                wait();
            } catch (Exception e) {
                System.out.println("producer failed");
            }
        }

        System.out.println(buffor);
        buffor++;

        notify();
    }

    public synchronized void consume(){
        while(buffor == 0 ){
            try{
                wait();
            } catch (Exception e){
                System.out.println("consumer failed");
            }
        }

        System.out.println(buffor);
        buffor--;

        notify();
    }

    public synchronized int getBuffor(){return buffor;}
}
