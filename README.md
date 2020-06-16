# myArthas
该项目是模仿阿里Arthas写的线上应用诊断小工具。基于Java探针-Java Agent技术对字节码进行增强，在指定方法中插入一些切面来实现相关参数的统计与观测，能做到JVM层面的AOP。对字节码的增强主要使用ASM字节码框架与ASM Bytecode Viewer插件，另外使用Java Attach机制实现两个JVM进程之间的正常通信。简单实现了热更新（Redefine）、方法内部链路追踪（Trace）、方法执行数据观测（Watch）和数据的时空隧道（TimeTunnel）等功能。
