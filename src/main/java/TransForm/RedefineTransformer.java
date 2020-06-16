package TransForm;

import util.ReadByte;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:41 2020/6/7
 * @Modified By:
 */
public class RedefineTransformer implements ClassFileTransformer {
    private String RedefineClassPath;   // 重新加载的class的位置
    private String classPath;           // 原来项目中的class的相对路径

    public RedefineTransformer(String RedefineClassPath, String classPath) {
        this.RedefineClassPath = RedefineClassPath;
        this.classPath = classPath;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("className " + className);
        String newClassName = className.replace("/", ".");
        System.out.println("RedefineClassPath " + RedefineClassPath);
        // 如果className和重新修改编译的类路径相等了，则说明可以执行替换操作
        if (newClassName.equals(classPath)) {
            return ReadByte.getBytesFromFile(RedefineClassPath);
        }
        return null;
    }
}
