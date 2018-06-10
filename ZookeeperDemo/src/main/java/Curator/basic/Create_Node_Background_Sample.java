package Curator.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import utils.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by WangLei 2018/6/6 20:55
 * description: 使用 Curator 的异步接口
 */
public class Create_Node_Background_Sample {
    static String path = "/zk-book";

    static CountDownLatch semaphore = new CountDownLatch(2);
    static ExecutorService tp = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(Constants.ZOOKEEPER_ADRESS)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();

        // 此处没有传入自定义的Executor
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("event[code: " + event.getResultCode() + ", type: " + event.getType() + "]");
                System.out.println("Thread of processResult: " + Thread.currentThread().getName());
                semaphore.countDown();
                System.out.println("No");
            }
        }).forPath(path, "init".getBytes());

        // 此处传入自定义的Executor
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println("event[code: " + event.getResultCode() + ", type：" + event.getType() + "]");
                        System.out.println("Thread of processResult: " + Thread.currentThread().getName());
                        semaphore.countDown();
                        System.out.println("Yes");
                    }
                },tp)
                .forPath(path,"init".getBytes());



        semaphore.await();
        System.out.println("CountDownCount countDown");
        tp.shutdown();

    }
}
