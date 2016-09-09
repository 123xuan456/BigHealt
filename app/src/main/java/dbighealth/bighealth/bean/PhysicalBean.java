package dbighealth.bighealth.bean;

import java.util.List;

/**
 * Created by mhysa on 2016/9/8.
 * 我的体质
 */
public class PhysicalBean {

    /**
     * title : 阳虚质
     * result : [{"symptom":"您手脚发凉吗？","symptomId":1},{"symptom":"您胃脘部、背部或腰膝部怕冷吗？","symptomId":2},{"symptom":"您感到怕冷、衣服比别人穿得多吗？","symptomId":3},{"symptom":"您比别人容易患感冒吗？","symptomId":4},{"symptom":"您吃（喝）凉的东西会感到不舒服吗？","symptomId":5},{"symptom":"你受凉或吃（喝）凉的东西后容易腹泻（拉肚子）吗？","symptomId":6}]
     */

    private ContentBean content;
    /**
     * content : {"title":"阳虚质","result":[{"symptom":"您手脚发凉吗？","symptomId":1},{"symptom":"您胃脘部、背部或腰膝部怕冷吗？","symptomId":2},{"symptom":"您感到怕冷、衣服比别人穿得多吗？","symptomId":3},{"symptom":"您比别人容易患感冒吗？","symptomId":4},{"symptom":"您吃（喝）凉的东西会感到不舒服吗？","symptomId":5},{"symptom":"你受凉或吃（喝）凉的东西后容易腹泻（拉肚子）吗？","symptomId":6}]}
     * code : 200
     */

    private int code;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ContentBean {
        private String title;
        /**
         * symptom : 您手脚发凉吗？
         * symptomId : 1
         */

        private List<ResultBean> result;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            private String symptom;
            private int symptomId;

            public String getSymptom() {
                return symptom;
            }

            public void setSymptom(String symptom) {
                this.symptom = symptom;
            }

            public int getSymptomId() {
                return symptomId;
            }

            public void setSymptomId(int symptomId) {
                this.symptomId = symptomId;
            }
        }
    }
}
