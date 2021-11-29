package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Serial extends Video {

    // number of seasons
    private final Integer numberofSeasons;

    // the list of seasons
    private final List<Season> allSeasons;

    // total duration of serial
    private Integer duration;

    public Serial(final String title, final ArrayList<String> genres, final Integer productionYear,
                  final List<Season> allSeasons, final Integer numberofSeasons,
                  final ArrayList<String> cast, final Double averageRating,
                  final Integer numberViews, final Integer numberFavorite, final Integer index) {

        super(title, genres, productionYear, cast, averageRating,
                numberViews, numberFavorite, index);
        this.allSeasons = allSeasons;
        this.numberofSeasons = numberofSeasons;
        computeDuration();
    }

    public Serial(final Serial assign) {
        super(assign.getTitle(), assign.getGenre(), assign.getProductionYear(), assign.getCast(),
                assign.getAverageRating(), assign.getNumberViews(),
                assign.getNumberFavorite(), assign.getIndex());

        this.allSeasons = assign.getAllSeasons();
        this.numberofSeasons = assign.getNumberofSeasons();
        computeDuration();
    }

    // getters for all attributes
    public List<Season> getAllSeasons() {
        return allSeasons;
    }

    public Integer getNumberofSeasons() {
        return numberofSeasons;
    }
    public Integer getDuration() {
        return this.duration;
    }
    public Double getAverageRating() {
        return averageRating;
    }

    /**
     * Method used for computing the average rating of a serial
     * The algorithm consist in iterating in list of season of the serial
     * Than iterating in the list of rating for every season and make the average
     */
    public void computeAverageRating() {
        Double totalSum = 0.0;

        // every serial has a list of seasons => iterate to compute the average
        for (Season season : allSeasons) {
            Double sum = 0.0;
            int nr = 0;
            // every season has a list of ratings from all the users
            for (Double rating : season.getRatings()) {
                sum += rating;
                nr += 1;
            }
            if (nr != 0) {
                totalSum += sum / nr;
            }
        }
        if (totalSum != 0) {
            this.averageRating = totalSum / this.numberofSeasons;
        } else {
            this.averageRating = 0.0;
        }
    }

    /**
     * Method used for computing the total duration of a serial
     */
    public void computeDuration() {
        Integer timer = 0;
        for (Season season : allSeasons) {
            timer += season.getDuration();
        }
        this.duration = timer;
    }

}
