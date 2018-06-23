package Curator.basic;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import utils.Constants;

/**
 * Create by WangLei 2018/6/6 20:15
 * description: 使用 Fluent 风格的 API 接口来创建一个 Zookeeper 客户端
 */
public class Create_Session_Sample_fluent {

    public static void main(String[] args) throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Constants.ZOOKEEPER_ADRESS)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
