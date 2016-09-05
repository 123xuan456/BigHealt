package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/5.
 * 健康常识
 */
public class CommonHealth {

    /**
     * ImgUrl : http://192.168.0.38:8080/JianKangChanYe/upload/20160901155737.jpg
     * code : 200
     * hint : 获取成功
     * ItemList : [{"id":10,"title":"数据10","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-03"},{"id":1,"title":"数据1","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":2,"title":"数据2","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":3,"title":"数据3","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":4,"title":"数据4","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":5,"title":"数据5","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":6,"title":"数据6","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":7,"title":"数据7","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":8,"title":"数据8","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"},{"id":9,"title":"数据9","itemUrl":"http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg","desciption":"内容1","kid":1,"time":"2016-09-02"}]
     */

    private String ImgUrl;
    private String code;
    private String hint;
    /**
     * id : 10
     * title : 数据10
     * itemUrl : http://192.168.0.38:8080/JianKangChanYe/upload/20160902091848.jpg
     * desciption : 内容1
     * kid : 1
     * time : 2016-09-03
     */

    private List<ItemListBean> ItemList;

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<ItemListBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ItemListBean> ItemList) {
        this.ItemList = ItemList;
    }

    public static class ItemListBean {
        private int id;
        private String title;
        private String itemUrl;
        private String desciption;
        private int kid;
        private String time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getItemUrl() {
            return itemUrl;
        }

        public void setItemUrl(String itemUrl) {
            this.itemUrl = itemUrl;
        }

        public String getDesciption() {
            return desciption;
        }

        public void setDesciption(String desciption) {
            this.desciption = desciption;
        }

        public int getKid() {
            return kid;
        }

        public void setKid(int kid) {
            this.kid = kid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
