package TestMain;

import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:17 2020/6/12
 * @Modified By:
 */
public class TimeTunnelMain {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        TimeTunnelMain demo = new TimeTunnelMain();
        while (true) {
            Stu ss = new Stu(321, "Tom");
            int ret = demo.sayHello("aaa", 1, 1000, 6.66, ss);
//            int ret = demo.sayHello(10086);
            Thread.sleep(1000);
            System.out.println(ret);
            System.out.println("-----------------");
        }
//        decodesayHello();
    }

    //desc (Ljava/lang/String;   I   J   D   LTestMain/WatchMain;)   I
    public int sayHello(String str, int a, long b, double c, Stu ss) throws InterruptedException, IOException {
//        String clazz = Thread.currentThread() .getStackTrace()[1].getClassName();
//        String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
//        String clazz = "aaa";
//        String method = "bbb";
//        String path = clazz+"$"+method+"$"+System.currentTimeMillis();
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(path),true));
//        String[] args = new String[5];
//        //"boolean", "char", "byte", "short", "int", "float", "long", "double"
//        args[0] = JSON.toJSONString(str);
//        args[1] = JSON.toJSONString(a);
//        args[2] = JSON.toJSONString(b);
//        args[3] = JSON.toJSONString(c);
//        args[4] = JSON.toJSONString(ss);
//        out.writeObject(args);
        Thread.sleep(1000);
        int dd = a + 2;
        return dd;
    }

    /**
     * 用来测试反序列化
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void decodesayHello() throws IOException, ClassNotFoundException {
        ObjectInputStream in  = new ObjectInputStream(new FileInputStream(new File("D:\\JavaProject\\myArthas\\TestMain.TimeTunnelMain$sayHello$1592237894239")));
        String[] a = (String[]) in.readObject();
        for (int i = 0 ; i < a.length ; ++i) {
            System.out.println(a[i]);
        }
        in.close();
    }


//    public int sayHello(int a) throws InterruptedException {
//        Thread.sleep(1000);
//        return a;
//    }

    public int IncNumber(int i) {
        i = i+1;
        return i;
    }
}
