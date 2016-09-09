package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/9/9.
 */
public class EveryDayBean {


    /**
     * code : 200
     * message : {"daily":[{"dailyId":4,"dailyRead":"啊啊啊埃杜阿多额撇问你个屁额我屁股恶恶个价位颇高稳婆去外婆亲我们去【哦从我们","dailyDate":"2016-05-15"}],"reminder":[{"reminderId":13,"reminder":"啊啊啊埃杜阿多额撇问你个屁额我屁股恶恶个价位颇高稳婆去外婆亲我们去【哦从我们","reminderDate":"2016-05-15"}]}
     */

    private int code;
    private MessageBean message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * dailyId : 4
         * dailyRead : 啊啊啊埃杜阿多额撇问你个屁额我屁股恶恶个价位颇高稳婆去外婆亲我们去【哦从我们
         * dailyDate : 2016-05-15
         */

        private List<DailyBean> daily;
        /**
         * reminderId : 13
         * reminder : 啊啊啊埃杜阿多额撇问你个屁额我屁股恶恶个价位颇高稳婆去外婆亲我们去【哦从我们
         * reminderDate : 2016-05-15
         */

        private List<ReminderBean> reminder;

        public List<DailyBean> getDaily() {
            return daily;
        }

        public void setDaily(List<DailyBean> daily) {
            this.daily = daily;
        }

        public List<ReminderBean> getReminder() {
            return reminder;
        }

        public void setReminder(List<ReminderBean> reminder) {
            this.reminder = reminder;
        }

        public static class DailyBean {
            private int dailyId;
            private String dailyRead;
            private String dailyDate;

            public int getDailyId() {
                return dailyId;
            }

            public void setDailyId(int dailyId) {
                this.dailyId = dailyId;
            }

            public String getDailyRead() {
                return dailyRead;
            }

            public void setDailyRead(String dailyRead) {
                this.dailyRead = dailyRead;
            }

            public String getDailyDate() {
                return dailyDate;
            }

            public void setDailyDate(String dailyDate) {
                this.dailyDate = dailyDate;
            }
        }

        public static class ReminderBean {
            private int reminderId;
            private String reminder;
            private String reminderDate;

            public int getReminderId() {
                return reminderId;
            }

            public void setReminderId(int reminderId) {
                this.reminderId = reminderId;
            }

            public String getReminder() {
                return reminder;
            }

            public void setReminder(String reminder) {
                this.reminder = reminder;
            }

            public String getReminderDate() {
                return reminderDate;
            }

            public void setReminderDate(String reminderDate) {
                this.reminderDate = reminderDate;
            }
        }
    }
}
