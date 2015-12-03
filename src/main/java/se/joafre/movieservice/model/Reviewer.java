package se.joafre.movieservice.model;

/**
 * Created by joanne on 02/12/15.
 *
 * CREATE TABLE Reviewer(
 id int primary key auto_increment,
 username varchar(200)
 );
 */
public final class Reviewer {

    private final int id;
    private final String username;

    public Reviewer(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
