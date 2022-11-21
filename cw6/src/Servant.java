public class Servant {
    int buffer;
    int limit;

    Servant(int _limit){
        limit = _limit;
        buffer = 0;
    }

    int getBuffer(){
        return buffer;
    }

    int leftInBuffer(){
        return limit - buffer;
    }

    int produce(int val){
        buffer += val;
        return buffer;
    }
    int consume(int val){
        buffer -= val;
        return buffer;
    }
}
