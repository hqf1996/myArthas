package TestMain;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:17 2020/6/12
 * @Modified By:
 */
public class TraceMain {
    public static void main(String[] args) throws InterruptedException {
        TraceMain demo = new TraceMain();
        while (true) {
            int ret = demo.sayHello("Hello");
            Thread.sleep(1000);
            System.out.println(ret);
            System.out.println("-----------------");
        }
    }

    public int sayHello(String str) throws InterruptedException {
        Thread.sleep(1000);
        int a = str.length();
        a = IncNumber(a);
        return a;
    }

    public int IncNumber(int i) {
        i = i+1;
        return i;
    }
}
