import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler {
    private LinkedBlockingQueue<MethodRequest> activeQueue;
    private ArrayDeque<MethodRequest> waitingQueue;

    Scheduler(){
        activeQueue = new LinkedBlockingQueue<>();
        waitingQueue = new ArrayDeque<>();

        new Thread("scheduler thread"){
            public void run(){
                try {
                    dispatch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void enqueue(MethodRequest m) throws InterruptedException {
        activeQueue.put(m);
    }

    void dispatch() throws InterruptedException {
        while(true){
            if(!waitingQueue.isEmpty() && waitingQueue.peek().guard()){
                MethodRequest m = waitingQueue.pop();
                m.call();
            }
            else{
                MethodRequest m = activeQueue.take();
                if (m.guard())
                    m.call();
                else
                    waitingQueue.add(m);
            }
        }
    }
}
