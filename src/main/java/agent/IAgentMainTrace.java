package agent;

import TransForm.RedefineTransformer;
import TransForm.TraceTransformer;
import org.springframework.asm.ClassReader;
import util.ReadByte;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:25 2020/6/12
 * @Modified By:
 */
public class IAgentMainTrace {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException, ClassNotFoundException {
        String path = agentArgs;
        System.out.println("path " + path);
        String[] classPath = ParseForPathAndMethod(path);
        System.out.println(classPath[0] + "  " + classPath[1]);
        inst.addTransformer(new TraceTransformer(classPath[0], classPath[1]), true);
        /**
         * 这段代码的意思是，重新转换目标类，也就是 Main 类。也就是说，你需要重新定义哪个类，需要指定，否则 JVM 不可能知道。
         * 还有一个类似的方法 redefineClasses ，注意，这个方法是在类加载前使用的。类加载后需要使用 retransformClasses 方法
         */
        inst.retransformClasses(Class.forName(classPath[0]));
    }

    public static String[] ParseForPathAndMethod(String path) {
        String classPath = path.substring(0, path.lastIndexOf("."));
        String method = path.substring(path.lastIndexOf(".")+1);
        return new String[]{classPath, method};
    }
}
