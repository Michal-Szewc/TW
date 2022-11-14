import java.sql.Array;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int producent_num = 5;
        int consumer_num = 5;
        int random_offset = 1234;
        int limit = 5;

        Proxy proxy = new Proxy(10);

        Producent[] producents = new Producent[producent_num];
        Consumer[] consumers = new Consumer[consumer_num];


        for (int i = 0; i < producent_num; ++i) {
            producents[i] = new Producent(proxy, new Random(i + random_offset), limit);
        }

        for (int i = 0; i < consumer_num; ++i) {
            consumers[i] = new Consumer(proxy, new Random(i + random_offset), limit);
        }

        for (Producent p : producents)
            p.start();

        for (Consumer c : consumers)
            c.start();
    }
}