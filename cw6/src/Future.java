public class Future {

    private boolean done;
    private int data;

    Future(){
        data = -;
        done = false;
    }

    public boolean is_done(){return done;}

    public void set_data(int _data){
        data = _data;
        done = true;
    }

    public int getData(){
        if(done)
            return data;
        throw new Exception("no panie czekaj");
    }
}
