package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/12.
 * 医疗养生
 */
public class TreatmentBean {


    /**
     * code : 200
     * hint : 获取成功
     * result : [{"region":[{"id":1,"province":"北京市"},{"id":2,"province":"天津市"},{"id":3,"province":"河北省"},{"id":4,"province":"山西省"}],"results":[{"id":1,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/0fb505d5492100a651da4b97.png","med":"1","region":1,"name":"王吉吉"},{"id":2,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/1-151112102610I8.jpg","med":"1","region":1,"name":"王吉吉"},{"id":13,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/20141112034433983.jpg","med":"2","region":1,"name":"公司名称"},{"id":15,"imageUrl":"http://192.168.0.43:8080/JianKangChanYe/upload/care/201411131415847532953.jpg","med":"2","region":1,"name":"公司名称"}]}]
     */

    private int code;
    private String hint;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * province : 北京市
         */

        private List<RegionBean> region;
        /**
         * id : 1
         * imageUrl : http://192.168.0.43:8080/JianKangChanYe/upload/care/0fb505d5492100a651da4b97.png
         * med : 1
         * region : 1
         * name : 王吉吉
         */

        private List<ResultsBean> results;

        public List<RegionBean> getRegion() {
            return region;
        }

        public void setRegion(List<RegionBean> region) {
            this.region = region;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class RegionBean {
            private int id;
            private String province;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }
        }

        public static class ResultsBean {
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
}
