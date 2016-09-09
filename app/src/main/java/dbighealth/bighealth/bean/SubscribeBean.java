package dbighealth.bighealth.bean;

/**
 * Created by de on 2016/9/8.
 */
public class SubscribeBean {

    private String imageurl;

    private String title;

    private String content;

    private String datetime;

    private String information;

    private String telephone;

    private int code;

    private String hint;

    public void setImageurl(String imageurl){
        this.imageurl = imageurl;
    }
    public String getImageurl(){
        return this.imageurl;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setDatetime(String datetime){
        this.datetime = datetime;
    }
    public String getDatetime(){
        return this.datetime;
    }
    public void setInformation(String information){
        this.information = information;
    }
    public String getInformation(){
        return this.information;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public String getTelephone(){
        return this.telephone;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setHint(String hint){
        this.hint = hint;
    }
    public String getHint(){
        return this.hint;
    }
}


