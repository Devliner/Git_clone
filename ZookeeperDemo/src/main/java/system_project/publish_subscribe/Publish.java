package system_project.publish_subscribe;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import utils.Constants;
import utils.PropertiesUtil;

import java.io.File;

/**
 * Create by WangLei 2018/6/9 16:46
 * description:
 */
public class Publish {
    static String path = "/JDBC";
    static String JDBC_PATH = "D:\\Code_Summary\\Git_clone\\ZookeeperDemo\\src\\main\\resources\\JDBC.txt";
    static long per_version = 0;


    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(Constants.ZOOKEEPER_ADRESS)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {

        String s = null;
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path,"null".getBytes());


/*        Thread.sleep(10000);
        for(int i=0;i<20;i++){
            String s = "第" + i + "次更改数据";
            client.setData().forPath(path,s.getBytes());
            Thread.sleep(2000);
        }*/

        File file = new File(JDBC_PATH);

        while(true){
            long current_version = file.lastModified();

            if(current_version>per_version){
                per_version = current_version;
                 s = PropertiesUtil.getProperties(JDBC_PATH);
                client.setData().forPath(path,s.getBytes());
            }
        }



        //Thread.sleep(Integer.MAX_VALUE);


    }
}
