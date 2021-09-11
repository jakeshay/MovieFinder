import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {
    private String url;
    private String user;
    private String pw;

    public DbConnect() {
        this.url = "jdbc:mysql://localhost:3306/MovieFinder";
        this.user = "root";
        this.pw  = "";


    }


    public List<Movie> createMovies(MovieFactory factory) {
        List<Movie> movieList = new ArrayList<>();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pw);

            Statement stmt = con.createStatement();
            String query = "SELECT MovieID, MovieName, Director FROM Movie;";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("MovieID");
                String name = rs.getString("MovieName");
                String director = rs.getString("Director");

                String query2 = String.format("SELECT ThemeName FROM Theme WHERE MovieID = %d;", id);
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);
                List<String> themes = new ArrayList<>();
                while (rs2.next()) {
                    themes.add(rs2.getString("ThemeName"));
                }

                String query3 = String.format("SELECT GenreName FROM Genre WHERE MovieID = %d;", id);
                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery(query3);
                List<String> genres = new ArrayList<>();
                while (rs3.next()) {
                    genres.add(rs3.getString("GenreName"));
                }

                String query4 = String.format("SELECT ActorName FROM Actor WHERE MovieID = %d;", id);
                Statement stmt4 = con.createStatement();
                ResultSet rs4 = stmt4.executeQuery(query4);
                List<String> actors = new ArrayList<>();
                while (rs4.next()) {
                    actors.add(rs4.getString("ActorName"));
                }

                Movie tmp = factory.createMovie(name, director, themes, genres, actors);
                movieList.add(tmp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieList;
    }


}
