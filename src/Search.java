import java.util.*;

public class Search {
    private List<Movie> movies;

    public Search() {
        this.movies = new ArrayList<>();
    }

    public void updateList(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> searchByDirector(Movie movie) {
        return movie.getDirector().getMovies();
    }

    public List<Movie> searchByTheme(String theme) {
        List<Movie> toReturn = new ArrayList<>();

        for (Movie movie: movies) {
            for (String t: movie.getThemes()) {
                if (Objects.equals(t, theme)) {
                    toReturn.add(movie);
                }
            }
        }

        return toReturn;
    }

    public List<List<Movie>> searchByThemes(List<String> themes) {
        List<List<Movie>> toReturn  = new ArrayList<>();

        for (String theme: themes) {
            toReturn.add(searchByTheme(theme));
        }

        return toReturn;
    }

    public List<Movie> searchByGenre(String genre) {
        List<Movie> toReturn = new ArrayList<>();

        for (Movie movie: movies) {
            for (String g: movie.getGenres()) {
                if (Objects.equals(g, genre)) {
                    toReturn.add(movie);
                }
            }
        }

        return toReturn;
    }

    public List<List<Movie>> searchByGenres(List<String> genres) {
        List<List<Movie>> toReturn  = new ArrayList<>();

        for (String genre: genres) {
            toReturn.add(searchByGenre(genre));
        }

        return toReturn;
    }

    public List<List<Movie>> searchByActors(Movie movie) {
        List<List<Movie>> toReturn  = new ArrayList<>();

        List<Actor> actors = movie.getActors();
        for (Actor actor: actors) {
            toReturn.add(actor.getMovies());
        }

        return toReturn;
    }

    public List<Movie> searchMovies(Movie movie) {
        Dictionary results = new Hashtable();
        List<Movie> similarMovies = new ArrayList<>();

        for (Movie m: searchByDirector(movie)) {
            results.put(m, 1);
        }

        for (List<Movie> l: searchByThemes(movie.getThemes())) {
            for (Movie m: l) {
                if (results.get(m) != null) {
                    int x = (int) results.get(m);
                    results.put(m, x + 1);
                }
                else {
                    results.put(m, 1);
                }
            }
        }

        for (List<Movie> l: searchByGenres(movie.getGenres())) {
            for (Movie m: l) {
                if (results.get(m) != null) {
                    int x = (int) results.get(m);
                    results.put(m, x + 1);
                }
                else {
                    results.put(m, 1);
                }
            }
        }

        for (List<Movie> l: searchByActors(movie)) {
            for (Movie m: l) {
                if (results.get(m) != null) {
                    int x = (int) results.get(m);
                    results.put(m, x + 1);
                }
                else {
                    results.put(m, 1);
                }
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


}
