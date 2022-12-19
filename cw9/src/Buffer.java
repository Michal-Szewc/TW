public class Buffer {
    public int buffer;

    private int limit;

    public Buffer(int _limit) {
        this.buffer = 0;
        this.limit = _limit;
    }

    public boolean isEnough(int val){
        return val + buffer <= limit && val + buffer >= 0;
    }

    public void add(int val){
        buffer += val;
    }
}
