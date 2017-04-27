package bean;

/**
 * date: 2017/4/20.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class SBBean {

    /**
     * code : 400
     * datas : {"error":"请填写用户名"}
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
         * error : 请填写用户名
         */

        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
