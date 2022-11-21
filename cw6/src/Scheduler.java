import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler {
    private LinkedBlockingQueue<MethodRequest> activeQueue;
    private ArrayDeque<MethodRequest> waitingQueue;

    private long startTime;
    private long n_jobs;
    private long goal;
    boolean working;
    private Thread t;

    Scheduler(long _goal){

        goal = _goal;
        n_jobs = 0;
        working = true;

        startTime = System.nanoTime();

        activeQueue = new LinkedBlockingQueue<>();
        waitingQueue = new ArrayDeque<>();

        t = new Thread("scheduler thread"){
            public void run(){
                try {
                    dispatch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    Thread getThread(){return t;}

    void enqueue(MethodRequest m) throws InterruptedException {
        activeQueue.put(m);
    }

    void dispatch() throws InterruptedException {
        while(working){
            if(!waitingQueue.isEmpty() && waitingQueue.peek().guard()){
                MethodRequest m = waitingQueue.pop();
                m.call();
                ++n_jobs;
                check();
            }
            else{
                MethodRequest m = activeQueue.take();
                if (m.guard()) {
                    m.call();
                    ++n_jobs;
                    check();
                }
                else
                    waitingQueue.add(m);
            }
        }
    }

    void check(){
        if (n_jobs == goal){
            working = false;
            long endTime = System.nanoTime();
            System.out.println("Czas wykonania: " + (endTime - startTime) / 1000000 + "ms");
            System.out.println("Ilość prac: " + n_jobs);
        }
    }
}
