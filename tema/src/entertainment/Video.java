package entertainment;

import java.util.ArrayList;

public class Video {
    // every video has a title
    private String title;
    // every video has more types from Genre enums
    private ArrayList<String> genres;
    // every video has a productionYear
    private Integer productionYear;
    // every video has a cast of actors
    private ArrayList<String> cast;

    public Video() {
        this.title = null;
        this.genres = null;
        this.productionYear = null;
        this.cast = null;

    }
    public Video(String title, ArrayList<String> genres, Integer productionYear, ArrayList<String> cast) {
        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
    }

    // getters for every attribute
    public String getTitle() {
        return title;
    }

    public ArrayList<String> getGenre() {
        return genres;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    // setters for every attribute
    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    @Override
    public String toString() {
        return "Video{" +
                "title='" + title + '\'' +
                ", genre=" + genres +
                ", productionYear=" + productionYear +
                '}';
    }
}
