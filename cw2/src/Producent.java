public class Producent extends Thread{
    Monitor m;

    Producent(Monitor _m){m = _m;}

    public void run(){
        System.out.println("producer run");

        while(true){
            m.produced();
        }
    }
}
