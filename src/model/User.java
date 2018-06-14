package model;

public class User {
    private String userClass;
    private int userId;

    //Constructor
    public User(String userClass, int userId) {
        this.userClass = userClass;
        this.userId = userId;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String newUserClass) { this.userClass = newUserClass; }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int newUserId) { this.userId = newUserId; }

    public MovieInfo getMovieInfo(String movieTitle) {
        return null;
    }

    public ShowTimeInfo getShowTimeInfo (String movieTitle) {
        return null;
    }
}
