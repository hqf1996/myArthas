package Visitor.methodVisitor;

import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import util.dataType;

import java.util.List;

import static org.springframework.asm.Opcodes.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 23:01 2020/6/11
 * @Modified By:
 */
public class WatchMethodVisitor extends MethodVisitor {
    private String method;
    private List<dataType> inputParam;
    private dataType outputParam;
    private int curSlot;
    private int lastSlot;

    public WatchMethodVisitor(MethodVisitor mv, String method, List<dataType> inputParam, dataType outputParam) {
        super(Opcodes.ASM5, mv);
        this.method = method;
        this.inputParam = inputParam;
        this.outputParam = outputParam;
    }

    @Override
    public void visitCode() {
        //方法体内开始时调用
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(method + " 入参：");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        curSlot = 1;
        for (int i = 0 ; i < inputParam.size() ; ++i) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("\t类型参数：" + inputParam.get(i).getRealType() + "@");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(inputParam.get(i).getOpcode(), curSlot);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "("+inputParam.get(i).getASMdesc()+")V", false);
            curSlot+=inputParam.get(i).getSlotSize();
        }
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
            // 出参类型
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(method + " 出参：");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("\t类型参数：" + outputParam.getRealType() + "@");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(outputParam.getOpcode(), lastSlot);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "("+outputParam.getASMdesc()+")V", false);
//            System.out.println("curSlot " + curSlot);
//            System.out.println("lastSlot " + lastSlot);
            // 在执行最后return的时候会执行LOAD操作（本例子中是ILOAD），相当于这个操作会记录到最后的return的值的槽的位置，
            // 所以我们只要把这个数保存起来，重新LOAD并打印一下就是最终返回的值。
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        System.out.println(opcode + " " + var);
        lastSlot = var;
        super.visitVarInsn(opcode, var);
    }
}
