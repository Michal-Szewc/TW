public class Counter {
    private int x;

    Counter(){
        x = 0;
    }

    public void change(int y){
        x += Math.signum(y);
    }
    public void setX(int _x){
        x = _x;
    }

    public int getX(){return x;}
}
