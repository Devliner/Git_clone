package Curator.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import utils.Constants;

/**
 * Create by WangLei 2018/6/7 10:58
 * description: Master选举
 */
public class Recipes_MasterSelect {
    static String master_path = "/curator_recipes_master_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZOOKEEPER_ADRESS)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws InterruptedException {
        client.start();
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListener() {
            // 一旦执行完 takeLeadership 方法， Curator会自动释放 Master 权利，然后重新开始新一轮的 Master 的选举
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为Master角色");
                Thread.sleep(3000);
                System.out.println("完成Master操作，释放Master权利");
            }

            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

            }
        });


        selector.autoRequeue();
        selector.start();
        Thread.sleep( Integer.MAX_VALUE );
    }
}
