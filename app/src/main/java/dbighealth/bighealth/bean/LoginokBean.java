package dbighealth.bighealth.bean;

/**
 * Created by de on 2016/9/6.
 */
public class LoginokBean {


    /**
     * code : 200
     * id : 26
     * username : mhysa
     * imgurl : 3
     * hint : 登陆成功
     */

    private int code;
    private int id;
    private String username;
    private String imgurl;
    private String hint;
    private String sex;
    private String age;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getAge() {
        return age;
    }
}
