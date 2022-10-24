public class Producent extends Thread{
    Monitor m;
    private int limit;
    private int count;

    private boolean special;

    Producent(Monitor _m, int _limit, boolean _special){m = _m; limit = _limit;count=0;special = _special;}

    public void run(){
        System.out.println("producer run");

        if(!special){
            while(true){
                int val = (int)((Math.random() * (limit - 1)) + 1);
                m.produced(val, this);
            }
        }
        else{
            while(true){
                m.produced(limit/2, this);
            }
        }
    }

    public void is_produced(){
        if(special)
            System.out.println("I'm special!");
        count++;
        System.out.println(count);
    }
}
