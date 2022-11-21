import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int producer_num = 7;
        int consumer_num = 7;
        int random_offset = 1234;
        int limit = 10;
        int sleep_time = 3;
        long n_jobs = 10000;

        boolean AO = false;

        if(AO == true){

            long additional_jobs = 0;

            Proxy proxy = new Proxy(2 * limit, n_jobs);

            Producer[] producers = new Producer[producer_num];
            Consumer[] consumers = new Consumer[consumer_num];


            for (int i = 0; i < producer_num; ++i) {
                producers[i] = new Producer(proxy, new Random(i + random_offset), limit, sleep_time);
            }

            for (int i = 0; i < consumer_num; ++i) {
                consumers[i] = new Consumer(proxy, new Random(i + random_offset), limit, sleep_time);
            }

            for (Producer p : producers)
                p.start();

            for (Consumer c : consumers)
                c.start();

            try {
                proxy.getThread().join();
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Producer p: producers){
                additional_jobs += p.getJobs();
                p.done();
            }
            for (Consumer c: consumers){
                additional_jobs += c.getJobs();
                c.done();
            }

            System.out.println("Ilość dodatkowych prac: " + additional_jobs);
            System.out.println("Średnia dodatkowa praca miedzy wywołaniami: " + additional_jobs * sleep_time / n_jobs + "ms");


            for (Producer p: producers) {
                try{
                    p.join();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            for (Consumer c: consumers) {
                try{
                    c.join();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else {
            Monitor buffor = new Monitor( 2* limit, n_jobs);

            Producer4[] producers = new Producer4[producer_num];

            Consumer4[] consumers = new Consumer4[consumer_num];

            for (int i = 0; i < producer_num; i++)
                producers[i] = new Producer4(buffor, limit, i, new Random(i + random_offset));

            for (int i = 0; i < consumer_num; i++)
                consumers[i] = new Consumer4(buffor, limit, i, new Random(i + random_offset));

            for (Producer4 p: producers)
                p.start();

            for (Consumer4 c: consumers)
                c.start();

            for (Producer4 p: producers) {
                try {
                    p.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (Consumer4 c: consumers) {
                try {
                    c.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            buffor.getTime();
        }
    }
}