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

    public List<Movie> searchMovies(Movie movie) {
        Dictionary results = new Hashtable();
        List<Movie> similarMovies = new ArrayList<>();

        List<String> byDirector = db.directedBy(movie.getDirectors());
        for (String id: byDirector) {
            Movie m = findMovie(id);
            if (m == null) {
                m = db.findMovieID(id);
                movies.put(id, m);
            }
            results.put(m, 1);
        }
        List<String> byWriter = db.writtenBy(movie.getWriters());
        for (String id: byWriter) {
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
        List<String> byGenre = db.byGenres(movie.getGenres());
        for (String id: byGenre) {
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
        List<String> byActor = db.byActors(movie.getActors());
        for (String id: byActor) {
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
