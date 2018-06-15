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
        boolean w = movieInfo.title == this.title;
        boolean x = movieInfo.duration == this.duration;
        boolean y =  movieInfo.genre.equals(this.genre);
        boolean z = movieInfo.censor.equals(this.censor);
        return movieInfo.duration == this.duration &&
                movieInfo.genre.equals(this.genre) &&
                movieInfo.censor.equals(this.censor);
    }
}
