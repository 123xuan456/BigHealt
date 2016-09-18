package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/14.
 * 医疗养生详情
 */
public class CompanyDetail {


    /**
     * name : 医疗养生会所
     * address : 北京市东城区
     * telephone : 12345678
     * size : 300
     * bigPic : http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152304.jpg
     * littlePic : [{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151835.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151844.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151908.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152316.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152326.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152336.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151816.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152304.jpg"}]
     */

    private MessageBean message;
    /**
     * message : {"name":"医疗养生会所","address":"北京市东城区","telephone":"12345678","size":"300","bigPic":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152304.jpg","littlePic":[{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151835.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151844.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151908.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152316.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152326.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152336.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151816.jpg"},{"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906152304.jpg"}]}
     * code : 200
     */

    private int code;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class MessageBean {
        private String name;
        private String address;
        private String telephone;
        private String size;
        private String bigPic;
        /**
         * imageUrl : http://192.168.0.43:8080/JianKangChanYe/upload/care/20160906151835.jpg
         */

        private List<LittlePicBean> littlePic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getBigPic() {
            return bigPic;
        }

        public void setBigPic(String bigPic) {
            this.bigPic = bigPic;
        }

        public List<LittlePicBean> getLittlePic() {
            return littlePic;
        }

        public void setLittlePic(List<LittlePicBean> littlePic) {
            this.littlePic = littlePic;
        }

        public static class LittlePicBean {
            private String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }
}
