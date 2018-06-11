package model;

public class User {
    private String userClass;
    private Integer userId;

    public User(String userClass, Integer userId) {
        this.userClass = userClass;
        this.userId = userId;
    }

    public String getUserClass() {
        return userClass;
    }

    public Integer getUserId() {
        return userId;
    }
}
