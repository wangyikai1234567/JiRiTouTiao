package bean;

/**
 * date: 2017/4/21.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class CGBean {

    /**
     * code : 200
     * datas : {"key":"ea4887a8cc0d1f146c359c4697c58519","userid":"62","username":"wangyikai11"}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * key : ea4887a8cc0d1f146c359c4697c58519
         * userid : 62
         * username : wangyikai11
         */

        private String key;
        private String userid;
        private String username;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
