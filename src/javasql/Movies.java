package javasql;

import beans.MovieBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Movies {
    private Connection connection;
    private ArrayList<MovieBean> movies;

    private String selectAllMovies = "select * from viewmoviegenres";
    private String addMovie = "insert into movie (name, release_date, length) values (?, ?, ?)";
    private String addMovieGenre = "insert into movie_genre (movie_id, genre_id) values (?, ?)";
    private String removeMovie = "DELETE movie_actor, m from movie_actor inner join movie m on movie_actor.movie_id = m.movie_id where m.name = ?";
    private String getGenre = "select * from genre where name = ?";


    public Movies(Connection cn) {
        this.connection = cn;
        this.movies = new ArrayList<>();
        getMovies();
    }

    public ArrayList<MovieBean> getMovies() {
        this.movies = new ArrayList<>();
        try (PreparedStatement myQry = this.connection.prepareStatement(selectAllMovies)) {
            runQuery(myQry);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

        return this.movies;
    }

    public int addMovie(String name, String genre, int releaseDate) {
        String qry = addMovie;
        int genreId = -1;
        int movieId = -1;

        try (PreparedStatement myQry = this.connection.prepareStatement(getGenre)) {
            myQry.setString(1, genre);

            ResultSet resultSet = myQry.executeQuery();
            if (resultSet.next()) {
                genreId = resultSet.getInt("genre_id");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

        int count = -1;
        try (PreparedStatement myQry = this.connection.prepareStatement(qry)) {
            myQry.setString(1, name);
            myQry.setString(2, genre);
            myQry.setInt(3, releaseDate);

            myQry.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        try (PreparedStatement myQry = this.connection.prepareStatement("select * from movie where name = ?")) {
            myQry.setString(1, name);

            ResultSet resultSet = myQry.executeQuery();
            if (resultSet.next()) {
                movieId = resultSet.getInt("movie_id");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        try (PreparedStatement myQry = this.connection.prepareStatement(addMovieGenre)) {
            myQry.setInt(1, movieId);
            myQry.setInt(2, genreId);

            myQry.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return count;
    }

    public int removeMovie(String name) {
        String qry = removeMovie;

        int count = -1;
        try (PreparedStatement myQry = this.connection.prepareStatement(qry)) {
            myQry.setString(1, name);

            count = myQry.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return count;
    }

    public String toJson() {
        String beansContent = "[";
        for (MovieBean movieBean : this.movies) {
            beansContent += movieBean.toJson() + ",";
        }
        String lastCharacter = beansContent.substring(beansContent.length() - 1);
        if (lastCharacter.equals(",")) {
            beansContent = beansContent.substring(0, beansContent.lastIndexOf(","));
        }
        beansContent += "]";
        return beansContent;
    }

    private MovieBean buildMovie(ResultSet rs) {
        MovieBean movieBean = new MovieBean();

        try {
            movieBean.setName(rs.getString("movie_name"));
            movieBean.setGenre(rs.getString("genre_name"));
            movieBean.setLength(rs.getString("length"));
            movieBean.setReleaseDate(rs.getString("release_date"));
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

        return movieBean;
    }

    private void buildMovies(ResultSet rs) throws SQLException {
        while (rs.next()) {
            this.movies.add(buildMovie(rs));
        }
    }

    private void runQuery(PreparedStatement query) {
        try (ResultSet rs = query.executeQuery()) {
            buildMovies(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

    }
}