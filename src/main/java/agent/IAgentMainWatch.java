package agent;

import TransForm.TraceTransformer;
import TransForm.WatchTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 17:25 2020/6/12
 * @Modified By:
 */
public class IAgentMainWatch {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException, ClassNotFoundException {
        String path = agentArgs;
        String[] classPath = ParseForPathAndMethod(path);
        inst.addTransformer(new WatchTransformer(classPath[0], classPath[1]), true);
        inst.retransformClasses(Class.forName(classPath[0]));
    }

    public static String[] ParseForPathAndMethod(String path) {
        String classPath = path.substring(0, path.lastIndexOf("."));
        String method = path.substring(path.lastIndexOf(".")+1);
        return new String[]{classPath, method};
    }
}
