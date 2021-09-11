import java.util.List;

public class Movie {
    private String name;
    private Director director;
    private List<String> themes;
    private List<String> genres;
    private List<Actor> actors;

    public Movie(String name, Director director, List<String> themes, List<String> genres, List<Actor> actors) {
        this.name = name;
        this.director = director;
        this.themes = themes;
        this.genres = genres;
        this.actors = actors;
    }


    public String getName() {
        return name;
    }

    public Director getDirector() {
        return director;
    }

    public List<String> getThemes() {
        return themes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
