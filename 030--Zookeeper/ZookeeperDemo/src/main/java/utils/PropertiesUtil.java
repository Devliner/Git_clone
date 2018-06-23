package utils;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * Create by WangLei 2018/6/9 14:43
 * description:
 */
public class PropertiesUtil {

    private static Logger Log = LoggerFactory.getLogger(PropertiesUtil.class);

    public static String getProperties(String path){
        StringBuilder sb = new StringBuilder();

        File file = new File(path);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String line = null;
            while(null != (line = br.readLine())){
                Log.info(sb.toString());
                sb.append("^").append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }


    public static void main(String[] args) throws InterruptedException {
        String s1 = "log4j.appender.logFile=org.apache.log4j.FileAppender";
        String s2 = "log4j.appender.logFile.Threshold=DEBUG";

        System.out.println(s1 + "\n" + s2);
        PropertiesUtil properties = new PropertiesUtil();

        URL resource = Resources.getResource("log4j.properties");

        //String path = resource.toString().replaceAll("/\\/g", "/");
        //String path = "D:\\Code_Summary\\Git_clone\\ZookeeperDemo\\target\\classes\\log4j.properties";
        String s = properties.getProperties("D:/Code_Summary/Git_clone/ZookeeperDemo/src/main/resources/log4j.properties");

        //System.out.println(resource.toString());
        String s3 = s1 + "\n" + s2;
        String[] split = s.split("\\^");
        for(int i=0; i< split.length; i++){
            System.out.println(split[i]);
        }



    }
}
