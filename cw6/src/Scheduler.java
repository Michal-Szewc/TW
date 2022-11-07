import java.util.List;

public class Scheduler {

    final private int bufor_limit;
    private int bufor;

    private List<Thread> clients;
    private List<Thread> vip_clients;

    Scheduler(int _bufor){bufor_limit = _bufor; bufor = 0;}

    Future produce(int val){
        Future to_return = new Future();
        clients.add(new Thread(() -> {
            int to_produce = val;

            while(bufor + to_produce >= bufor_limit){
                ...
            }

            bufor += to_produce;

            to_return.setData(0);
        }));

        return to_return;
    }

}
