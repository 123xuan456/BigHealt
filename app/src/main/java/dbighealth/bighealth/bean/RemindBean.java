package dbighealth.bighealth.bean;



import java.util.List;

/**
 * Created by de on 2016/9/28.
 */
public class RemindBean {
    private List<Message> message ;

    private int code;

    public void setMessage(List<Message> message){
        this.message = message;
    }
    public List<Message> getMessage(){
        return this.message;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }

    public class Message {

        private String medicineName;

        private String time;

        private String date;

        public void setMedicineName(String medicineName){
            this.medicineName = medicineName;
        }
        public String getMedicineName(){
            return this.medicineName;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }
        public void setDate(String date){
            this.date = date;
        }
        public String getDate(){
            return this.date;
        }

    }

}
