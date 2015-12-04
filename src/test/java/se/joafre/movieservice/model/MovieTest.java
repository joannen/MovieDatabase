package se.joafre.movieservice.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by joanne on 04/12/15.
 */
public class MovieTest {

    private Movie movie1;
    private Movie movie2;

    @Before
    public void setup(){
        movie1 = new Movie(1, "abc", 1990, "action");
        movie2 = new Movie(2, "abc", 1990, "Action");

    }

    @Test
    public void equalsTest(){
        assertThat(movie1, equalTo(movie2));
    }

    public void hashCodeTest(){
        assertThat(movie1.hashCode(), equalTo(movie2.hashCode()));
    }

}