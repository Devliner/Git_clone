package SimpleZookeeperDemo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import utils.Constants;

import java.io.IOException;

/**
 * Create by WangLei 2018/6/6 15:30
 * description: 使用 Zookeeper 进行控制权限，权限模式 "digest"，
 */
public class AuthSample_Get {
    final static String PATH = "/zk-book-auth_test";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper1 = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, null);
        // scheme:权限控制模式，auth:具体的权限信息
        zooKeeper1.addAuthInfo("digest","foo:true".getBytes());
        zooKeeper1.create(PATH,"init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        System.out.println("Path Create Success ");

        // 当加入权限后，后来的人就无法操作这个目录
        ZooKeeper zooKeeper2 = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, null);
        zooKeeper2.getData(PATH,false,null);
    }
}
