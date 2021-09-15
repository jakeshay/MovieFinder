import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieFactory {

    public Movie createMovie(String id, String name, int year, String rating, String runtime,
                             double meta, double imdb, List<String> directors, List<String> writers,
                             List<String> genres, List<String> actors) {

        Movie newMovie = new Movie(id, name, year, rating, runtime, meta, imdb, directors, writers, genres, actors);

        return newMovie;
    }

}
