package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by de on 2016/10/13.
 */
public class ManageSiteBean {


    /**
     * code : 200
     * message : [{"addressId":1,"userId":1,"name":"111","phoneNumber":"18533355444","area":"222","street":"333","address":"444","defaults":1},{"addressId":6,"userId":1,"name":"gy","phoneNumber":"113","area":"1","street":"1","address":"1","defaults":0},{"addressId":7,"userId":1,"name":"2","phoneNumber":"3","area":"4","street":"5","address":"6","defaults":0},{"addressId":8,"userId":1,"name":"1","phoneNumber":"phoneNumber","area":"area","street":"street","address":"address","defaults":0},{"addressId":9,"userId":1,"name":"嘿嘿嘿","phoneNumber":"phoneNumber","area":"area","street":"street","address":"address","defaults":0},{"addressId":10,"userId":1,"name":"嘿嘿嘿","phoneNumber":"18533355444","area":"北京市","street":"北京市","address":"北京市","defaults":1}]
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
         * addressId : 1
         * userId : 1
         * name : 111
         * phoneNumber : 18533355444
         * area : 222
         * street : 333
         * address : 444
         * defaults : 1
         */

        private int addressId;
        private int userId;
        private String name;
        private String phoneNumber;
        private String area;
        private String street;
        private String address;
        private int defaults;

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setDefaults(int defaults) {
            this.defaults = defaults;
        }

        public int getAddressId() {
            return addressId;
        }

        public int getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getArea() {
            return area;
        }

        public String getStreet() {
            return street;
        }

        public String getAddress() {
            return address;
        }

        public int getDefaults() {
            return defaults;
        }
    }
}
