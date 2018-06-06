package WatcherDemo;

import org.apache.zookeeper.*;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import utils.Constants;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * author:WangLei
 * date:2018/6/6
 * time:8:55
 * description:
 */
public class Watch_Exist implements Watcher{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static String path = "/watch_exist";

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        ZooKeeper zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, new Watch_Exist(), false);
        connectedSemaphore.await();

        zooKeeper.exists(path,new Watch_Exist());

        zooKeeper.create(path,"existWatch".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        zooKeeper.setData(path,"existWatch setData".getBytes(),-1);
        zooKeeper.delete(path,-1);

        Thread.sleep(3000);

    }

    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            if(Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
                connectedSemaphore.countDown();
            }else if(Event.EventType.NodeDataChanged == watchedEvent.getType()){
                System.out.println("NodeChildrenChanged Event");
            }else if(Event.EventType.NodeCreated == watchedEvent.getType()){
                System.out.println("NodeCreated Event");
            }else if(Event.EventType.NodeDeleted == watchedEvent.getType()){
                System.out.println("NodeDeleted Event");
            }else{
                System.out.println("Dont't in EventType ");
            }
        }
    }
}
