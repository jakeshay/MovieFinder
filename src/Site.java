import java.util.ArrayList;
import java.util.List;

public class Site {
    private List<Movie> movies;
    private List<Actor> actorsList;
    private List<Director> directorList;
    private Search search;
    private MovieFactory factory;
    private DbConnect connect;

    public Site() {
        this.movies = new ArrayList<>();
        this.actorsList = new ArrayList<>();
        this.directorList = new ArrayList<>();
        this.search = new Search();
        this.factory = new MovieFactory(actorsList, directorList);
        this.connect = new DbConnect();
    }

    public void retrieveMovies() {
        List<Movie> movieList = connect.createMovies(factory);

        for (Movie movie: movieList) {
            createMovie(movie);
        }
    }

    private void updateSearch() {
        search.updateList(this.movies);
    }

    public Movie findMovie(String name) {
        for (Movie movie : this.movies) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }

        return null;
    }

    public void createMovie(Movie movie) {
        movies.add(movie);
        actorsList = factory.updateActors();
        directorList = factory.updateDirectors();
        updateSearch();
    }

    public void createMovie(String name, String director, List<String> themes, List<String> genres, List<String> actors) {
        movies.add(factory.createMovie(name, director, themes, genres, actors));
        actorsList = factory.updateActors();
        directorList = factory.updateDirectors();
        updateSearch();
    }

    public void displayMovies() {
        List<Movie> tmp = this.movies;
        for (Movie movie : tmp) {
            System.out.println(movie.getName());
        }
    }

    public void displayMovie(String name) {
        Movie movie = findMovie(name);
        System.out.println(movie.getName());
        System.out.println(movie.getDirector().getName());
        System.out.println("Themes:");
        for (String theme: movie.getThemes()) {
            System.out.println(theme);
        }
        System.out.println("Genres:");
        for (String genre: movie.getGenres()) {
            System.out.println(genre);
        }
        System.out.println("Actors:");
        for (Actor actor: movie.getActors()) {
            System.out.println(actor.getName());
        }
    }

    public void search(String movieName) {
        for (Movie m: movies) {
            if (m.getName().equals(movieName)) {
                search(m);
            }
        }
    }

    private void search(Movie movie) {
        List<Movie> results = search.searchMovies(movie);
        for (Movie m: results) {
            System.out.println(m.getName());
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
