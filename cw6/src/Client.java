public abstract class Client {
    protected int val;
    protected boolean is_working;

    public int getChange(){return val;}

    public void hard_work(){
        for(int i=0;i<100;++i){
            if(i > 101){
                System.out.println("hmm");
            }
        }
    }
}
