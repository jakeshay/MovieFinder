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

    /**
     * Update current dictionary of movies from search
     */
    private void updateMovies() {
        this.movies = search.updateMovies();
    }

    /**
     * Find and return a movie, if it doesn't already exist in the dictionary then call getMovie method and pull from DB
     * @param title of movie
     * @param year released
     * @return movie object
     */
    public Movie findMovie(String title, int year) {
        for (Enumeration e = movies.keys(); e.hasMoreElements();) {
            String id = (String) e.nextElement();
            Movie m = movies.get(id);
            if (m.getTitle().equals(title) && m.getYear() == year) {
                return m;
            }
        }

        return getMovie(title, year);
    }

    /**
     * Send data to DbConnect to find and create new movie object with DB details
     * @param title of movie
     * @param year released
     * @return movie object
     */
    private Movie getMovie(String title, int year) {
        Movie m = connect.findMovie(title, year);
        movies.put(m.getId(), m);
        search.getUpdate(movies);

        return m;
    }

    /**
     * Display movie details
     * @param name of movie
     * @param year released
     */
    public void displayMovie(String name, int year) {
        Movie movie = findMovie(name, year);

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

    /**
     * Search DB to find all similar movies and return them in order of similarity
     * @param movieName title of movie
     * @param year released
     */
    public void search(String movieName, int year) {
        Movie m = findMovie(movieName, year);

        List<Movie> results = search.searchMovies(m);
        for (Movie movie: results) {
            System.out.println(movie.getTitle());
        }

        updateMovies();
    }

    public Dictionary<String, Movie> getMovies() {
        return movies;
    }
}
