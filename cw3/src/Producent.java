public class Producent extends Thread{
    Monitor m;

    Producent(Monitor _m){m = _m;}

    public void run(){
        System.out.println("producer run");

        while(true){
            int val = (int)((Math.random() * (m.getLimit()/2 - 1)) + 1);
            m.produced(val);
        }
    }
}
