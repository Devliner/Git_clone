package Curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import utils.Constants;

/**
 * Create by WangLei 2018/6/6 20:48
 * description: 使用 Curator 更新数据内容
 */
public class Set_Data_Sample {
    static String path = "/zk-book";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Constants.ZOOKEEPER_ADRESS)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path,"init".getBytes());
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("Success set mode for : " + path + ", new version: " + client.setData().withVersion(stat.getVersion()).forPath(path).getVersion());
        client.setData().withVersion(stat.getVersion()).forPath(path);
    }
}
