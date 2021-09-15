import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {
    private String url;
    private String user;
    private String pw;
    private MovieFactory factory;

    public DbConnect() {
        this.url = "jdbc:mysql://localhost:3306/MovieFinder";
        this.user = "root";
        this.pw  = "";

        this.factory = new MovieFactory();
    }

    /**
     * Find movie in the DB, parse data and create a new Movie object
     * @param title of movie
     * @param year released
     * @return new movie object with DB data
     */
    public Movie findMovie(String title, int year) {
        Movie result = null;
        try(Connection conn = DriverManager.getConnection(url, user, pw)) {
            System.out.println(String.format("Finding: %s", title));

            String query = String.format("SELECT * FROM Movie WHERE Title = '%s' AND ReleaseYear = %d;", title, year);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
                String id = rs.getString("MovieID");
                String rating = rs.getString("Rating");
                String runtime = rs.getString("Runtime");
                double meta = rs.getDouble("Metascore");
                double imdb = rs.getDouble("imdbRating");

            List<String> directors = getData(id, "Directors");
            List<String> writers = getData(id, "Writers");
            List<String> genres = getData(id, "Genres");
            List<String> actors = getData(id, "Actors");

            result = factory.createMovie(id, title, year, rating, runtime, meta, imdb, directors, writers, genres, actors);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Find movie by id
     * @param id of movie
     * @return new movie object with returned data from db
     */
    public Movie findMovieID(String id) {
        Movie result = null;
        try(Connection conn = DriverManager.getConnection(url, user, pw)) {
            System.out.println(String.format("Finding: %s", id));

            String query = String.format("SELECT * FROM Movie WHERE MovieID = '%s';", id);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String title = rs.getString("Title");
            int year = rs.getInt("ReleaseYear");
            String rating = rs.getString("Rating");
            String runtime = rs.getString("Runtime");
            double meta = rs.getDouble("Metascore");
            double imdb = rs.getDouble("imdbRating");

            List<String> directors = getData(id, "Directors");
            List<String> writers = getData(id, "Writers");
            List<String> genres = getData(id, "Genres");
            List<String> actors = getData(id, "Actors");

            result = factory.createMovie(id, title, year, rating, runtime, meta, imdb, directors, writers, genres, actors);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Search by movie id in specified table
     * @param id of movie
     * @param type table to search
     * @return data returned from db query
     */
    private List<String> getData(String id, String type) {
        try(Connection conn = DriverManager.getConnection(url, user, pw)) {
            String query = String.format("SELECT Name FROM %s WHERE MovieID = '%s'", type, id);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            List<String> data = new ArrayList<>();

            while (rs.next()) {
                data.add(rs.getString("Name"));
            }

            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * search for movie id's
     * @param data parameter for query
     * @param type table to search
     * @return list of movie id's
     */
    public List<String> searchByType(List<String> data, String type) {
        List<String> results = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, pw);) {
            for (String d : data) {
                String query = String.format("SELECT * FROM %s WHERE Name = '%s'", type, d);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                rs.next();

                results.add(rs.getString("MovieID"));

            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
