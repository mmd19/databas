package javasql;

import beans.ActorBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Actors {
    private Connection connection;
    private ArrayList<ActorBean> actors;

    private String selectAllActors = "select * from actor";
    private String updateActor = "UPDATE actor SET country = ? WHERE name = ?;";

    public Actors(Connection cn) {
        this.connection = cn;
        this.actors = new ArrayList<>();
        getActors();
    }

    public ArrayList<ActorBean> getActors() {
        this.actors = new ArrayList<>();
        try (PreparedStatement myQry = this.connection.prepareStatement(selectAllActors)) {
            runQuery(myQry);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

        return this.actors;
    }

    public ArrayList<ActorBean> getMainActors() {
        this.actors = new ArrayList<>();
        try (PreparedStatement myQry = this.connection.prepareStatement(selectAllActors)) {
            runQuery(myQry);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

        return this.actors;
    }

    public int updateActors(String name, String newCountry) {
        String qry = updateActor;

        int count = -1;
        try (PreparedStatement myQry = this.connection.prepareStatement(qry)) {
            myQry.setString(1, newCountry);
            myQry.setString(2, name);

            count = myQry.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return count;
    }

    public String toJson() {
        String beansContent = "[";
        for (ActorBean ab : this.actors) {
            beansContent += ab.toJson() + ",";
        }
        String lastCharacter = beansContent.substring(beansContent.length() - 1);
        if (lastCharacter.equals(",")) {
            beansContent = beansContent.substring(0, beansContent.lastIndexOf(","));
        }
        beansContent += "]";
        return beansContent;
    }

    private ActorBean buildActor(ResultSet rs) {
        ActorBean actorBean = new ActorBean();

        try {
            actorBean.setId(rs.getInt("actor_id"));
            actorBean.setName(rs.getString("name"));
            actorBean.setCountry(rs.getString("country"));
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

        return actorBean;
    }

    private void buildActors(ResultSet rs) throws SQLException {
        while (rs.next()) {
            this.actors.add(buildActor(rs));
        }
    }

    private void runQuery(PreparedStatement query) {
        try (ResultSet rs = query.executeQuery()) {
            buildActors(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }

    }
}