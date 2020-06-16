package util;

import org.springframework.asm.Opcodes;
import org.springframework.asm.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 0:12 2020/6/14
 * @Modified By: 将每一个输入的参数进行dataType的解析和封装
 */
public class dataTypeParse {
    private static String[] descList = {"Z", "C", "B", "S", "I", "F", "J", "D", "Ljava/lang/Object;", "Ljava/lang/String;"};
    private static String[] realType = {"boolean", "char", "byte", "short", "int", "float", "long", "double", "Object" ,"String"};
    private static Map<String, String> descTypeMap = new HashMap<>();

    static {
        for (int i = 0 ; i < descList.length ; ++i) {
            descTypeMap.put(descList[i], realType[i]);
        }
    }

    /**
     * 从输入的类型描述符中解析出对应的java类型，并封装进opcode、slotSize、ASMdesc和realType
     * @param descAllParam
     * @return
     */
    public static List<dataType> getInputParam(String descAllParam) {
        List<dataType> dataTypes = new ArrayList<>();
        Type[] argumentTypes = Type.getArgumentTypes(descAllParam);
        for (Type type : argumentTypes) {
            String desc = type.getDescriptor();
            String realType = getRealType(desc);
            String ASMdesc = getASMdesc(desc);
            int opcode = getOpcode(realType);
            int slot = getSlot(realType);
            dataType oneDataType = new dataType(desc, realType, ASMdesc, opcode, slot);
            dataTypes.add(oneDataType);
        }
        return dataTypes;
    }

    /**
     * 从输出的类型描述符中解析出对应的java类型，并封装相应的参数。
     * @param descAllParam
     * @return
     */
    public static dataType getOutputParam(String descAllParam) {
        Type returnType = Type.getReturnType(descAllParam);
        String desc = returnType.getDescriptor();
        String RetRealType = getRealType(desc);
        String RetASMdesc = getASMdesc(desc);
        int Retslot = getSlot(RetRealType);
        int Retopcode = getOpcode(RetRealType);
        dataType oneDataType = new dataType(desc, RetRealType, RetASMdesc, Retopcode, Retslot);
        return oneDataType;
    }

    public static String getRealType(String desc) {
        if (descTypeMap.containsKey(desc)) {
            return descTypeMap.get(desc);
        } else {
            return desc;
        }
    }

    public static String getASMdesc(String desc) {
        if (descTypeMap.containsKey(desc)) {
            return desc;
        } else {
            return "Ljava/lang/Object;";
        }
    }

    /**
     * 返回每一个类型的Opcode
     * @param realType
     * @return
     */
    public static int getOpcode(String realType) {
        if (realType.equals("boolean") || realType.equals("byte") || realType.equals("char") || realType.equals("short")
        || realType.equals("int")) {
            return Opcodes.ILOAD;
        } else if (realType.equals("long")) {
            return Opcodes.LLOAD;
        } else if (realType.equals("float")) {
            return Opcodes.FLOAD;
        } else if (realType.equals("double")) {
            return Opcodes.DLOAD;
        } else {
            // 包括了String类型和其他的非基元类型
            return Opcodes.ALOAD;
        }
    }

    /**
     * 返回槽的数目，这边容易踩坑。注意，long类型和double类型占两个槽的位置，其他类型占一个槽的位置。
     * @param realType
     * @return
     */
    public static int getSlot(String realType) {
        if (realType.equals("long") || realType.equals("double")) {
            return 2;
        } else {
            return 1;
        }
    }
}
