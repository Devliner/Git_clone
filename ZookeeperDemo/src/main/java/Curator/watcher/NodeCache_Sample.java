package Curator.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import utils.Constants;

/**
 * Create by WangLei 2018/6/7 9:11
 * description: 使用 Cache 进行监听，Cache 是 Curator 中对时间监听的包装。
 */
public class NodeCache_Sample {
    static String path = "/zk-book/nodecache";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZOOKEEPER_ADRESS)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path,"init".getBytes());
        final NodeCache cache = new NodeCache(client, path, false);
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                System.out.println("Node data update, new data: " + new String(cache.getCurrentData().getData()));
            }
        });

        client.setData().forPath(path,"u".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);

    }
}
