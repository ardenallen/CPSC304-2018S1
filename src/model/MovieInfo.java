package model;

public class MovieInfo {
    private String title;
    private int duration; // in minutes
    private String genre;
    private String censor;

    public MovieInfo(String title, int duration, String genre, String censor) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.censor = censor;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getCensor() {
        return censor;
    }

    public boolean equals(MovieInfo movieInfo) {
        return  movieInfo.title.equals(this.title) &&
                movieInfo.duration == this.duration &&
                movieInfo.genre.equals(this.genre) &&
                movieInfo.censor.equals(this.censor);
    }
}
