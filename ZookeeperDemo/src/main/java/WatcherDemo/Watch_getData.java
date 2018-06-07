package WatcherDemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import utils.Constants;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Create by WangLei 2018/6/6 11:12
 * description: 测试创建 Watch 的 getData API，对应的三种 EventType。
 */
public class Watch_getData implements Watcher{
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String path = "/watch_getData";


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, new Watch_getData());
        countDownLatch.await();


        zooKeeper.create(path,"getData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.getData(path,new Watch_getData(),new Stat());
        zooKeeper.setData(path,"new setData".getBytes(),-1);

        zooKeeper.getData(path,new Watch_getData(),new Stat());
        zooKeeper.delete(path,-1);
    }

    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()){
            if(Event.EventType.None == event.getType() && null == event.getPath()){
                countDownLatch.countDown();
                System.out.println("Connect Success");
            }else if(Event.EventType.NodeDataChanged == event.getType()){
                System.out.println("NodeDataChanged event");
            }else if(Event.EventType.NodeDeleted == event.getType()){
                System.out.println("NodeDeleted event");
            }else {
                System.out.println("input wrong");
            }
        }


    }
}
