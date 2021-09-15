import java.util.List;

public class Movie {
    private String id;
    private String title;
    private int year;
    private String rating;
    private String runtime;
    private double meta;
    private double imdb;
    private List<String> directors;
    private List<String> writers;
    private List<String> genres;
    private List<String> actors;

    public Movie(String id, String title, int year, String rating, String runtime, double meta, double imdb,
                 List<String> directors, List<String> writers, List<String> genres, List<String> actors) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.meta = meta;
        this.imdb = imdb;
        this.directors = directors;
        this.writers = writers;
        this.genres = genres;
        this.actors = actors;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getRuntime() {
        return runtime;
    }

    public double getMeta() {
        return meta;
    }

    public double getImdb() {
        return imdb;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getActors() {
        return actors;
    }
}
