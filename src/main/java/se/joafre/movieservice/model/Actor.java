package se.joafre.movieservice.model;

/**
 * Created by joanne on 02/12/15.
 */
public final class Actor {

    private final int id;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public Actor(String firstName, String middleName, String lastName) {
        this.id = -1;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Actor(int id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Actor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = middleName;
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

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (firstName != null ? !firstName.equals(actor.firstName) : actor.firstName != null) return false;
        if (middleName != null ? !middleName.equals(actor.middleName) : actor.middleName != null) return false;
        return !(lastName != null ? !lastName.equals(actor.lastName) : actor.lastName != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
