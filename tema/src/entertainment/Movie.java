package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    // how longest a movie is
    private Integer duration;

    // list of rating from all users
    private List<Double> ratings;

    public Movie(final String title, final ArrayList<String> genres, final Integer productionYear,
                 final Integer duration, final ArrayList<String> cast) {
        super(title, genres, productionYear, cast);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    public Movie(final String title, final ArrayList<String> genres, final Integer productionYear,
                 final Integer duration, final ArrayList<String> cast, final Double averageRating,
                 final Integer numberViews, final Integer numberFavorite) {

        super(title, genres, productionYear, cast, averageRating, numberViews, numberFavorite);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    public Movie(final String title, final ArrayList<String> genres, final Integer productionYear,
                 final Integer duration, final ArrayList<String> cast, final Double averageRating,
                 final Integer numberViews, final Integer numberFavorite, final Integer index) {

        super(title, genres, productionYear, cast, averageRating,
                numberViews, numberFavorite, index);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    // copy-constructor
    public Movie(final Movie assign) {
        super(assign.getTitle(), assign.getGenre(), assign.getProductionYear(), assign.getCast(),
                assign.getAverageRating(), assign.getNumberViews(),
                assign.getNumberFavorite(), assign.getIndex());

        this.duration = assign.getDuration();
        this.ratings = new ArrayList<>();
    }

    // getters for all atributes
    public Integer getTime() {
        return this.duration;
    }

    /**
     * @param rating
     */
    // add rating from a user
    public void addRating(final Double rating) {
        this.ratings.add(rating);
    }

    public Integer getDuration() {
        return duration;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    /**
     *
     */
    // compute the average rating
    public void computeAverageRating() {
        Double sum = 0.0;
        int num = 0;
        for (Double rating : ratings) {
            sum += rating;
            num += 1;
        }
        if (num != 0) {
            this.averageRating = sum / num;
        } else {
            this.averageRating = 0.0;
        }
    }

}
