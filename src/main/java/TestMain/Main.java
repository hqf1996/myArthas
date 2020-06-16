package TestMain;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:55 2020/6/7
 * @Modified By:
 */
public class Main {
    public static void main( String[] args ) throws InterruptedException {
        Main demo = new Main();
        while (true) {
            demo.sayHello();
            Thread.sleep(1000);
        }
    }

    public void sayHello() {
        System.out.println("Hello World!");
    }
}
