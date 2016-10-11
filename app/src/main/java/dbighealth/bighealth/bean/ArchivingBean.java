package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/10/11.
 */
public class ArchivingBean {

    /**
     * code : 200
     * message : [{"userId":33,"name":"我的天啊啊啊啊","nation":"汉族","sex":"男","bloodType":"A","living":"北京","height":182,"weight":50,"birth":null,"allergy":null,"familyHistory":null,"pastMedical":null,"habit_diet":0,"habit_sleep":0,"habit_motion":6,"textExplain":"aaaa 啊啊啊啊啊 还是"}]
     */

    private int code;
    private List<MessageBean> message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public static class MessageBean {
        /**
         * userId : 33
         * name : 我的天啊啊啊啊
         * nation : 汉族
         * sex : 男
         * bloodType : A
         * living : 北京
         * height : 182
         * weight : 50
         * birth : null
         * allergy : null
         * familyHistory : null
         * pastMedical : null
         * habit_diet : 0
         * habit_sleep : 0
         * habit_motion : 6
         * textExplain : aaaa 啊啊啊啊啊 还是
         */

        private int userId;
        private String name;
        private String nation;
        private String sex;
        private String bloodType;
        private String living;
        private int height;
        private int weight;
        private Object birth;
        private Object allergy;
        private Object familyHistory;
        private Object pastMedical;
        private int habit_diet;
        private int habit_sleep;
        private int habit_motion;
        private String textExplain;

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public void setLiving(String living) {
            this.living = living;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setBirth(Object birth) {
            this.birth = birth;
        }

        public void setAllergy(Object allergy) {
            this.allergy = allergy;
        }

        public void setFamilyHistory(Object familyHistory) {
            this.familyHistory = familyHistory;
        }

        public void setPastMedical(Object pastMedical) {
            this.pastMedical = pastMedical;
        }

        public void setHabit_diet(int habit_diet) {
            this.habit_diet = habit_diet;
        }

        public void setHabit_sleep(int habit_sleep) {
            this.habit_sleep = habit_sleep;
        }

        public void setHabit_motion(int habit_motion) {
            this.habit_motion = habit_motion;
        }

        public void setTextExplain(String textExplain) {
            this.textExplain = textExplain;
        }

        public int getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getNation() {
            return nation;
        }

        public String getSex() {
            return sex;
        }

        public String getBloodType() {
            return bloodType;
        }

        public String getLiving() {
            return living;
        }

        public int getHeight() {
            return height;
        }

        public int getWeight() {
            return weight;
        }

        public Object getBirth() {
            return birth;
        }

        public Object getAllergy() {
            return allergy;
        }

        public Object getFamilyHistory() {
            return familyHistory;
        }

        public Object getPastMedical() {
            return pastMedical;
        }

        public int getHabit_diet() {
            return habit_diet;
        }

        public int getHabit_sleep() {
            return habit_sleep;
        }

        public int getHabit_motion() {
            return habit_motion;
        }

        public String getTextExplain() {
            return textExplain;
        }
    }
}
