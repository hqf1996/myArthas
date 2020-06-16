package TestMain;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 16:51 2020/6/15
 * @Modified By:
 */
public class Stu {
    private int id;
    private String name;

    public Stu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
