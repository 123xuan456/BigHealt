package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/10/14.
 */
public class AffirmIndentBean {
    /**
     * name : 嘿嘿
     * phoneNumber : 18533355123
     * address : 北京市北京市北京asdas市
     * code : 200
     * message : [{"shoppingId":1,"userId":26,"num":5,"title":"西湖龙井 ","images":"http://192.168.0.120:8081/JianKangChanYe/upload/product/2-140506152TS34.jpg","content":"茉莉花茶（Jasmine Tea），又叫茉莉香片，属于花茶，已有1000多年历史。世界茉莉花茶发源地为福建福州 ，其茶香与茉莉花香交互融合，有\u201c窨得茉莉无上味，列作人间第一香\u201d的美誉。","price":5000},{"shoppingId":3,"userId":26,"num":1,"title":"白毫银针 ","images":"http://192.168.0.120:8081/JianKangChanYe/upload/product/6-150205152G8.jpg","content":"雪花秀(sulwhasoo)韩国著名草本护肤化妆品品牌,中国唯一官方网站,致力于中国女性传播美丽与自信,提供升级滋晶雪肤美白,滋阴肌本,水律提拉,臻秀抗皱,敏感修复护理系列等...","price":5000}]
     */

    private String name;
    private String phoneNumber;
    private String address;
    private int code;
    /**
     * shoppingId : 1
     * userId : 26
     * num : 5
     * title : 西湖龙井
     * images : http://192.168.0.120:8081/JianKangChanYe/upload/product/2-140506152TS34.jpg
     * content : 茉莉花茶（Jasmine Tea），又叫茉莉香片，属于花茶，已有1000多年历史。世界茉莉花茶发源地为福建福州 ，其茶香与茉莉花香交互融合，有“窨得茉莉无上味，列作人间第一香”的美誉。
     * price : 5000
     */

    private List<MessageBean> message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        private int shoppingId;
        private int userId;
        private int num;
        private String title;
        private String images;
        private String content;
        private int price;

        public int getShoppingId() {
            return shoppingId;
        }

        public void setShoppingId(int shoppingId) {
            this.shoppingId = shoppingId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
   /* private String name;

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
    }*/

}
