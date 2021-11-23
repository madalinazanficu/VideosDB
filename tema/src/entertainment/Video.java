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
    public Video(String title, ArrayList<String> genres, Integer productionYear, ArrayList<String> cast) {
        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
        this.averageRating = 0.0;
        this.numberViews = 0;
        this.numberFavorite = 0;
    }

    public Video(String title, ArrayList<String> genres, Integer productionYear, ArrayList<String> cast, Double averageRating, Integer numberViews, Integer numberFavorite) {
        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
        this.averageRating = averageRating;
        this.numberViews = numberViews;
        this.numberFavorite = numberFavorite;
    }
    public Video(String title, ArrayList<String> genres, Integer productionYear, ArrayList<String> cast, Double averageRating, Integer numberViews, Integer numberFavorite, Integer index) {
        this.title = title;
        this.genres = genres;
        this.productionYear = productionYear;
        this.cast = cast;
        this.averageRating = averageRating;
        this.numberViews = numberViews;
        this.numberFavorite = numberFavorite;
        this.index = index;
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

    public Double getAverageRating() {
        return this.averageRating;
    }

    public Integer getNumberViews() {return this.numberViews;}

    public Integer getNumberFavorite() {return this.numberFavorite;}

    public Integer getIndex() {return this.index;}

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

    // increment the number of views
    public void incrementViews(Integer numberViews) {
        this.numberViews += numberViews;
    }
    // increment the number of favorite
    public void incrementFavorite(Integer numberFavorite) {
        this.numberFavorite += numberFavorite;
    }

    @Override
    public String toString() {
        return "Video{" +
                "title='" + title + '\'' +
                ", genre=" + genres +
                ", productionYear=" + productionYear +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
