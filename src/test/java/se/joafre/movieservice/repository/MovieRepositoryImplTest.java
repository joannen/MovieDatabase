package se.joafre.movieservice.repository;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import se.joafre.movieservice.model.Movie;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * Created indirectly by the universe.
 */

@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryImplTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Movie movie;
    private MovieRepositoryImpl movieRepository = new MovieRepositoryImpl();

    @Before
    public void setup() {
        movie = new Movie("Snap at the tutor", 1965, "Action");
    }

    @Test
    public void addedMovieIsRetrievable() {
        movieRepository.persistMovie(movie);
        assertTrue(movieRepository.getAllMovies().contains(movie));
    }


}