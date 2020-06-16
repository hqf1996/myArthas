package Visitor.methodVisitor;

import javassist.bytecode.Opcode;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import util.dataType;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.asm.Opcodes.*;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 23:01 2020/6/11
 * @Modified By:
 */
public class TimeTunnelMethodVisitor extends MethodVisitor {
    private String method;
    private String classPath;
    private List<dataType> inputParam;
    private int curSlot;
    private static String[] basicDataType = {"boolean", "char", "byte", "short", "int", "float", "long", "double"};
    private static String[] BoxingDataType = {"java/lang/Boolean", "java/lang/Character", "java/lang/Byte",
            "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", ""};
    private static Map<String, String> basicBoxingMap = new HashMap<>();
    static {
        for (int i = 0 ; i < basicDataType.length ; ++i) {
            basicBoxingMap.put(basicDataType[i], BoxingDataType[i]);
        }
    }

    public TimeTunnelMethodVisitor(MethodVisitor mv, String method, String classPath, List<dataType> inputParam) {
        super(Opcodes.ASM5, mv);
        this.method = method;
        this.classPath = classPath;
        this.inputParam = inputParam;
    }

    @Override
    public void visitCode() {
        //方法体内开始时调用
        // 设置className的槽的位置
        int classNameIndex = getCurrentIndex();   //className的槽的位置  8
        mv.visitLdcInsn(classPath);
        mv.visitVarInsn(ASTORE, classNameIndex);
        // 设置methodName槽的位置
        int methodIndex = classNameIndex+1;     // methodName槽的位置  9
        mv.visitLdcInsn(method);
        mv.visitVarInsn(ASTORE, methodIndex);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("当前" + classPath + "$" + method + "正在进行TimeTunnel...");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // 设置序列化文件的位置pathIndex  10
        int pathIndex = methodIndex+1;
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitVarInsn(ALOAD, classNameIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn("$");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, methodIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn("$");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitVarInsn(ASTORE, pathIndex);

        /** 开始序列化  **/
        // ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(path),true));
        int outIndex = pathIndex+1;  // 11
        mv.visitTypeInsn(NEW, "java/io/ObjectOutputStream");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "java/io/FileOutputStream");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "java/io/File");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, pathIndex);
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/File", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ICONST_1);
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/FileOutputStream", "<init>", "(Ljava/io/File;Z)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/ObjectOutputStream", "<init>", "(Ljava/io/OutputStream;)V", false);
        mv.visitVarInsn(ASTORE, outIndex);

        /**创建数组用来存放序列化的参数，注意数组的大小是按照参数的数量来的*/
        // 当int取值-128~127时，JVM采用bipush指令将常量压入栈中。
        // 当int取值-1~5时，JVM采用iconst指令将常量压入栈中。
        // 当int取值-32768~32767时，JVM采用sipush指令将常量压入栈中。
        // 当int取值-2147483648~2147483647时，JVM采用ldc指令将常量压入栈中。
        int arrIndex = outIndex+1;  //12
        mv.visitIntInsn(Opcode.BIPUSH, inputParam.size());
        mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
        mv.visitVarInsn(ASTORE, arrIndex);

        /**装载Json数组*/  //args[0] = JSON.toJSONString(str);
        int index = 1;
        for (int i = 0 ; i < inputParam.size() ; ++i) {
            mv.visitVarInsn(ALOAD, arrIndex);
            mv.visitIntInsn(Opcode.BIPUSH, i);
            mv.visitVarInsn(inputParam.get(i).getOpcode(), index);
            index+=inputParam.get(i).getSlotSize();
            // 如果是8种（"boolean", "char", "byte", "short", "int", "float", "long", "double"）基本数据类型，
            // 需要先在内部转换为String类型
            if (basicBoxingMap.containsKey(inputParam.get(i).getRealType())) {
                String boxingStr = basicBoxingMap.get(inputParam.get(i).getRealType());
                mv.visitMethodInsn(INVOKESTATIC, boxingStr,
                        "valueOf", "("+inputParam.get(i).getDesc()+")L"+boxingStr+";", false);
            }
            mv.visitMethodInsn(INVOKESTATIC, "com/alibaba/fastjson/JSON", "toJSONString", "(Ljava/lang/Object;)Ljava/lang/String;", false);
            mv.visitInsn(AASTORE);
        }
        /**写出数组到out中*/ // out.writeObject(args);
        mv.visitVarInsn(ALOAD, outIndex);
        mv.visitVarInsn(ALOAD, arrIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/ObjectOutputStream", "writeObject", "(Ljava/lang/Object;)V", false);
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

        }
        super.visitInsn(opcode);
    }

    /**
     * 获得当前
     * @return
     */
    public int getCurrentIndex() {
        int curIndex = 1;
        for (int i = 0 ; i < inputParam.size() ; ++i) {
            curIndex+=inputParam.get(i).getSlotSize();
        }
        return curIndex;
    }
}
