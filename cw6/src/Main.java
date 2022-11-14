public class Main {
    public static void main(String[] args) {

        Proxy proxy = new Proxy(10);

        new Thread("scheduler thread"){
            public void run(){
                try {
                    proxy.dispatch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("consume thread"){
            public void run(){
                try {
                    BufforFuture result = proxy.consume(3);
                    System.out.println(result.get());
                    result = proxy.consume(2);
                    System.out.println(result.get());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        new Thread("produce thread"){
            public void run(){
                try {
                    sleep(1000);
                    proxy.produce(4);
                    sleep(1000);
                    proxy.produce(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }
}