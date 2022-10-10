public class Lab_thread extends Thread{
    private Counter c;

    Lab_thread(Counter _c){
        c = _c;
    }

    public void run(int x, boolean slow){
        if (slow) {
            for(int i=0;i<Math.abs(x);i++)
                c.change(x);
            try{
                Thread.sleep(10);
            } catch (Exception e){
                System.out.println("ups sleep");
            }
        }
        else{
            for(int i=0;i<Math.abs(x);i++)
                c.change(x);
        }
    }
}
