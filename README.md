# myArthas
该项目是模仿阿里Arthas写的线上应用诊断小工具。基于Java探针-Java Agent技术对字节码进行增强，在指定方法中插入一些切面来实现相关参数的统计与观测，能做到JVM层面的AOP。对字节码的增强主要使用ASM字节码框架与ASM Bytecode Viewer插件，另外使用Java Attach机制实现两个JVM进程之间的正常通信。简单实现了热更新（Redefine）、方法内部链路追踪（Trace）、方法执行数据观测（Watch）和数据的时空隧道（TimeTunnel）等功能。

----



## Redefine

对正在运行的程序实现热更新，不需要重启服务即可完成相关代码的替换。

>```java
>String vmPid = "21268";
>String agentJarPath = "D:\\JavaProject\\myArthas\\target\\myArthas-1.0-SNAPSHOT-shaded.jar";
>VirtualMachine virtualMachine = VirtualMachine.attach(vmPid);
>virtualMachine.loadAgent(agentJarPath, "D:\\JavaProject\\myArthas\\redefineClass\\Main.class");
>virtualMachine.detach();
>```
>
>实现效果
>
>![](https://hqf1996-1258281483.cos.ap-shanghai.myqcloud.com/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF/1.png)

## Trace

追踪正在执行的函数的链路调用情况，并且统计该函数下相关子函数的耗时。最后统计整个函数消耗时间。

>```java
>String vmPid = "1620";
>String agentJarPath = "D:\\JavaProject\\myArthas\\target\\trace.jar";
>VirtualMachine virtualMachine = VirtualMachine.attach(vmPid);
>virtualMachine.loadAgent(agentJarPath, "TestMain.TraceMain.sayHello");
>virtualMachine.detach();
>```
>
>实现效果
>
>![](https://hqf1996-1258281483.cos.ap-shanghai.myqcloud.com/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF/2.png)

## watch

监控函数的入参出参信息，包括参数类型和参数值。

>```java
>String vmPid = "19432";
>String agentJarPath = "D:\\JavaProject\\myArthas\\target\\watch.jar";
>VirtualMachine virtualMachine = VirtualMachine.attach(vmPid);
>virtualMachine.loadAgent(agentJarPath, "TestMain.WatchMain.sayHello");
>virtualMachine.detach();
>```
>
>实现效果
>
>![](https://hqf1996-1258281483.cos.ap-shanghai.myqcloud.com/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF/3.png)

## TimeTunnel

方法执行数据的时空隧道，记录下指定方法每次调用的相关信息，并能对这些不同的时间下调用进行观测回放。

>```java
>String vmPid = "16248";
>String agentJarPath = "D:\\JavaProject\\myArthas\\target\\timetunnel.jar";
>VirtualMachine virtualMachine = VirtualMachine.attach(vmPid);
>virtualMachine.loadAgent(agentJarPath, "TestMain.TimeTunnelMain.sayHello");
>virtualMachine.detach();
>```
>
>实现效果
>
>![](https://hqf1996-1258281483.cos.ap-shanghai.myqcloud.com/%E9%98%BF%E5%B0%94%E8%90%A8%E6%96%AF/4.png)
>
>反序列化效果
>
>```java
>public static void decodesayHello() throws IOException, ClassNotFoundException {
>    ObjectInputStream in  = new ObjectInputStream(new FileInputStream(new File("D:\\JavaProject\\myArthas\\TestMain.TimeTunnelMain$sayHello$1592293146017")));
>    String[] a = (String[]) in.readObject();
>    for (int i = 0 ; i < a.length ; ++i) {
>        System.out.println(a[i]);
>    }
>    in.close();
>}
>```
>
>```
>"aaa"
>1
>1000
>6.66
>{"id":321,"name":"Tom"}
>
>Process finished with exit code 0
>```