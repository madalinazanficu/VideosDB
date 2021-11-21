package entertainment;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Video {

    // number of seasons
    private Integer numberofSeasons;

    // the list of seasons
    private List<Season> allSeasons;

    // list of average rating of all seasons for the Show
   // private List<Double> averageRating;

    public Serial (String title, ArrayList<String> genres, Integer productionYear, List<Season> allSeasons, Integer numberofSeasons, ArrayList<String> cast) {
        super(title, genres, productionYear, cast);
        this.allSeasons = allSeasons;
        this.numberofSeasons = numberofSeasons;
        // this.averageRating = new ArrayList<Double>();
    }

    // getters for all attributes
    public List<Season> getAllSeasons() {
        return allSeasons;
    }

    public Integer getNumberofSeasons() {
        return numberofSeasons;
    }
}
