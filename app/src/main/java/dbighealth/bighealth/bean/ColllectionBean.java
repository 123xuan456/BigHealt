package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/10/12.
 * 我的收藏
 */
public class ColllectionBean {

    /**
     * message : [{"userId":26,"articleId":75,"images":"http://192.168.0.120:8081/JianKangChanYe/upload/1458184145726.jpg","title":"眼病","count":1,"time":"2016-10-14"},{"userId":26,"articleId":74,"images":"http://192.168.0.120:8081/JianKangChanYe/upload/84729833-d02c-4ab9-abec-631fe570b193.jpg","title":"眼病","count":1,"time":"2016-10-12"},{"userId":26,"articleId":73,"images":"http://192.168.0.120:8081/JianKangChanYe/upload/4487fcd8f679132b3ca70a.jpg","title":"眼病","count":3,"time":"2016-10-11"}]
     * code : 200
     */

    private int code;
    /**
     * userId : 26
     * articleId : 75
     * images : http://192.168.0.120:8081/JianKangChanYe/upload/1458184145726.jpg
     * title : 眼病
     * count : 1
     * time : 2016-10-14
     */

    private List<MessageBean> message;

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
        private int userId;
        private int articleId;
        private String images;
        private String title;
        private int count;
        private String time;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
