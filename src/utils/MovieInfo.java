package utils;

public class MovieInfo {
    private int duration; // in minutes
    private String genre;
    private String censor;

    public MovieInfo(int duration, String genre, String censor) {
        this.duration = duration;
        this.genre = genre;
        this.censor = censor;
    }
}
