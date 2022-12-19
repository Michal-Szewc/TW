import org.jcsp.lang.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Secretary implements CSProcess{

    private One2OneChannel next;
    private One2OneChannel prev;
    private One2OneChannel clientRes;
    private One2OneChannel clientReq;
    private String name;
    private boolean clientNeeds;

    private Queue<Token> tokensIn = new LinkedList<>();
    private Queue<Token> tokensOut = new LinkedList<>();

    private boolean passing;

    public Secretary(One2OneChannel _clientRes, One2OneChannel _clientReq, int id, boolean _passing){
        this.clientRes = _clientRes;
        this.clientReq = _clientReq;
        this.name = "[ Secretary " + id + " ] ";

        this.clientNeeds = true;

        this.passing = _passing;
    }

    @Override
    public void run() {
        final Skip skip = new Skip();
        final Guard[] guard = {clientReq.in(), prev.in(), skip};
        final Alternative alt = new Alternative(guard);
        while(true){
            int index=alt.select();
            switch (index){
                // odbierz wiadomosc klienta
                case 0:
                    System.out.println(this.name + "odbiera od klienta");
                    Message clientRequest = (Message) clientReq.in().read();
                    if(clientRequest.getType() == MessageType.BUFFER_REQUEST){
                        if(tokensIn.isEmpty()){
                            clientNeeds = true;
                            Message response = new Message(MessageType.TOKEN, -1);
                            System.out.println(this.name + "informuje klienta o braku tokenow");
                            clientRes.out().write(response);
                        }
                        else {
                            Message response = new Message(MessageType.TOKEN, tokensIn.remove().getID());
                            System.out.println(this.name + "wysyła token klientowi");
                            clientRes.out().write(response);
                        }
                    }
                    else{
                        if(clientRequest.getType() == MessageType.TOKEN_RESPONSE_SUCCESS)
                            clientNeeds = false;
                        else
                            clientNeeds = true;

                        tokensOut.add(new Token(clientRequest.getVal()));
                        System.out.println(this.name + "otrzymała token z powrotem");
                    }


                    break;
                // otrzymaj od poprzedniego
                case 1:
                    System.out.println(this.name + "odbiera token od poprzedniej");
                    Message prevMess = (Message) prev.in().read();
                    tokensIn.add(new Token(prevMess.getVal()));
                    break;
                // wyslij dalej
                case 2:
                    System.out.println(this.name + "chce przesłać token dalej");
                    if(!tokensOut.isEmpty()) {
                        Message nextMess = new Message(MessageType.TOKEN, tokensOut.remove().getID());
                        next.out().write(nextMess);
                        System.out.println(this.name + "wysłała token dalej");
                    }
                    else if(!clientNeeds && tokensIn.size() > 0){
                        Message nextMess = new Message(MessageType.TOKEN, tokensIn.remove().getID());
                        next.out().write(nextMess);
                        System.out.println(this.name + "wysłała swiezy token dalej");
                    }
                    break;
            }
        }
    }

    public void setPrev(One2OneChannel prev) {
        this.prev = prev;
    }

    public void setNext(One2OneChannel next) {
        this.next = next;
    }

    public void addToken(Token t) {
        tokensIn.add(t);
    }
}
