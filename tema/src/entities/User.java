package entities;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String username;

    // the types of subscription are: basic, premium
    private String subscription;

    // the key is the name of the video, the value represents the number of visualisations
    private Map<String, Integer> history;

    // user's favorite videos
    private List<String> favorite;

    // user's number of rating
    private Integer nrRatings;

    // movies rated
    private HashMap<Movie, Double> ratedMovies;

    // shows rated - contain the serial and a hasmap with seasons and their rating
    private HashMap<Serial, HashMap<Integer, Double>> ratedShows;

    // all videos rated
    // private HashMap<Video, Integer> ratedVideos;

    // default constructor
    public User() {
        this.username = null;
        this.subscription = null;
        this.history = null;
        this.favorite = null;
        this.ratedMovies = null;
        this.ratedShows = null;
    }


    public User(String username, String subscription, Map<String, Integer> history, List<String> favorite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favorite = favorite;
        this.ratedMovies = new HashMap<Movie, Double>();
        this.ratedShows = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getSubscription() {
        return subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public String getFavoriteByName(String searchFavorite) {
        for (String fav : favorite) {
            if (fav.equals(searchFavorite)) {
                return fav;
            }
        }
        return null;
    }
    public String addNewFavoriteVideo(String newFavoriteVideo) {
        String output;
        // search in the history the newFavoriteVideo
        if (this.history.get(newFavoriteVideo) == null) {
            output = "error -> " + newFavoriteVideo + " is not seen";
            return output;
        }
        // if newFavoriteVideo is already in favorites video
        if (this.getFavoriteByName(newFavoriteVideo) != null) {
                output = "error -> " + newFavoriteVideo + " is already in favourite list";
                return output;
        }
        // otherwise => add it Favorite list
        this.favorite.add(newFavoriteVideo);
        output = "success -> " + newFavoriteVideo + " was added as favourite";

        // return it in Commands -> favoriteCommand
        return output;
    }

    public String addToHistory(String newHistoryVideo) {
        // search in history the newHistoryVideo
        // not found
        if (this.history.get(newHistoryVideo) == null) {
            this.history.put(newHistoryVideo, 1);
            return "success -> " + newHistoryVideo + " was viewed with total views of 1";
        }

        // increment the views of the newHistoryVideo
        Integer views = this.history.get(newHistoryVideo) + 1;
        this.history.replace(newHistoryVideo, views);
        return "success -> " + newHistoryVideo + " was viewed with total views of "
                + this.history.get(newHistoryVideo);
    }

    public String rateMovie(Movie specificMovie, Double rating) {

        // First case => the movie has not been seen by the user => no rating allowed
        if (this.history.get(specificMovie.getTitle()) == null) {
            return "error -> " + specificMovie.getTitle() + " is not seen";
        }

        // Second case => the movie should not be already rated
        if (this.ratedMovies.get(specificMovie) == null) {
            this.ratedMovies.put(specificMovie, rating);
            return  "success -> " + specificMovie.getTitle() + " was rated with "
                    + rating + " by " + this.username;
        }

        // Third case => the movie has been already rated
        return "error -> " + specificMovie.getTitle() + " has been already rated";
    }

    public String rateSerial(Serial specificSerial, Double rating, Integer seasonNumber) {

        // First case => the serial has not been seen by the user => no rating allowed
        if (this.history.get(specificSerial.getTitle()) == null) {
            return "error -> " + specificSerial.getTitle() + " is not seen";
        }

        // Second case => the seasonNumber has not been already rated
        if (this.ratedShows.get(specificSerial) == null) {
            // first position is the seasonNumber than his rating
            HashMap<Integer, Double> entry = new HashMap<>();
            entry.put(seasonNumber, rating);

            this.ratedShows.put(specificSerial, entry);
            return "success -> " + specificSerial.getTitle() + " was rated with "
                    + rating + " by " + this.username;
        }

        // Third case => the seasonNumber has already been rated
        return "error -> " + specificSerial.getTitle() + " has been already rated";
    }
}
