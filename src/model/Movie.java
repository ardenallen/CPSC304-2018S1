package model;

public class Movie {
    private String title;
    private String genre;
    private int duration;
    private String censor;

    public Movie() {
        /*
         * Use pulled info from oracle for object construction
         */

    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getCensor() {
        return censor;
    }
}
