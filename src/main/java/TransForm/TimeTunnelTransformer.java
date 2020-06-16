package TransForm;

import Visitor.classVisitor.TimeTunnelClassVisitor;
import Visitor.classVisitor.WatchClassVisitor;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.Opcodes;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:41 2020/6/7
 * @Modified By:
 */
public class TimeTunnelTransformer implements ClassFileTransformer {
    private String classPath;        // class的位置
    private String method;           // 用于Trace的方法名

    public TimeTunnelTransformer(String classPath, String method) {
        this.classPath = classPath;
        this.method = method;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String newClassName = className.replace("/", ".");
        if (newClassName.equals(classPath)) {
            ClassReader cr = null;
            try {
                cr = new ClassReader(classPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new TimeTunnelClassVisitor(cw, method, classPath);
            cr.accept(cv, Opcodes.ASM5);
            // 获取生成的class文件对应的二进制流
            byte[] code = cw.toByteArray();
            return code;
        }
        return null;
    }
}
