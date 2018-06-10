package Curator.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import utils.Constants;

/**
 * Create by WangLei 2018/6/6 20:39
 * description: 使用 Curator 获取内容
 */
public class Get_Data_Sample {
    static String path = "/zk-book";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(5000)
                .connectString(Constants.ZOOKEEPER_ADRESS)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path,"init".getBytes());
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));
    }
}
