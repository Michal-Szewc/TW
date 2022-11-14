public class Proxy {

    Scheduler scheduler;
    Servant servant;



    Proxy(int _limit){
        scheduler = new Scheduler();
        servant = new Servant(_limit);
    }

    void produce(int val) throws InterruptedException {
        MethodRequest m = new Produce(servant, val);
        scheduler.enqueue(m);
    }

    BufforFuture consume(int val) throws InterruptedException {
        BufforFuture result = new BufforFuture();
        MethodRequest m = new Consume(servant, result, val);
        scheduler.enqueue(m);
        return result;
    }

    void dispatch() throws InterruptedException{
        scheduler.dispatch();
    }

}
