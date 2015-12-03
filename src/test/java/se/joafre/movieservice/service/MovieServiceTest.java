package se.joafre.movieservice.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.joafre.movieservice.model.Movie;
import se.joafre.movieservice.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by joanne on 02/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @Rule
    public ExpectedException expector = ExpectedException.none();

    @Mock
    private MovieRepository movieRepositoryMock;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;
    private List<Movie> movieList;

    @Before
    public void setup()
    {
        movie = new Movie(1, "Back to the Future", 1985, "action");
        movieList = new ArrayList<>();
    }



    @org.junit.Test
    public void testGetAll() throws Exception {
        movieList.add(movie);
        when(movieRepositoryMock.getAllMovies()).thenReturn(movieList);

        assertThat(movieService.getAllMovies(), equalTo(movieList));

        verify(movieRepositoryMock).getAllMovies();
    }

    @org.junit.Test
    public void testPersist() throws Exception {
       movieList.add(movie);
        movieService.persist(movie);
       when(movieRepositoryMock.getAllMovies()).thenReturn(movieList);

        assertThat(movieService.getAllMovies(), equalTo(movieList));

        verify(movieRepositoryMock).persistMovie(movie);
        verify(movieRepositoryMock).getAllMovies();
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        when(movieRepositoryMock.updateMovie(movie)).thenReturn(1);

        assertThat(movieService.update(movie), equalTo(1));

        verify(movieRepositoryMock).updateMovie(movie);
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        when(movieRepositoryMock.deleteMovie(1)).thenReturn(1);
        assertThat(movieService.delete(1), equalTo(1));
        verify(movieRepositoryMock).deleteMovie(1);
    }
}