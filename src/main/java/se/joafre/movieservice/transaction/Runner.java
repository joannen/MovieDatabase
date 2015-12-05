package se.joafre.movieservice.transaction;

import se.joafre.movieservice.model.Actor;
import se.joafre.movieservice.transaction.model.Genre;

import java.sql.SQLException;

/**
 * Created indirectly by the universe.
 */
public final class Runner {

    public static void main(String[] args) {

        Movie m = new Movie("flapflap", 2015, 2);
        Actor a = new Actor("Fredrik", "Höllinger");
        Genre g = new Genre("Gore");

        System.out.println("non-persisted movie: " + m);
        System.out.println("non-persisted actor: " + a);
        System.out.println("non-persisted genre: " + g);

        try {
            Movie movieResult = Database.persist(m);
            Actor actorResult = Database.persist(a);
            Genre genreResult = Database.persist(g);
            System.out.println("persisted actor: " + actorResult);
            System.out.println("persisted movie: " + actorResult);
            System.out.println("persisted genre: " + genreResult);

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
