import java.util.*;

public class Search {
    private Dictionary<String, Movie> movies;
    private DbConnect db;

    public Search(Dictionary<String, Movie> movies) {
        this.movies = movies;
        this.db = new DbConnect();
    }

    private Movie findMovie(String id) {
        return movies.get(id);
    }

    /**
     * Get list of similar movies by type
     * @param data search parameter for query
     * @param type table to search
     * @param results similar movie dictionary to update
     * @return updated dictionary
     */
    private Dictionary searchBy(List<String> data, String type, Dictionary results) {
        List<String> ids = db.searchByType(data, type);
        for (String id: ids) {
            Movie m = findMovie(id);
            if (m == null) {
                m = db.findMovieID(id);
                movies.put(id, m);
            }
            if (results.get(m) != null) {
                int x = (int) results.get(m);
                results.put(m, x + 1);
            }
            else {
                results.put(m, 1);
            }
        }

        return results;
    }

    /**
     * Find similar movies
     * @param movie input
     * @return list of movies in order of similarity
     */
    public List<Movie> searchMovies(Movie movie) {
        Dictionary results = new Hashtable();
        List<Movie> similarMovies = new ArrayList<>();

        results = searchBy(movie.getDirectors(), "Directors", results);
        results = searchBy(movie.getWriters(), "Writers", results);
        results = searchBy(movie.getGenres(), "Genres", results);
        results = searchBy(movie.getActors(), "Actors", results);

        results.remove(movie);

        while (!results.isEmpty()) {
            Movie tmp;
            Movie similar = null;
            int maxInt = -1;
            for (Enumeration e = results.keys(); e.hasMoreElements();) {
                tmp = (Movie) e.nextElement();
                if ((int) results.get(tmp) > maxInt) {
                    similar = tmp;
                    maxInt = (int) results.get(tmp);
                }
            }

            similarMovies.add(similar);
            results.remove(similar);
        }

        return similarMovies;
    }

    public void getUpdate(Dictionary<String, Movie> movies) {
        this.movies = movies;
    }

    public Dictionary<String, Movie> updateMovies() {
        return movies;
    }


}
