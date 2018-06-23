package Curator.basic;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import utils.Constants;

/**
 * Create by WangLei 2018/6/6 16:38
 * description:使用 Curator 来创建一个 Zookeeper 客户端
 */
public class Create_Session_Sample {
    public static void main(String[] args) throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.ZOOKEEPER_ADRESS, 5000, 3000, retryPolicy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
