package util;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 23:20 2020/6/13
 * @Modified By: 数据类型的封装
 */
public class dataType {
    private String desc;        // 类型描述符，比如基本数据类型中的I(对应int)，自己封装的类型LTestMain/WatchMain;(WatchMain类)
    private String realType;    // desc对应的Java中的类型，比如Z对应着boolean
    private String ASMdesc;     // ASM字节码中的类型的描述符，用于输入输出的数值输出使用，与desc在基本数据类型上是一致的
                                // ，但是在一些自己封装的数据类型上是不一样的，比如LTestMain/WatchMain;(WatchMain类)
                                //应该要是LJava.lang.Object才可以。
    private int opcode;         // 操作指令对应的opcode
    private int slotSize;       // 每个数据类型对应的槽的占用位置，long 和 double变量需要占用两个槽，其他是一个槽。

    public dataType(String desc, String realType, String ASMdesc, int opcode, int slotSize) {
        this.desc = desc;
        this.realType = realType;
        this.ASMdesc = ASMdesc;
        this.opcode = opcode;
        this.slotSize = slotSize;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRealType() {
        return realType;
    }

    public void setRealType(String realType) {
        this.realType = realType;
    }

    public String getASMdesc() {
        return ASMdesc;
    }

    public void setASMdesc(String ASMdesc) {
        this.ASMdesc = ASMdesc;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public int getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
    }

    @Override
    public String toString() {
        return "dataType{" +
                "desc='" + desc + '\'' +
                ", realType='" + realType + '\'' +
                ", ASMdesc='" + ASMdesc + '\'' +
                ", opcode=" + opcode +
                ", slotSize=" + slotSize +
                '}';
    }
}
