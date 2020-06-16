package Visitor.classVisitor;

import Visitor.methodVisitor.TraceMethodVisitor;
import Visitor.methodVisitor.WatchMethodVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.Type;
import util.dataType;
import util.dataTypeParse;

import java.util.List;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 15:48 2020/6/8
 * @Modified By:
 */
public class WatchClassVisitor extends ClassVisitor {
    private String method;

    public WatchClassVisitor(final ClassVisitor cv, String method) {
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
            List<dataType> inputParam = dataTypeParse.getInputParam(desc);
            dataType outputParam = dataTypeParse.getOutputParam(desc);
            return new WatchMethodVisitor(mv, method, inputParam, outputParam);
        }
        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return null;
    }
}
