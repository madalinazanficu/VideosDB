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

    // getters for all atributes
    public Integer getTime() {
        return this.duration;
    }

}
