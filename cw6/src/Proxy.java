public class Proxy {

    Scheduler scheduler;
    Servant servant;



    Proxy(int _limit){
        scheduler = new Scheduler();
        servant = new Servant(_limit);
    }

    BufforFuture produce(int val) throws InterruptedException {
        BufforFuture result = new BufforFuture();
        scheduler.enqueue(new Produce(servant, result, val));
        return result;
    }

    BufforFuture consume(int val) throws InterruptedException {
        BufforFuture result = new BufforFuture();
        scheduler.enqueue(new Consume(servant, result, val));
        return result;
    }
}
