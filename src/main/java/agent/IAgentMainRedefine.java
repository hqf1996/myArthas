package agent;

import TransForm.RedefineTransformer;
import org.springframework.asm.ClassReader;
import util.ReadByte;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 23:30 2020/6/7
 * @Modified By:
 */
public class IAgentMainRedefine {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException, ClassNotFoundException {
        String path = agentArgs;
        String classPath = ParseClassPathForPath(path);
        System.out.println("path " + path);
        System.out.println("classPath " + classPath);
        inst.addTransformer(new RedefineTransformer(path, classPath), true);
        /**
         * 这段代码的意思是，重新转换目标类，也就是 Main 类。也就是说，你需要重新定义哪个类，需要指定，否则 JVM 不可能知道。
         * 还有一个类似的方法 redefineClasses ，注意，这个方法是在类加载前使用的。类加载后需要使用 retransformClasses 方法
         */
        inst.retransformClasses(Class.forName(classPath));
    }

    public static String ParseClassPathForPath(String path) {
        byte[] bytesFromFile = ReadByte.getBytesFromFile(path);
        ClassReader cr = new ClassReader(bytesFromFile);
        String classPath = cr.getClassName().replace("/", ".");
        System.out.println("classPath " + classPath);
        return classPath;
    }
}
