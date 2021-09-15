import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        Site site = new Site();
        /*site.createMovie("The Martian", "Ridley Scott", Arrays.asList("Perseverance", "Ingenuity"), Arrays.asList("Sci-Fi", "Space"), Arrays.asList("Matt Damon", "Kristen Wigg"));
        site.createMovie("Iron Man", "Jon Favreau", Arrays.asList("Morality"), Arrays.asList("Sci-Fi", "Superhero"), Arrays.asList("Robert Downey Jr.", "Jeff Bridges"));
        site.createMovie("Iron Man 2", "Jon Favreau", Arrays.asList("Self-Discovery"), Arrays.asList("Sci-Fi", "Superhero"), Arrays.asList("Robert Downey Jr.", "Don Cheadle"));
        site.createMovie("The Other Guys", "Adam McKay", Arrays.asList("Courage"), Arrays.asList("Comedy", "Action"), Arrays.asList("Will Ferrell", "Mark Whalberg"));
        site.createMovie("The Hangover", "Todd Philips", Arrays.asList("Friendship"), Arrays.asList("Comedy", "Mystery"), Arrays.asList("Bradley Cooper", "Zach Galifinakis"));
        site.createMovie("A Few Good Men", "Rob Reiner", Arrays.asList("Truth"), Arrays.asList("Drama", "Legal"), Arrays.asList("Tom Cruise", "Jack Nicholson"));
        site.createMovie("Interstellar", "Christopher Nolan", Arrays.asList("Perseverance"), Arrays.asList("Sci-Fi", "Space"), Arrays.asList("Matthew McConaughy", "Anne Hathaway"));
        site.createMovie("Roman J. Israel, Esq.", "Dan Gilroy", Arrays.asList("Corruption"), Arrays.asList("Drama", "Legal"), Arrays.asList("Denzel Washington", "Colin Farrell"));
        */
        //site.makeConnection();

        site.search("Bloodshot", 2020);
    }
}
