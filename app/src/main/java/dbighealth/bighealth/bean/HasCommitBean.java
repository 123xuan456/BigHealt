package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/10/10.
 * 已经提交过的报告
 */
public class HasCommitBean {


    /**
     * userId : 26
     * urls : [{"url":"http://192.168.0.120:8081/JianKangChanYe/http://106.2.219.210:1314/upload/cache/8a7f070d-9db6-425a-8796-04748b679a51.jpg"},{"url":"http://192.168.0.120:8081/JianKangChanYe/http://106.2.219.210:1314/upload/cache/fa8d2c1c-9aba-438f-9757-5d92cfb92cb5.jpg"},{"url":"http://192.168.0.120:8081/JianKangChanYe/http://106.2.219.210:1314/upload/cache/8acdbb53-f184-41b6-8a1f-80c908f1d3cf.jpg"}]
     * savedate : 2016-10-10
     * code : 200
     */

    private int userId;
    private String savedate;
    private int code;
    /**
     * url : http://192.168.0.120:8081/JianKangChanYe/http://106.2.219.210:1314/upload/cache/8a7f070d-9db6-425a-8796-04748b679a51.jpg
     */

    private List<UrlsBean> urls;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSavedate() {
        return savedate;
    }

    public void setSavedate(String savedate) {
        this.savedate = savedate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<UrlsBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlsBean> urls) {
        this.urls = urls;
    }

    public static class UrlsBean {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
