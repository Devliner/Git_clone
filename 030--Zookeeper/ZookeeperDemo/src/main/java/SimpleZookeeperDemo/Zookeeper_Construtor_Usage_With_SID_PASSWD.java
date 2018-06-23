package SimpleZookeeperDemo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import utils.Constants;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Create by WangLei 2018/6/6 15:00
 * description: 在 Zookeeper 的构造器中允许传入 sessionId 和 sessionPasswd，可以恢复之前的会话，以维持之前会话的有效性。
 */
public class Zookeeper_Construtor_Usage_With_SID_PASSWD implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 1000, new Zookeeper_Construtor_Usage_With_SID_PASSWD());
        connectedSemaphore.await();
        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();

        // Use illegal sessionId and sessionPassWd
        zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 1000, new Zookeeper_Construtor_Usage_With_SID_PASSWD(), 1l, "test".getBytes());
        System.out.println("--------");
        // Use correct sessionId and sessionPassWd
        zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS,1000,new Zookeeper_Construtor_Usage_With_SID_PASSWD(),sessionId,sessionPasswd);
        System.out.println("==============");
        Thread.sleep(3000);

    }
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: " + event);
        if(Event.KeeperState.SyncConnected == event.getState()){
            connectedSemaphore.countDown();
        }
    }
}
