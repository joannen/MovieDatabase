package se.joafre.movieservice.model;

/**
 * Created by joanne on 02/12/15.
 */
public final class Movie {

    private final int id;
    private final String title;
    private final int productionYear;
    private final String genre;

    public Movie(int id, String title, int productionYear, String genre) {
        this.id = id;
        this.title = title;
        this.productionYear = productionYear;
        this.genre = genre;
    }

    public Movie(String title, int productionYear, String genre) {
        this.id = -1;
        this.title = title;
        this.productionYear = productionYear;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", productionYear=" + productionYear +
                ", genre='" + genre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (getProductionYear() != movie.getProductionYear()) return false;
        if (getTitle() != null ? !getTitle().equalsIgnoreCase(movie.getTitle()) : movie.getTitle() != null) return false;
        return !(getGenre() != null ? !getGenre().equalsIgnoreCase(movie.getGenre()) : movie.getGenre() != null);

    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + getProductionYear();
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        return result;
    }
}
