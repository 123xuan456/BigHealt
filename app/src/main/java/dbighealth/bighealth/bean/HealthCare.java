package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/12.
 * 医疗养生
 */
public class HealthCare {

    /**
     * medicalList : [{"id":9,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152356.jpg","med":"2","region":1},{"id":11,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151816.jpg","med":"2","region":1},{"id":13,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151816.jpg","med":"2","region":1},{"id":15,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152304.jpg","med":"2","region":1}]
     * code : 200
     * hint : 获取成功
     */

    private int code;
    private String hint;
    /**
     * id : 9
     * imageUrl : http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152356.jpg
     * med : 2
     * region : 1
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
    }
}
