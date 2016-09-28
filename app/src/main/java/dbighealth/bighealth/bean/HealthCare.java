package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/12.
 * 医疗养生
 */
public class HealthCare {


    /**
     * medicalList : [{"id":1,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/0fb505d5492100a651da4b97.png","med":"1","region":1,"name":"王吉吉"},{"id":2,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/1-151112102610I8.jpg","med":"1","region":1,"name":"王吉吉"}]
     * code : 200
     * hint : 获取成功
     */

    private int code;
    private String hint;
    /**
     * id : 1
     * imageUrl : http://192.168.0.43:8080/JianKangChanYe/upload/care/0fb505d5492100a651da4b97.png
     * med : 1
     * region : 1
     * name : 王吉吉
     */

    private List<MedicalListBean> medicalList;

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

    public List<MedicalListBean> getMedicalList() {
        return medicalList;
    }

    public void setMedicalList(List<MedicalListBean> medicalList) {
        this.medicalList = medicalList;
    }

    public static class MedicalListBean {
        private int id;
        private String imageUrl;
        private String med;
        private int region;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getMed() {
            return med;
        }

        public void setMed(String med) {
            this.med = med;
        }

        public int getRegion() {
            return region;
        }

        public void setRegion(int region) {
            this.region = region;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
