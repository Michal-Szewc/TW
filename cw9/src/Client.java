import org.jcsp.lang.*;

import java.util.Random;

public class Client implements CSProcess{
    private One2OneChannel req;
    private One2OneChannel res;
    int limit;
    int sign;
    private Random rand;
    private String name;

    boolean try_again;
    Buffer[] bufferRef;

    public Client(One2OneChannel _req, One2OneChannel _res, int _limit, long rand_seed, int id, Buffer[] _buffer){
        this.req = _req;
        this.res = _res;
        this.limit = Math.abs(_limit);
        this.rand=new Random(rand_seed);
        if(_limit > 0) {
            this.sign = 1;
            this.name = "[ Producer " + id + " ] ";
        }
        else {
            this.sign = -1;
            this.name = "[ Consumer " + id + " ] ";
        }
        this.try_again = false;
        this.bufferRef = _buffer;
    }

    public int getRandomNumber() {
        // return this.sign * (rand.nextInt(this.limit) + 1);
        return this.sign;
    }


    @Override
    public void run() {
        int val = getRandomNumber();
        while(true){
            if(!this.try_again)
                val = getRandomNumber();
            Message request = new Message(MessageType.BUFFER_REQUEST, val);
            System.out.println(this.name + "chce zmienic o  " + val + " bufor");
            req.out().write(request);
            Message response = (Message) res.in().read();
            if(response.getVal()>=0) {
                try_again = false;
                Message inform;

                System.out.println(this.name + "pobrałem: token bufora");
                int ID = response.getVal();
                if (bufferRef[ID].isEnough(val)) {
                    bufferRef[ID].add(val);
                    System.out.println(this.name + "Stan bufora " + ID + " : " + bufferRef[ID].buffer);
                    inform = new Message(MessageType.TOKEN_RESPONSE_SUCCESS, ID);
                }
                else {
                    System.out.println(this.name + "Nie udało się zmienić, zły stan bufora: " + bufferRef[ID].buffer);
                    inform = new Message(MessageType.TOKEN_RESPONSE_FAILURE, ID);
                }
                req.out().write(inform);

            } else {
                System.out.println(this.name + "oddstawiam zadanie na później");
                try_again = true;
            }
        }
    }
}
