package model;

public class MovieStat {
    public String title;
    public int count;

    public MovieStat(String title, int count) {
        this.title = title;
        this.count = count;
    }

    // Returns true if this movie is more popular than movie x
    public boolean morePopular(MovieStat x) {
        return this.count >= x.count;
    }
}
