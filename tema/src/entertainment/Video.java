package entertainment;

import java.util.ArrayList;

public class Video {
    // every video has a title
    private final String title;
    // every video has more types from Genre enums
    private final ArrayList<String> genres;
    // every video has a productionYear
    private final Integer productionYear;
    // every video has a cast of actors
    private final ArrayList<String> cast;
    // every video has an average rating
    protected Double averageRating;
    // every video has a number of views
    private Integer numberViews;
    // every video has a number of users that add it at favorite
    private Integer numberFavorite;
    // every video has a index in database
    private Integer index;

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

    public Video(final Video assign) {
        this.title = assign.getTitle();
        this.genres = assign.getGenre();
        this.productionYear = assign.getProductionYear();
        this.cast = assign.getCast();
        this.averageRating = assign.getAverageRating();
        this.numberViews = assign.getNumberViews();
        this.numberFavorite = assign.getNumberFavorite();
        this.index = assign.getIndex();
    }

    /**
     * @return the title of video
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the list of genre of video
     */
    public ArrayList<String> getGenre() {
        return genres;
    }

    /**
     * @return the production year of video
     */
    public Integer getProductionYear() {
        return productionYear;
    }

    /**
     * @return list of actors that played in video
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * @return the averageRating of video
     */
    public Double getAverageRating() {
        return this.averageRating;
    }

    /**
     * @return the number of views of video
     */
    public Integer getNumberViews() {
        return this.numberViews;
    }

    /**
     * @return the number of "like" of video
     */
    public Integer getNumberFavorite() {
        return this.numberFavorite;
    }

    /**
     * @return the index from the videos' database for video
     */
    public Integer getIndex() {
        return this.index;
    }
    /**
     * Method used for incrementing the number of views of video
     * @param nrViews - the increment size
     */
    public void incrementViews(final Integer nrViews) {
        this.numberViews += nrViews;
    }

    /**
     * Method used for incrementing the number of favorite ("likes") for video
     * @param nrFavorite - the increment size
     */
    public void incrementFavorite(final Integer nrFavorite) {
        this.numberFavorite += nrFavorite;
    }
}
