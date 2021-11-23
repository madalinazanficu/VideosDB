package entertainment;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Video {

    // number of seasons
    private Integer numberofSeasons;

    // the list of seasons
    private List<Season> allSeasons;

    // total Duration of a serial
    private Integer duration;


    public Serial (String title, ArrayList<String> genres, Integer productionYear, List<Season> allSeasons, Integer numberofSeasons, ArrayList<String> cast) {
        super(title, genres, productionYear, cast);
        this.allSeasons = allSeasons;
        this.numberofSeasons = numberofSeasons;
        computeDuration();
    }

    public Serial (String title, ArrayList<String> genres, Integer productionYear, List<Season> allSeasons, Integer numberofSeasons, ArrayList<String> cast, Double averageRating, Integer numberViews, Integer numberFavorite) {
        super(title, genres, productionYear, cast, averageRating, numberViews, numberFavorite);
        this.allSeasons = allSeasons;
        this.numberofSeasons = numberofSeasons;
        computeDuration();
    }
    public Serial (String title, ArrayList<String> genres, Integer productionYear, List<Season> allSeasons, Integer numberofSeasons, ArrayList<String> cast, Double averageRating, Integer numberViews, Integer numberFavorite, Integer index) {
        super(title, genres, productionYear, cast, averageRating, numberViews, numberFavorite, index);
        this.allSeasons = allSeasons;
        this.numberofSeasons = numberofSeasons;
        computeDuration();
    }

    // copy-constructor
//    public Serial(Serial assign) {
//        super(assign.getTitle(), assign.getGenre(), assign.getProductionYear(), assign.getCast(), assign.getAverageRating(), assign.getNumberViews(), assign.getNumberFavorite());
//        this.allSeasons = assign.allSeasons;
//        this.numberofSeasons = assign.numberofSeasons;
//        computeDuration();
//    }

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
    public Double getAverageRating() {return averageRating;}

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
        if (totalSum != 0)
            this.averageRating = totalSum / this.numberofSeasons;
        else
            this.averageRating = 0.0;
    }
    public void computeDuration() {
        Integer duration = 0;
        for(Season season : allSeasons) {
            duration += season.getDuration();
        }
        this.duration = duration;
    }

}
