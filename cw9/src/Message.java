import org.jcsp.lang.One2OneChannel;

public class Message {
    private MessageType type;
    private int val;

    public Message(MessageType _type, int _val){
        this.type = _type;
        this.val = _val;
    }

    public Message(MessageType type) {
        this.type = type;
    }

    public int getVal() {
        return val;
    }

    public MessageType getType() {
        return type;
    }
}
