package bean;

/**
 * date: 2017/4/20.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class ZCBean {
    private String username;
    private String password;
    private String email;

    public ZCBean(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public ZCBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ZCBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
