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

    public boolean equals(MovieInfo movieInfo) {
        boolean x = movieInfo.duration == this.duration;
        boolean y =  movieInfo.genre.equals(this.genre);
        boolean z = movieInfo.censor.equals(this.censor);
        return movieInfo.duration == this.duration &&
                movieInfo.genre.equals(this.genre) &&
                movieInfo.censor.equals(this.censor);
    }
}
