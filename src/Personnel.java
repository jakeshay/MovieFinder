import java.util.ArrayList;
import java.util.List;

public abstract class Personnel {
    protected String name;
    protected String bio;
    protected List<Movie> movies;

    public Personnel(String name) {
        this.name = name;
        movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
