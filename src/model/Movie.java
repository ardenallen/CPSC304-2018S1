package model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private int duration; // in minutes
    private String genre;
    private String censor;

    public Movie(String title, int duration, String genre, String censor) {
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

    public static List<Movie> getAllMovieInfo() {
        List<Movie> allMovie = new ArrayList<>();

        /*
         * TODO: Add query to pull all movies
         */

        return allMovie;
    }

    public boolean equals(Movie movie) {
        return  movie.title.equals(this.title) &&
                movie.duration == this.duration &&
                movie.genre.equals(this.genre) &&
                movie.censor.equals(this.censor);
    }
}
