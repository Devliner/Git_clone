package testDemo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by WangLei 2018/6/9 17:12
 * description:
 */
public class FileDemo {

    public static void main(String[] args) throws InterruptedException {
        File file = new File("D:\\Code_Summary\\Git_clone\\ZookeeperDemo\\src\\main\\resources\\JDBC.txt");




        while(true){

            if(file.exists()) {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateTime= df.format(new Date(file.lastModified()));

                System.out.println(dateTime);

            }

            long l = file.lastModified();
            System.out.println("file version: " + l);
            Thread.sleep(500);
        }




    }
}
