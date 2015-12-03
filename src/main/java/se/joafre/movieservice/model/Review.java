package se.joafre.movieservice.model;

/**
 * Created by joanne on 02/12/15.


 CREATE TABLE Review(
 id int primary key auto_increment,
 comment text,
 grade int(11) not null,
 movieId int not null,
 reviewerId int not null
 );*/
public final class Review {

    private final int id;
    private final String comment;
    private final int grade;
    private final int movieId;
    private final int reviewerId;

    public Review(int id, String comment, int grade, int movieId, int reviewerId) {
        this.id = id;
        this.comment = comment;
        this.grade = grade;
        this.movieId = movieId;
        this.reviewerId = reviewerId;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public int getGrade() {
        return grade;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getReviewerId() {
        return reviewerId;
    }
}
