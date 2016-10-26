/**
 * Copyright 2016 bejson.com
 */
package dbighealth.bighealth.bean;
import java.util.List;

/**
 * Auto-generated: 2016-10-14 12:58:5
 *
 */
public class InProductBean {

    private int id;
    private String title;
    private String images;
    private String content;
    private String price;
    private List<Littleimages> littleImages;
    private int code;
    private String hint;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setImages(String images) {
        this.images = images;
    }
    public String getImages() {
        return images;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setLittleImages(List<Littleimages> littleImages) {
        this.littleImages = littleImages;
    }
    public List<Littleimages> getLittleImages() {
        return littleImages;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    public String getHint() {
        return hint;
    }



    public class Littleimages {
        private String little;
        public void setLittle(String little) {
            this.little = little;
        }
        public String getLittle() {
            return little;
        }

    }
}