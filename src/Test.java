
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {

    public static void main(String args[]){
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future> list = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i ++){
            Callable c = new Callable() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            };
            Future f = pool.submit(c);
            list.add(f);
        }
        pool.shutdown();

    }
}
