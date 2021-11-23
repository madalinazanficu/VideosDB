package entertainment;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    // how longest a movie is
    private Integer duration;

    // list of rating from all users
    private List<Double> ratings;

    public Movie(String title, ArrayList<String> genres, Integer productionYear, Integer duration, ArrayList<String> cast) {
        super(title, genres, productionYear, cast);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    public Movie(String title, ArrayList<String> genres, Integer productionYear, Integer duration, ArrayList<String> cast, Double averageRating, Integer numberViews, Integer numberFavorite) {
        super(title, genres, productionYear, cast, averageRating, numberViews, numberFavorite);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }
    // copy-constructor
    public Movie(Movie assign) {
        super(assign.getTitle(), assign.getGenre(), assign.getProductionYear(), assign.getCast(), assign.getAverageRating(), assign.getNumberViews(), assign.getNumberFavorite());
        this.duration = assign.getDuration();
        this.ratings = new ArrayList<>();

    }

    // getters for all atributes
    public Integer getTime() {
        return this.duration;
    }

    // add rating from a user
    public void addRating(Double rating) {
        this.ratings.add(rating);
    }

    public Integer getDuration() {
        return duration;
    }

    // compute the average rating
    public void computeAverageRating() {
        Double sum = 0.0;
        int num = 0;
        for (Double rating : ratings) {
            sum += rating;
            num += 1;
        }
        if (num != 0)
            this.averageRating = sum / num;
        else
            this.averageRating = 0.0;
    }

}
