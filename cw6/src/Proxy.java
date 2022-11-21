public class Proxy {

    Scheduler scheduler;
    Servant servant;



    Proxy(int _limit, long goal){
        scheduler = new Scheduler(goal);
        servant = new Servant(_limit);
    }

    BufferFuture produce(int val) throws InterruptedException {
        BufferFuture result = new BufferFuture();
        scheduler.enqueue(new Produce(servant, result, val));
        return result;
    }

    BufferFuture consume(int val) throws InterruptedException {
        BufferFuture result = new BufferFuture();
        scheduler.enqueue(new Consume(servant, result, val));
        return result;
    }

    Thread getThread(){return scheduler.getThread();}
}
