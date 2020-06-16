package TransForm;

import Visitor.classVisitor.TraceClassVisitor;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.Opcodes;
import util.ReadByte;

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
public class TraceTransformer implements ClassFileTransformer {
    private String classPath;        // class的位置
    private String method;           // 用于Trace的方法名

    public TraceTransformer(String classPath, String method) {
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
            // 表示 ASM 会自动帮你计算局部变量表和操作数栈的大小，但是你还是需要调用visitMaxs方法，
            // 但是可以使用任意参数，因为它们会被忽略。带有这个标识，对于栈帧大小，还是需要你手动计算。
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new TraceClassVisitor(cw, method);
            cr.accept(cv, Opcodes.ASM5);
            // 获取生成的class文件对应的二进制流
            byte[] code = cw.toByteArray();
            return code;
        }
        return null;
    }
}
