package Visitor.classVisitor;

import Visitor.methodVisitor.TraceMethodVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 15:48 2020/6/8
 * @Modified By:
 */
public class TraceClassVisitor extends ClassVisitor {
    private String method;

    public TraceClassVisitor(final ClassVisitor cv, String method) {
        super(Opcodes.ASM5, cv);
        this.method = method;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (cv != null) {
            cv.visit(version, access, name, signature, superName, interfaces);
        }
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //如果methodName是newFunc，则返回我们自定义的TestMethodVisitor
        if (method.equals(name)) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            return new TraceMethodVisitor(mv, method);
        }
        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return null;
    }
}
