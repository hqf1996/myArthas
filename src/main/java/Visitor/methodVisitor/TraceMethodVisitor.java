package Visitor.methodVisitor;

import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import static org.springframework.asm.Opcodes.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 23:01 2020/6/11
 * @Modified By:
 */
public class TraceMethodVisitor extends MethodVisitor {
    private String method;

    public TraceMethodVisitor(MethodVisitor mv, String method) {
        super(Opcodes.ASM5, mv);
        this.method = method;
    }

    @Override
    public void visitCode() {
        //方法体内开始时调用
        // 先记录下最先开始的时间，到时候要用于计算总时间
        mv.visitLdcInsn(method);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        mv.visitMethodInsn(INVOKESTATIC, "util/TimeUtil", "setStartTime", "(Ljava/lang/String;Ljava/lang/Long;)V", false);
        super.visitCode();
    }
    @Override
    public void visitInsn(int opcode) {
        // 每执行一个指令都会调用 return的时候就是整个方法终止的时候，注意这里当opcode在这个范围内的时候说明方法结束返回
        // 那么就可以执行终止总时间的打印操作
        /***
         *     int IRETURN = 172;
         *     int LRETURN = 173;
         *     int FRETURN = 174;
         *     int DRETURN = 175;
         *     int ARETURN = 176;
         *     int RETURN = 177;
         */
        if ((opcode >= Opcodes.IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            // 给定结束时间
            mv.visitLdcInsn(method);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
            mv.visitMethodInsn(INVOKESTATIC, "util/TimeUtil", "setEndTime", "(Ljava/lang/String;Ljava/lang/Long;)V", false);

            // 输出方法总耗时
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(method + " all time consume:");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            // 计算方法耗时
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(method);
            mv.visitMethodInsn(INVOKESTATIC, "util/TimeUtil", "costTimeMethod", "(Ljava/lang/String;)Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        // 输出当前的方法名
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(owner.replace("/", ".") + ":" + name);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // 给定开始时间
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        mv.visitMethodInsn(INVOKESTATIC, "util/TimeUtil", "setStartTime", "(Ljava/lang/String;Ljava/lang/Long;)V", false);

        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

        // 给定结束时间
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        mv.visitMethodInsn(INVOKESTATIC, "util/TimeUtil", "setEndTime", "(Ljava/lang/String;Ljava/lang/Long;)V", false);

        // 计算方法耗时
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitMethodInsn(INVOKESTATIC, "util/TimeUtil", "costTimeMethod", "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }
}
