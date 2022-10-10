public class Monitor {
    private int buffor;
    private int limit;

    Monitor(int _limit){
        buffor = 0 ;
        limit = _limit;
    }

    public synchronized void produced(){
        while(buffor == 1){
            try{
                wait();
            } catch (Exception e) {
                System.out.println("producer failed");
            }
        }

        System.out.println("buffor: 1");
        buffor = 1;

        notifyAll();
    }

    public synchronized void consume(){
        while(buffor == 0 ){
            try{
                wait();
            } catch (Exception e){
                System.out.println("consumer failed");
            }
        }

        System.out.println("buffor: 0");
        buffor = 0;

        notifyAll();
    }

    public synchronized int getBuffor(){return buffor;}
}
