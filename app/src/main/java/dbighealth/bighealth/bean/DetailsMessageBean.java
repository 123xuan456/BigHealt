package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/9/13.
 */
public class DetailsMessageBean {
    private int problemId;

    private String problem;

    private String getHelp;

    private List<Message> message ;

    private int code;

    public void setProblemId(int problemId){
        this.problemId = problemId;
    }
    public int getProblemId(){
        return this.problemId;
    }
    public void setProblem(String problem){
        this.problem = problem;
    }
    public String getProblem(){
        return this.problem;
    }
    public void setGetHelp(String getHelp){
        this.getHelp = getHelp;
    }
    public String getGetHelp(){
        return this.getHelp;
    }
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
        private String model;

        private String text;

        private String Date;

        public void setModel(String model){
            this.model = model;
        }
        public String getModel(){
            return this.model;
        }
        public void setText(String text){
            this.text = text;
        }
        public String getText(){
            return this.text;
        }
        public void setDate(String Date){
            this.Date = Date;
        }
        public String getDate(){
            return this.Date;
        }

    }
}
