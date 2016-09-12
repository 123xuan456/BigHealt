package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/9/9.
 */
public class ConditionBean {

    /**
     * code : 200
     * message : [{"userId":1,"breakfastsize":"999","breakfasttime":"11:15-11:16","lunchsize":"e","lunchtime":"999","addsize":"w","addtime":"999","dinnersize":"99","dinnertime":"9","savedate":"2016-09-08"}]
     */

    private int code;
    /**
     * userId : 1
     * breakfastsize : 999
     * breakfasttime : 11:15-11:16
     * lunchsize : e
     * lunchtime : 999
     * addsize : w
     * addtime : 999
     * dinnersize : 99
     * dinnertime : 9
     * savedate : 2016-09-08
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
        private String breakfastsize;
        private String breakfasttime;
        private String lunchsize;
        private String lunchtime;
        private String addsize;
        private String addtime;
        private String dinnersize;
        private String dinnertime;
        private String savedate;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getBreakfastsize() {
            return breakfastsize;
        }

        public void setBreakfastsize(String breakfastsize) {
            this.breakfastsize = breakfastsize;
        }

        public String getBreakfasttime() {
            return breakfasttime;
        }

        public void setBreakfasttime(String breakfasttime) {
            this.breakfasttime = breakfasttime;
        }

        public String getLunchsize() {
            return lunchsize;
        }

        public void setLunchsize(String lunchsize) {
            this.lunchsize = lunchsize;
        }

        public String getLunchtime() {
            return lunchtime;
        }

        public void setLunchtime(String lunchtime) {
            this.lunchtime = lunchtime;
        }

        public String getAddsize() {
            return addsize;
        }

        public void setAddsize(String addsize) {
            this.addsize = addsize;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDinnersize() {
            return dinnersize;
        }

        public void setDinnersize(String dinnersize) {
            this.dinnersize = dinnersize;
        }

        public String getDinnertime() {
            return dinnertime;
        }

        public void setDinnertime(String dinnertime) {
            this.dinnertime = dinnertime;
        }

        public String getSavedate() {
            return savedate;
        }

        public void setSavedate(String savedate) {
            this.savedate = savedate;
        }
    }
}
