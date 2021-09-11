import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieFactory {
    private List<Actor> actorsList;
    private List<Director> directorsList;

    public MovieFactory(List<Actor> actors, List<Director> directors) {
        actorsList = actors;
        directorsList = directors;
    }
    public Movie createMovie(String name, String director, List<String> themes, List<String> genres, List<String> actors) {
        Director d = findDirector(director);
        if (d == null) {
            Director newDirector = new Director(director);
            directorsList.add(newDirector);
            d = newDirector;
        }

        List<Actor> a1 = new ArrayList<>();
        for (String actor: actors) {
            Actor a = findActor(actor);
            if (a == null) {
                Actor newActor = new Actor(actor);
                actorsList.add(newActor);
                a = newActor;
            }
            a1.add(a);
        }

        Movie newMovie = new Movie(name, d, themes, genres, a1);
        d.addMovie(newMovie);

        for (Actor a2: a1) {
            a2.addMovie(newMovie);
        }

        return newMovie;
    }

    private Director findDirector(String director) {
        for (Director d: directorsList) {
            if (Objects.equals(d.getName(), director)) {
                return d;
            }
        }
        return null;
    }

    private Actor findActor(String actor) {
        for (Actor a: actorsList) {
            if (Objects.equals(a.getName(), actor)) {
                return a;
            }
        }
        return null;
    }

    public List<Actor> updateActors() {
        return actorsList;
    }

    public List<Director> updateDirectors() {
        return directorsList;
    }
}
