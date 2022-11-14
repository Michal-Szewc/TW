public class BufforFuture {

    private boolean done;
    volatile private int data;

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

    int get() throws InterruptedException{
        return data;
    }
}
