package dbighealth.bighealth.bean;

/**
 * Created by de on 2016/10/17.
 * 地区管理
 */
public class MyRegion {
    private String name ;
    private String id;
    private String parent_id;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getParent_id() {
        return parent_id;
    }
    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

}
