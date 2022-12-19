import org.jcsp.lang.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final int seed_offset = 12;

        final int client_num = 4; // number of consumers and producers
        final int buffer_size = 10;
        final int buffer_count = 20;
        List<CSProcess> processes = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        ArrayList<Secretary> secretaries = new ArrayList<>();

        Buffer[] buffer = new Buffer[buffer_count];
        for(int i = 0; i < buffer_count; ++i)
            buffer[i] = new Buffer(buffer_size);

        int[] values = {buffer_size/2, -buffer_size/2};

        for(int i = 0; i < client_num; ++i) {
            final One2OneChannel to_client = Channel.one2one();
            final One2OneChannel from_client = Channel.one2one();

            clients.add(new Client(from_client, to_client, values[i%2], (i + seed_offset) * 2, i, buffer));
            secretaries.add(new Secretary(to_client, from_client, i, i%2 == 0));
        }

        for(int i = 0; i < client_num; ++i){
            final One2OneChannel connection = Channel.one2one();
            secretaries.get(i).setNext(connection);
            secretaries.get((i + 1) % client_num).setPrev(connection);
        }

        for(int i = 0; i < buffer_count; ++i){
            secretaries.get(i % client_num).addToken(new Token(i));
        }

        for(Secretary s: secretaries)
            processes.add(s);

        for(Client c: clients)
            processes.add(c);

        Parallel par = new Parallel(processes.toArray(CSProcess[]::new));
        par.run();
    }
}