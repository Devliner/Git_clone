package Curator.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import utils.Constants;

/**
 * Create by WangLei 2018/6/7 9:28
 * description:
 */
public class PathChildrenCache_Sample {
    static String path = "/zk-book";
    static int count = 0;
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZOOKEEPER_ADRESS)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();

        while(null != client.checkExists().forPath(path)) {
            System.out.println("Path exists");
            client.delete().deletingChildrenIfNeeded().forPath(path);
            Thread.sleep(3000);
            //System.exit(1);

        }



        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()){
                    case CHILD_ADDED:System.out.println("CHILD_ADDED," + event.getData().getPath()); break;
                    case CHILD_UPDATED:System.out.println("CHILD_UPDATED," + event.getData().getPath()); break;
                    case CHILD_REMOVED:System.out.println("CHILD_REMOED," + event.getData().getPath()); break;
                    default:break;
                }
            }
        });

        while(true){
            //client.create().withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());

            Thread.sleep(1000);

            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path+"/c1");
            Thread.sleep(1000);
            client.delete().forPath(path+"/c1");
            Thread.sleep(1000);
            client.delete().forPath(path);
            Thread.sleep(1000);

            System.out.println("第 " + count++ + " 次");
        }


    }
}
