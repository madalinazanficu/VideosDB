package entertainment;

import java.util.ArrayList;
import java.util.Objects;

public class Video {
    // every video has a title
    private String title;
    // every video has more types from Genre enums
    private ArrayList<String> genres;
    // every video has a productionYear
    private Integer productionYear;
    // every video has a cast of actors
    private ArrayList<String> cast;
    // every video has an average rating
    public Double averageRating;
    // every video has a number of views
    public Integer numberViews;
    // every video has a number of users that add it at favorite
    public Integer numberFavorite;
    // every video has a index in database
    public Integer index;

    public Video() {
        this.title = null;
        this.genres = null;
        this.productionYear = null;
        this.cast = null;
        this.averageRating = 0.0;
        this.numberViews = 0;
        this.numberFavorite = 0;
    }
    public Video(final String title, final ArrayList<String> genres, final Integer productionYear,
                 final ArrayList<String> cast) {

        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
        this.averageRating = 0.0;
        this.numberViews = 0;
        this.numberFavorite = 0;
    }

    public Video(final String title, final ArrayList<String> genres, final Integer productionYear,
                 final ArrayList<String> cast, final Double averageRating,
                 final Integer numberViews, final Integer numberFavorite) {

        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
        this.averageRating = averageRating;
        this.numberViews = numberViews;
        this.numberFavorite = numberFavorite;
    }
    public Video(final String title, final ArrayList<String> genres, final Integer productionYear,
                 final ArrayList<String> cast, final Double averageRating,
                 final Integer numberViews, final Integer numberFavorite, final Integer index) {

        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
        this.averageRating = averageRating;
        this.numberViews = numberViews;
        this.numberFavorite = numberFavorite;
        this.index = index;
    }

    // Copy Constructor
    public Video(Video assign) {
        this.title = assign.getTitle();
        this.genres = assign.getGenre();
        this.productionYear = assign.getProductionYear();
        this.cast = assign.getCast();
        this.averageRating = assign.getAverageRating();
        this.numberViews = assign.getNumberViews();
        this.numberFavorite = assign.getNumberFavorite();
        this.index = assign.getIndex();

    }

    // getters for every attribute

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return
     */
    public ArrayList<String> getGenre() {
        return genres;
    }

    /**
     * @return
     */
    public Integer getProductionYear() {
        return productionYear;
    }

    /**
     * @return
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * @return
     */
    public Double getAverageRating() {
        return this.averageRating;
    }

    /**
     * @return
     */
    public Integer getNumberViews() {
        return this.numberViews;
    }

    /**
     * @return
     */
    public Integer getNumberFavorite() {
        return this.numberFavorite;
    }

    /**
     * @return
     */
    public Integer getIndex() {
        return this.index;
    }

    /**
     * @param title
     */
    // setters for every attribute
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @param genres
     */
    public void setGenre(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * @param cast
     */
    public void setCast(final ArrayList<String> cast) {
        this.cast = cast;
    }

    /**
     * @param numberViews
     */
    // increment the number of views
    public void incrementViews(final Integer numberViews) {
        this.numberViews += numberViews;
    }

    /**
     * @param numberFavorite
     */
    // increment the number of favorite
    public void incrementFavorite(final Integer numberFavorite) {
        this.numberFavorite += numberFavorite;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Video{" +
                "title='" + title + '\'' +
                ", genre=" + genres +
                ", productionYear=" + productionYear +
                '}';
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genres, productionYear, cast, averageRating,
                            numberViews, numberFavorite, index);
    }
}
