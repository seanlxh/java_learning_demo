import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExcutorTest2 implements Runnable {


    public String name;

    public ThreadPoolExcutorTest2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1, //corePoolSize
                2,    //maximumPoolSize
                1L,
                TimeUnit.SECONDS,
                workQueue
        );
        threadPool.execute(new ThreadPoolExcutorTest2("任务1"));
        threadPool.execute(new ThreadPoolExcutorTest2("任务2"));
        threadPool.execute(new ThreadPoolExcutorTest2("任务3"));
        threadPool.execute(new ThreadPoolExcutorTest2("任务4"));
        threadPool.execute(new ThreadPoolExcutorTest2("任务5"));
        threadPool.execute(new ThreadPoolExcutorTest2("任务6"));
        threadPool.shutdown();

    }
}