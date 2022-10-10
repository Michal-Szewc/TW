import java.io.*;

public class Main {
    public static void main(String[] args) {

        Counter c = new Counter();

        // szybciej program przestaje działać, gdy zwiększamy ilość operacji, a nie ilość wątków

        int Number_of_threads = 100000;
        int Number_of_operations = 1000;
        boolean slow = true;

        Lab_thread[] threads_add = new Lab_thread[Number_of_threads / 2];
        Lab_thread[] threads_subtract = new Lab_thread[Number_of_threads / 2];

        for(int i=0;i<Number_of_threads / 2;i++){
            threads_add[i] = new Lab_thread(c);
            threads_subtract[i] = new Lab_thread(c);
        }

        for(int i=0; i < Number_of_threads / 2; i++){
            threads_add[i].run(Number_of_operations, slow);
            threads_subtract[i].run(-Number_of_operations, slow);
        }

        try{
            for( Lab_thread thread: threads_add)
                thread.join();
            for( Lab_thread thread: threads_subtract)
                thread.join();
        }
        catch ( Exception e) {
            System.out.println("ups");
        }

        System.out.println(c.getX());
    }
}