package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class CommonHomeBean {


    /**
     * code : 200
     * hint : 获取成功
     * result : [{"title":"眼病","results":[{"id":1,"title":"白内障 ","images":"http://192.168.0.38:8080/JianKangChanYe/upload/20160818151330.jpg","content":"白内障 视力减退，看东西模糊、物体的颜色不够明亮等。老年人平时需要戴老花镜才能看书读报，某天忽然不需要戴老花镜也看得很清楚，这很可能是白内障的早期症状之一。","homeyid":2,"picid":1},{"id":2,"title":"青光眼","images":"http://192.168.0.38:8080/JianKangChanYe/upload/20160818151439.jpg","content":"青光眼 眼压升高引起视神经损害，头痛或伴有眼眶、鼻根胀痛；恶心呕吐；看红光会有一个彩虹样的光圈绕在灯光周围，外围红色，内圈绿色或紫蓝色；夜间出现雾视和视物模糊。患有高血压、糖尿病、动脉硬化等疾病是易患青光眼的危险人群。","homeyid":2,"picid":1}]},{"title":"肾病","results":[]},{"title":"足病","results":[]}]
     */

    private int code;
    private String hint;
    /**
     * title : 眼病
     * results : [{"id":1,"title":"白内障 ","images":"http://192.168.0.38:8080/JianKangChanYe/upload/20160818151330.jpg","content":"白内障 视力减退，看东西模糊、物体的颜色不够明亮等。老年人平时需要戴老花镜才能看书读报，某天忽然不需要戴老花镜也看得很清楚，这很可能是白内障的早期症状之一。","homeyid":2,"picid":1},{"id":2,"title":"青光眼","images":"http://192.168.0.38:8080/JianKangChanYe/upload/20160818151439.jpg","content":"青光眼 眼压升高引起视神经损害，头痛或伴有眼眶、鼻根胀痛；恶心呕吐；看红光会有一个彩虹样的光圈绕在灯光周围，外围红色，内圈绿色或紫蓝色；夜间出现雾视和视物模糊。患有高血压、糖尿病、动脉硬化等疾病是易患青光眼的危险人群。","homeyid":2,"picid":1}]
     */

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
        private String title;
        /**
         * id : 1
         * title : 白内障
         * images : http://192.168.0.38:8080/JianKangChanYe/upload/20160818151330.jpg
         * content : 白内障 视力减退，看东西模糊、物体的颜色不够明亮等。老年人平时需要戴老花镜才能看书读报，某天忽然不需要戴老花镜也看得很清楚，这很可能是白内障的早期症状之一。
         * homeyid : 2
         * picid : 1
         */

        private List<ResultsBean> results;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            private int id;
            private String title;
            private String images;
            private String content;
            private int homeyid;
            private int picid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getHomeyid() {
                return homeyid;
            }

            public void setHomeyid(int homeyid) {
                this.homeyid = homeyid;
            }

            public int getPicid() {
                return picid;
            }

            public void setPicid(int picid) {
                this.picid = picid;
            }
        }
    }
}
