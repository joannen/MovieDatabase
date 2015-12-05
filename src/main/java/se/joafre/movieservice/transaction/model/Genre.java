package se.joafre.movieservice.transaction.model;

/**
 * Created indirectly by the universe.
 */
public final class Genre {

    private final String genreName;
    private final int genreId;

    public Genre(int genreId, String genreName) {
        this.genreName = genreName;
        this.genreId = genreId;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
        this.genreId = -1;
    }

    public String getGenreName() {
        return genreName;
    }

    public int getGenreId() {
        return genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return !(getGenreName() != null ? !getGenreName().equals(genre.getGenreName()) : genre.getGenreName() != null);

    }

    @Override
    public int hashCode() {
        return getGenreName() != null ? getGenreName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreName='" + genreName + '\'' +
                ", genreId=" + genreId +
                '}';
    }
}
