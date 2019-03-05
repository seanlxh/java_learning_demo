import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


public class NettyTest {
    static ExecutorService es = Executors.newFixedThreadPool(2);
    static class Netty{
        private String name;
        private Netty(String name){
            this.name = name;
        }
    }
    public static void doStm(final ICallback callback){
        Thread t = new Thread(){
            public void run(){
                System.out.println("do Sth");
                try{
                    Thread.sleep(5000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("a1","这是我返回的参数字符串");
                callback.callback(params);
            }
        };
        es.execute(t);
        es.execute(t);
    }
    public static void main(String[] args){
        doStm(new ICallback() {
            @Override
            public void callback(Map<String, Object> params) {
                System.out.println("单个线程也已经处理完毕了，返回参数a1=" + params.get("a1"));
            }
        });
        System.out.println("主线任务已经执行完了");

        Callable<Netty> c = new Callable<Netty>() {
            @Override
            public Netty call() throws Exception {
                Thread.sleep(5000);
                return new Netty("张三");
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Netty> fn = es.submit(c);
        while (!fn.isDone()) {
            try {
                //处理完毕后返回的结果
                Netty nt = fn.get();
                System.out.println(nt.name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}



