package SimpleZookeeperDemo;

import utils.Constants;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Create by WangLei 2018/6/10 11:10
 * description:
 */
public class AuthSample_Super {

    final static String PATH = "/zk-book";

    public static void main(String[] args) throws IOException {
        ZooKeeper zookeeper = new ZooKeeper(Constants.ZOOKEEPER_ADRESS, 5000, null);
        zookeeper.addAuthInfo("digest","foo:true".getBytes());
    }
}
