public class Servant {
    int buffor;
    int limit;

    Servant(int _limit){
        limit = _limit;
        buffor = 0;
    }

    int getBuffor(){
        return buffor;
    }

    int leftInBuffor(){
        return limit - buffor;
    }

    void produce(int val){
        buffor += val;
    }
    int consume(int val){
        buffor -= val;
        return buffor;
    }
}
