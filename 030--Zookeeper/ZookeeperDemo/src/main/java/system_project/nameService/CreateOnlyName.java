package system_project.nameService;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import utils.Constants;

/**
 * Create by WangLei 2018/6/9 20:15
 * description: 实现类似于 UUID 唯一性的功能
 */
public class CreateOnlyName {
    static String parentPath = "/jobs";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZOOKEEPER_ADRESS)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();

        while(true){
            String s = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(parentPath + "/job-");
            System.out.println(s);
            Thread.sleep(1000);
        }
    }
}
