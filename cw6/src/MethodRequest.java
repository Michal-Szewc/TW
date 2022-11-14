public abstract class MethodRequest {

    public abstract boolean guard();

    public abstract void call();
}

class Produce extends MethodRequest {
    Servant servant;
    int val;

    Produce(Servant _servant, int _val){
        servant = _servant;
        val = _val;
    }


    @Override
    public boolean guard() {
        return servant.leftInBuffor() >= val;
    }

    @Override
    public void call() {
        servant.produce(val);
    }
}

class Consume extends MethodRequest {

    Servant servant;
    BufforFuture result;
    int val;

    Consume(Servant _servant, BufforFuture _result, int _val){
        servant = _servant;
        result = _result;
        val = _val;
    }
    @Override
    public boolean guard() {
        return servant.getBuffor() >= val;
    }

    @Override
    public void call() {
        synchronized (result) {
            result.setResult(servant.consume(val));
            result.notify();
        }
    }
}
