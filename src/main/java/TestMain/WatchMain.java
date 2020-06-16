package TestMain;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:17 2020/6/12
 * @Modified By:
 */
public class WatchMain {
    public static void main(String[] args) throws InterruptedException {
        WatchMain demo = new WatchMain();
        while (true) {
            int ret = demo.sayHello("aaa", 1, 1000, 6.66, new WatchMain());
//            int ret = demo.sayHello(10086);
            Thread.sleep(1000);
            System.out.println(ret);
            System.out.println("-----------------");
        }
    }

    //desc (Ljava/lang/String;   I   J   D   LTestMain/WatchMain;)   I
    public int sayHello(String str, int a, long b, double c, WatchMain watchMain) throws InterruptedException {
//        System.out.println(str);
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        System.out.println(watchMain);
        Thread.sleep(1000);
        int dd = a + 2;
        return dd;
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
