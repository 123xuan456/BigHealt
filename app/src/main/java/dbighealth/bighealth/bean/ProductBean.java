package dbighealth.bighealth.bean;


import java.util.List;

public class ProductBean {

        //返回值
        private int code;
        //获取成功
        private String hint;

        private List<ProductList> productList ;

        public void setCode(int code){
            this.code = code;
        }
        public int getCode(){
            return this.code;
        }
        public void setHint(String hint){
            this.hint = hint;
        }
        public String getHint(){
            return this.hint;
        }
        public void setProductList(List<ProductList> productList){
            this.productList = productList;
        }
        public List<ProductList> getProductList(){
            return this.productList;
        }

    public static class ProductList {
        private int id;
        //名字
        private String title;
        //图片
        private String images;
        //内容
        private String content;
        //
        private int pid;
        //时间
        private String time;

       public ProductList(int id, String typename){

            super();
            this.id = id;
            this.title = typename;

        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setImages(String images){
            this.images = images;
        }
        public String getImages(){
            return this.images;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setPid(int pid){
            this.pid = pid;
        }
        public int getPid(){
            return this.pid;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }

    }

}
