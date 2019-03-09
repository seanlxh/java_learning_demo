import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPReactor implements Runnable{
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public TCPReactor(int port) throws IOException{
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);
        serverSocketChannel.socket().bind(addr);
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            System.out.println("Waiting for new event on port:"+serverSocketChannel.socket().getLocalPort()+"...");
            try{
                if(selector.select() == 0){
                    continue;
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()){
                dispatch((SelectionKey) it.next());
                it.remove();
            }
        }
    }

    private void dispatch(SelectionKey key){
        Runnable r = (Runnable)(key.attachment());
        if(r != null)
            r.run();
    }
}
