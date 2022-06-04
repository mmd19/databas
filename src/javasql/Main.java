package javasql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection conn = databasehelper.DbConnect("movies");

        showAllMovies(conn);

        showAllActors(conn);

        updateActors(conn, "Will Smith", "USA");

        removeMovie(conn, "Mr Bean");

        addNewMovie(conn, "Scream", "Horror", 2001);

        conn.close();
    }

    private static void addNewMovie(Connection conn, String name, String genre, int releaseDate) {
        Movies myMovies = new Movies(conn);

        int antal = myMovies.addMovie(name, genre, releaseDate);
        System.out.println("tillagd : " + antal);
    }

    private static void removeMovie(Connection conn, String name) {
        Movies myMovies = new Movies(conn);

        int antal = myMovies.removeMovie(name);
        System.out.println("borttaget : " + antal);
    }

    private static void showAllMovies(Connection conn) {
        Movies myMovies = new Movies(conn);

        ArrayList<String> document = new ArrayList<String>();
        document.add(myMovies.toJson());

        System.out.println(document);
    }

    private static void showAllActors(Connection conn) {
        Actors myActors = new Actors(conn);

        ArrayList<String> document = new ArrayList<String>();
        document.add(myActors.toJson());

        System.out.println(document);
    }

    private static void updateActors(Connection conn, String name, String country) {
        Actors myActors = new Actors(conn);

        int antal = myActors.updateActors(name, country);
        System.out.println("uppdaterat : " + antal);
    }

}
