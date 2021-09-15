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
            query = String.format("SELECT Name FROM Directors WHERE MovieID = '%s'", id);
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);
            List<String> directors = new ArrayList<>();

            while (rs2.next()) {
                directors.add(rs2.getString("Name"));
            }

            query = String.format("SELECT Name FROM Writers WHERE MovieID = '%s'", id);
            Statement stmt3 = conn.createStatement();
            ResultSet rs3 = stmt3.executeQuery(query);
            List<String> writers = new ArrayList<>();

            while (rs3.next()) {
                writers.add(rs3.getString("Name"));
            }

            query = String.format("SELECT Name FROM Genres WHERE MovieID = '%s'", id);
            Statement stmt4 = conn.createStatement();
            ResultSet rs4 = stmt4.executeQuery(query);
            List<String> genres = new ArrayList<>();

            while (rs4.next()) {
                genres.add(rs4.getString("Name"));
            }

            query = String.format("SELECT Name FROM Actors WHERE MovieID = '%s'", id);
            Statement stmt5 = conn.createStatement();
            ResultSet rs5 = stmt5.executeQuery(query);
            List<String> actors = new ArrayList<>();

            while (rs5.next()) {
                actors.add(rs5.getString("Name"));
            }

            result = factory.createMovie(id, title, year, rating, runtime, meta, imdb, directors, writers, genres, actors);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Movie findMovieID(String id) {
        Movie result = null;
        try(Connection conn = DriverManager.getConnection(url, user, pw);) {
            System.out.println(String.format("Finding: %s", id));

            String query = String.format("SELECT * FROM Movie WHERE MovieID = '%s'", id);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            String title = rs.getString("Title");
            int year = rs.getInt("ReleaseYear");
            String rating =  rs.getString("Rating");
            String runtime = rs.getString("Runtime");
            double meta = rs.getDouble("Metascore");
            double imdb = rs.getDouble("imdbRating");

            query = String.format("SELECT Name FROM Directors WHERE MovieID = '%s'", id);
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);
            List<String> directors = new ArrayList<>();

            while (rs2.next()) {
                directors.add(rs2.getString("Name"));
            }

            query = String.format("SELECT Name FROM Writers WHERE MovieID = '%s'", id);
            Statement stmt3 = conn.createStatement();
            ResultSet rs3 = stmt3.executeQuery(query);
            List<String> writers = new ArrayList<>();

            while (rs3.next()) {
                writers.add(rs3.getString("Name"));
            }

            query = String.format("SELECT Name FROM Genres WHERE MovieID = '%s'", id);
            Statement stmt4 = conn.createStatement();
            ResultSet rs4 = stmt4.executeQuery(query);
            List<String> genres = new ArrayList<>();

            while (rs4.next()) {
                genres.add(rs4.getString("Name"));
            }

            query = String.format("SELECT Name FROM Actors WHERE MovieID = '%s'", id);
            Statement stmt5 = conn.createStatement();
            ResultSet rs5 = stmt5.executeQuery(query);
            List<String> actors = new ArrayList<>();

            while (rs5.next()) {
                actors.add(rs5.getString("Name"));
            }

            result = factory.createMovie(id, title, year, rating, runtime, meta, imdb, directors, writers, genres, actors);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<String> directedBy(List<String> directors) {
        List<String> results = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, pw);) {
            for (String director : directors) {
                System.out.println(String.format("Search for movies directed by: %s", director));

                String query = String.format("SELECT * FROM Directors WHERE Name = '%s'", director);
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

    public List<String> writtenBy(List<String> writers) {
        List<String> results = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, pw);) {
            for (String writer : writers) {
                System.out.println(String.format("Search for movies written by: %s", writer));

                String query = String.format("SELECT * FROM Writers WHERE Name = '%s'", writer);
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

    public List<String> byGenres(List<String> genres) {
        List<String> results = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, pw);) {
            for (String genre : genres) {
                System.out.println(String.format("Search for movies with genre: %s", genre));

                String query = String.format("SELECT * FROM Genres WHERE Name = '%s'", genre);
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

    public List<String> byActors(List<String> actors) {
        List<String> results = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, pw);) {
            for (String actor : actors) {
                System.out.println(String.format("Search for movies starring: %s", actor));

                String query = String.format("SELECT * FROM Actors WHERE Name = '%s'", actor);
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
