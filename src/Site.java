import java.util.*;

public class Site {
    private Dictionary<String, Movie> movies;
    private Search search;
    private DbConnect connect;

    public Site() {
        this.movies = new Hashtable();
        this.search = new Search(movies);
        this.connect = new DbConnect();
    }

    private void updateMovies() {
        this.movies = search.updateMovies();
    }

    public Movie findMovie(String title, int year) {
        for (Enumeration e = movies.keys(); e.hasMoreElements();) {
            String id = (String) e.nextElement();
            Movie m = movies.get(id);
            if (m.getTitle().equals(title) && m.getYear() == year) {
                return m;
            }
        }

        return null;
    }

    public Movie getMovie(String title, int year) {
        Movie m = connect.findMovie(title, year);
        movies.put(m.getId(), m);
        search.getUpdate(movies);

        return m;
    }

    public void displayMovie(String name, int year) {
        Movie movie = findMovie(name, year);
        if (movie == null) {
            movie = getMovie(name, year);
        }
        System.out.println(movie.getId());
        System.out.println(name);
        System.out.println(year);
        System.out.println(movie.getRating());
        System.out.println(movie.getRuntime());
        System.out.println(movie.getMeta());
        System.out.println(movie.getImdb());
        System.out.println("Directors:");
        for (String director: movie.getDirectors()) {
            System.out.println(director);
        }
        System.out.println("Writers:");
        for (String writer: movie.getWriters()) {
            System.out.println(writer);
        }
        System.out.println("Genres:");
        for (String genre: movie.getGenres()) {
            System.out.println(genre);
        }
        System.out.println("Actors:");
        for (String actor: movie.getActors()) {
            System.out.println(actor);
        }
    }

    public void search(String movieName, int year) {
        Movie m = findMovie(movieName, year);
        if (m == null) {
            m = getMovie(movieName, year);
        }
        search(m);
    }

    private void search(Movie movie) {
        List<Movie> results = search.searchMovies(movie);
        for (Movie m: results) {
            System.out.println(m.getTitle());
        }
        movies = search.updateMovies();
    }

    public Dictionary<String, Movie> getMovies() {
        return movies;
    }
}
