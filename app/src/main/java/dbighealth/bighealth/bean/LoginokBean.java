package dbighealth.bighealth.bean;

/**
 * Created by de on 2016/9/6.
 */
public class LoginokBean {

    /**
     * code : 200
     * id : 16
     * hint : 登陆成功
     */

    private int code;
    private int id;
    private String hint;
    private String username;
    private String imgurl;
    private String sex;

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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String getImgurl() {
        return imgurl;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }
}
