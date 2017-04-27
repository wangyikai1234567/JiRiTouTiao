package bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * date: 2017/4/18.
 * author: 王艺凯 (lenovo )
 * function:
 */
//表名

//name (String)       :  表名称
//        isId (boolean)      :  是否为主键
//        autoGen (boolean)   :  是否自增(默认: false)
//        proprety (String)   :  是否为空(默认: NOT NULL)
@Table(name =  "title")
public class MyTitle {
//    1 @Check     check约束
//    2 @Column    列名
//    3 @Finder    一对多、多对一、多对多关系(见sample的Parent、Child中的使用)
//    4 @Foreign   外键
//    5 @Id        主键，当为int类型时，默认自增。 非自增时，需要设置id的值
//    6 @NoAutoIncrement  不自增
//    7 @NotNull  不为空
//    8 @Table    表名
//    9 @Transient  不写入数据库表结构
//    10 @Unique     唯一约束
    @Column(name = "id",isId = true)
    private int id ;
    @Column(name = "title")
    private String title;
    @Column(name = "uri")
    private String uri;

    @Column(name = "codes")
    private int codes;


    public MyTitle() {
    }

    public MyTitle(int id, String title, int codes, String uri) {
        this.id = id;
        this.title = title;
        this.codes = codes;
        this.uri = uri;
    }

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getCodes() {
        return codes;
    }

    public void setCodes(int codes) {
        this.codes = codes;
    }
}
