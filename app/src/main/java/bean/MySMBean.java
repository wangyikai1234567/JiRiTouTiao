package bean;

/**
 * date: 2017/4/14.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class MySMBean {
    private int image;
    private String title;

    public MySMBean(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public MySMBean() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
