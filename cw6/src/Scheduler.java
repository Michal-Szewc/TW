import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler {
    private LinkedBlockingQueue<MethodRequest> activeQueue;
    private LinkedBlockingQueue<MethodRequest> waitingQueue;

    Scheduler(){
        activeQueue = new LinkedBlockingQueue<>();
        waitingQueue = new LinkedBlockingQueue<>();
    }

    void enqueue(MethodRequest m) throws InterruptedException {
        activeQueue.put(m);
    }

    void dispatch() throws InterruptedException {
        while(true){
            if(!waitingQueue.isEmpty() && waitingQueue.peek().guard()){
                MethodRequest m = waitingQueue.take();
                m.call();
            }
            else if(!activeQueue.isEmpty()){
                MethodRequest m = activeQueue.take();
                if (m.guard())
                    m.call();
                else
                    waitingQueue.put(m);
            }
        }
    }
}
