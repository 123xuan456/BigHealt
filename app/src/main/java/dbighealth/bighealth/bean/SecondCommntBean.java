package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/22.
 * 眼病等二级界面
 */
public class SecondCommntBean {

    /**
     * title : 眼病
     * content : 眼科频道为您提供最全面的眼科疾病预防、眼科疾病治疗、眼病保健等知识,详细讲解近视眼、白内障、青光眼、干眼症、红眼病等常见眼科疾病常识。
     * images : http://192.168.0.43:8080/JianKangChanYe/upload/84729833-d02c-4ab9-abec-631fe570b193.jpg
     * code : 200
     * hint : 获取成功
     * comList : [{"id":1,"articleId":74,"comment":"帅点王哲发呆","time":"2016-09-05","userid":24,"name":"哈士奇"},{"id":2,"articleId":74,"comment":"王哲发呆打发很快就收到货","time":"2016-09-13","userid":24,"name":"哈士奇"},{"id":3,"articleId":74,"comment":"的是覅而言王哲发呆","time":"2016-09-13","userid":25,"name":"hashiqi"},{"id":4,"articleId":74,"comment":"的方式撒地方王哲发呆","time":"2016-09-13","userid":24,"name":"哈士奇"},{"id":5,"articleId":74,"comment":"王哲发呆","time":"2016-09-13","userid":16,"name":"哈士奇"}]
     */

    private String title;
    private String content;
    private String images;
    private int code;
    private String hint;
    /**
     * id : 1
     * articleId : 74
     * comment : 帅点王哲发呆
     * time : 2016-09-05
     * userid : 24
     * name : 哈士奇
     */

    private List<ComListBean> comList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<ComListBean> getComList() {
        return comList;
    }

    public void setComList(List<ComListBean> comList) {
        this.comList = comList;
    }

    public static class ComListBean {
        private int id;
        private int articleId;
        private String comment;
        private String time;
        private int userid;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
