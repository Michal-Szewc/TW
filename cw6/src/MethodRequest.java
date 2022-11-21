public abstract class MethodRequest {

    public abstract boolean guard();

    public abstract void call();
}

class Produce extends MethodRequest {
    Servant servant;
    BufferFuture result;
    int val;

    Produce(Servant _servant, BufferFuture _result, int _val){
        servant = _servant;
        result = _result;
        val = _val;
    }


    @Override
    public boolean guard() {
        return servant.leftInBuffer() >= val;
    }

    @Override
    public void call() {
        result.setResult(servant.produce(val));
    }
}

class Consume extends MethodRequest {

    Servant servant;
    BufferFuture result;
    int val;

    Consume(Servant _servant, BufferFuture _result, int _val){
        servant = _servant;
        result = _result;
        val = _val;
    }
    @Override
    public boolean guard() {
        return servant.getBuffer() >= val;
    }

    @Override
    public void call() {
        result.setResult(servant.consume(val));
    }
}
