package SimpleZookeeperDemo;

import org.apache.zookeeper.*;
import utils.Constants;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Create by WangLei 2018/6/6 15:48
 * description: 使用异步创建目录，同步和异步的接口有所区别。
 *
 * 在同步接口调用过程中，我们需要关注接口抛出异常的可能；但是异步接口中，接口本身不会抛出异常的，所有的异常都会在回调函数中通过 Result Code 来体现
 */
public class Zookeeper_Create_API_ASync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, new Zookeeper_Create_API_ASync_Usage());
        connectedSemaphore.await();

        zooKeeper.create("/zk-test-ephemeral-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL,new IStringCallback(),"I am context");
        zooKeeper.create("/zk-test-ephemeral-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL,new IStringCallback(),"I am context");

        zooKeeper.create("/zk-test-ephemeral-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL,new IStringCallback(),"I am context");


        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()){
            connectedSemaphore.countDown();
            System.out.println("connected success");
        }
    }
}


class IStringCallback implements AsyncCallback.StringCallback{

    /**
     *
     * @param resultCode 异步调用结果的响应码，0 为成功
     * @param path 创建的目录
     * @param ctx  调用创建目录时传入的参数值
     * @param name 如果创建Znode 不是顺序型，则name=path;否则name就是path+随机码
     */
    public void processResult(int resultCode, String path, Object ctx, String name) {
        System.out.println("Create path result:[" + resultCode + ", " + path + ", "
         + ctx + ", real path name: " + name);
    }
}
