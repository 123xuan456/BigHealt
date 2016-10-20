package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/10/14.
 */
public class AffirmIndentBean {
    private String name;

    private String phoneNumber;

    private String address;

    private int code;

    private List<Message> message ;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(List<Message> message){
        this.message = message;
    }
    public List<Message> getMessage(){
        return this.message;
    }

    public class Message {
        private int num;

        private String title;

        private String images;

        private String content;

        private String price;

        private int shoppingId;

        public int getShoppingId() {
            return shoppingId;
        }
        public void setShoppingId(int shoppingId) {
            this.shoppingId = shoppingId;
        }
        public void setNum(int num){
            this.num = num;
        }
        public int getNum(){
            return this.num;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setImages(String images){
            this.images = images;
        }
        public String getImages(){
            return this.images;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setPrice(String price){
            this.price = price;
        }
        public String getPrice(){
            return this.price;
        }
    }
}
