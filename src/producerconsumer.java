import java.lang.Thread;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class Data {
    private String id;

    private String name;

    public Data(String id,String name){
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data [id=" + id + ", name=" + name + "]";
    }
}

class Provider implements Runnable{

    private BlockingQueue<Data> queue;

    private volatile boolean isRunning = true;

    private static AtomicInteger count = new AtomicInteger();

    private Random r = new Random();

    public Provider(BlockingQueue<Data> queue){
        this.queue = queue;
    }


    @Override
    public void run() {
        while (isRunning){
            try{
                Thread.sleep(r.nextInt(1000));
                int id = count.incrementAndGet();
                Data data = new Data(Integer.toString(id),"数据"+id);
                System.out.println("当前线程:"+ Thread.currentThread().getName() + ",获取了数据，id为:"+ id+ ",进行装载到公共缓冲区中。。。");
                if(!this.queue.offer(data,2, TimeUnit.SECONDS)){
                    System.out.print("提交缓冲区数据失败");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.print("aaa");
        }
    }
    public void stop(){
        this.isRunning = false;
    }
}


class Consumer implements Runnable{

    private BlockingQueue<Data> queue;

    private Random r = new Random();

    public Consumer(BlockingQueue<Data> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Data data = queue.take();
                Thread.sleep(r.nextInt(1000));
                System.out.print("当前消费线程"+Thread.currentThread().getName() +",消费成功，消费id为"+data.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class producerconsumer {
    public static void main(String[] args){
        //内存缓冲区
        BlockingQueue<Data> queue = new LinkedBlockingQueue<Data>(10);
        //生产者
        Provider p1 = new Provider(queue);
        Provider p2 = new Provider(queue);
        Provider p3 = new Provider(queue);

        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);

        //创建线程池，这是一个缓存的线程池，可以创建无穷大的线程，没有任务的时候不创建线程，空闲线程存活的时间为60s。
        ExecutorService cachepool = Executors.newCachedThreadPool();
        cachepool.execute(p1);
        cachepool.execute(p2);
        cachepool.execute(p3);
        cachepool.execute(c1);
        cachepool.execute(c2);
        cachepool.execute(c3);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p1.stop();
        p2.stop();
        p3.stop();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
