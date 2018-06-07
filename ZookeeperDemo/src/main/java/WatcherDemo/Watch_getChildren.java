package WatcherDemo;

import org.apache.zookeeper.*;
import utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Create by WangLei 2018/6/6 12:24
 * description: 测试创建 Watch 的 getChildren API，对应的两种 EventType。
 */
public class Watch_getChildren implements Watcher{
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String path = "/watch_getChildren";
    private static String path_children = "/watch_getChildren/children";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, new Watch_getChildren());
        //zooKeeper.delete(path,-1);
        zooKeeper.create(path,"watch_getChildren".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        List<String> children = zooKeeper.getChildren(path, new Watch_getChildren());
        zooKeeper.create(path_children,"path children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

        zooKeeper.getChildren(path,new Watch_getChildren());
        zooKeeper.setData(path,"new parent".getBytes(),-1);

        zooKeeper.getChildren(path,new Watch_getChildren());
        zooKeeper.setData(path_children,"set children".getBytes(),-1);

        zooKeeper.getChildren(path,new Watch_getChildren());
        zooKeeper.delete(path_children,-1);

        zooKeeper.getChildren(path,new Watch_getChildren());
        zooKeeper.delete(path,-1);


    }

    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()){
            if(Event.EventType.None == event.getType() && null == event.getPath()){
                countDownLatch.countDown();
                System.out.println("Connect Success");
            }else if(Event.EventType.NodeChildrenChanged == event.getType()){
                System.out.println("NodeChildrenChanged");
            }else if(Event.EventType.NodeDeleted == event.getType()){
                System.out.println("NodeDeleted");
            }else{
                System.out.println("No Change");
            }
        }
    }
}
