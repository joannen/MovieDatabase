package se.joafre.movieservice.model;

/**
 * Created by joanne on 02/12/15.
 */
public final class Actor {

    private final int id;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public Actor(int id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }
}
