import java.lang.Thread;

public class Synchronize implements Runnable{

    private void function1(){
        System.out.println("1"+ Thread.currentThread().getName());
    }

    private static void function2() throws InterruptedException {
        System.out.println("2"+ Thread.currentThread().getName());
        Thread.sleep(10000);
    }

    private void function3(){
        System.out.println("3"+ Thread.currentThread().getName());
    }
    @Override
    public void run() {
        function1();
        try {
            function2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        function3();
    }

    public static void main(String[] args){
        Thread thread1 = new Thread(new Synchronize(),"test1");
        Thread thread2 = new Thread(new Synchronize(),"test2");

        thread1.start();
        thread2.start();
    }


}
