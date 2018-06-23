package system_project.publish_subscribe;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import utils.Constants;

/**
 * Create by WangLei 2018/6/9 14:41
 * description: 将 JDBC 信息放到一个节点上，然后所有的客户端向这个节点注册监听，然后修改这个节点信息，
 */
public class Subscribe {

    static String path = "/JDBC";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZOOKEEPER_ADRESS)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        final NodeCache cache = new NodeCache(client, path, false);
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                String data = new String(cache.getCurrentData().getData());
                String[] split = data.split("\\^");
                for(int i=0; i < split.length; i++){
                    System.out.println(split[i]);
                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
