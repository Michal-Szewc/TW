public class BufforFuture {

    private boolean done;
    private int data;

    BufforFuture(){
        data = -1;
        done = false;
    }

    boolean isDone(){
        return done;
    }

    public void setResult(int data) {
        this.data = data;
        this.done = true;
    }

    synchronized int get() throws InterruptedException{
        if(isDone())
            return data;
        wait();
        return data;
    }
}
