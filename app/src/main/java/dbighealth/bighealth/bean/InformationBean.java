package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/9/13.
 */
public class InformationBean {
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
        private int userId;

        private int problemId;

        private String problem;

        private String questDate;

        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
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
        public void setQuestDate(String questDate){
            this.questDate = questDate;
        }
        public String getQuestDate(){
            return this.questDate;
        }

    }
}
