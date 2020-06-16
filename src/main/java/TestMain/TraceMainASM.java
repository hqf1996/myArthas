package TestMain;

import com.sun.corba.se.impl.oa.toa.TOA;
import util.TimeUtil;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:17 2020/6/12
 * @Modified By:
 */
public class TraceMainASM {

    public static void main(String[] args) throws InterruptedException {
        TraceMainASM demo = new TraceMainASM();
        while (true) {
            int ret = demo.sayHello("Hello");
            Thread.sleep(1000);
            System.out.println(ret);
            System.out.println("-----------------");
        }
    }

    public int sayHello(String str) throws InterruptedException {
        long TotalStart = System.nanoTime();

        TimeUtil.setStartTime("sayHello", System.nanoTime());
        Thread.sleep(1000);
        TimeUtil.setEndTime("sayHello", System.nanoTime());
        System.out.println(TimeUtil.costTimeMethod("sayHello"));

        TimeUtil.setStartTime("sayHello", System.nanoTime());
        int a = str.length();
        TimeUtil.setEndTime("sayHello", System.nanoTime());
        System.out.println(TimeUtil.costTimeMethod("sayHello"));

        TimeUtil.setStartTime("sayHello", System.nanoTime());
        a = IncNumber(a);
        TimeUtil.setEndTime("sayHello", System.nanoTime());
        System.out.println(TimeUtil.costTimeMethod("sayHello"));

        TimeUtil.setStartTime("sayHello", TotalStart);
        TimeUtil.setEndTime("sayHello", System.nanoTime());
        System.out.println(TimeUtil.costTimeMethod("sayHello"));
        return a;
    }

    public int IncNumber(int i) {
        i = i+1;
        return i;
    }
}
