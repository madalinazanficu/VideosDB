package entities;

import actor.ActorsAwards;
import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;

import java.util.*;

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
        this.nrRatings = Integer.valueOf(0);
    }


    public User(String username, String subscription, Map<String, Integer> history, List<String> favorite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        incrementViewsUser(history);
        this.favorite = favorite;
        incrementFavoriteUser(favorite);
        this.ratedMovies = new HashMap<Movie, Double>();
        this.ratedShows = new HashMap<>();
        this.nrRatings = Integer.valueOf(0);
    }
    public User(String username, String subscription, Map<String, Integer> history, List<String> favorite, Integer nrRatings) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        incrementViewsUser(history);
        this.favorite = favorite;
        incrementFavoriteUser(favorite);
        this.ratedMovies = new HashMap<Movie, Double>();
        this.ratedShows = new HashMap<>();
        this.nrRatings = nrRatings;
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

    public Integer getNrRatings() {
        return this.nrRatings;
    }


    // increment the number of views for each video from an user history (given directly from input)
    public void incrementViewsUser(Map<String, Integer> history) {
        // iterate in user's history
        Iterator<Map.Entry<String, Integer>> iterator = history.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            // every video from history is the key from history map
            Video specificVideo = VideosDB.getInstance().getSpecificVideo(entry.getKey());

            // if that video is valid (movie or serial) increment the views of that video
            if (specificVideo != null)
                specificVideo.incrementViews(entry.getValue());
        }
    }

    // increment the number of likes for each video from an user favorite list (given directly from input)
    public void incrementFavoriteUser(List<String> favorite) {

        // iterate in user's favorite list
        for (String fav : favorite) {
            Video specificVideo = VideosDB.getInstance().getSpecificVideo(fav);

            // for every valid video increment the favorite number of that video
            if (specificVideo != null) {
                    specificVideo.incrementFavorite(1);
            }
        }
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

        // increment the number of users that add this video at favorite
        Video specificVideo = VideosDB.getInstance().getSpecificVideo(newFavoriteVideo);
        specificVideo.incrementFavorite(1);

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

        // increment the number of views of the specificVideo
        Video specificVideo = VideosDB.getInstance().getSpecificVideo(newHistoryVideo);
        specificVideo.incrementViews(1);

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
            this.nrRatings++;

            specificMovie.addRating(rating);
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

        // Second case => the specificSerial has not been rated or seasonNumber has not been already rated
        if (this.ratedShows.get(specificSerial) == null || this.ratedShows.get(specificSerial).get(seasonNumber) == null) {

            // Serialul nu a primit niciun rating pana acum => noua intrare in hasmap
            HashMap<Integer, Double> entry;
            if (this.ratedShows.get(specificSerial) == null) {
                entry = new HashMap<>();
                entry.put(seasonNumber, rating);
            } else {
                // Serialul a mai primit rating, dar sezonul curent nu
                entry = this.ratedShows.get(specificSerial);
                entry.put(seasonNumber, rating);
            }

            this.ratedShows.put(specificSerial, entry);
            this.nrRatings++;

            // RATE THE SEASON => every serial has a list of sesons, every season has a list of ratings
            List <Double> ratings = specificSerial.getAllSeasons().get(seasonNumber - 1).getRatings();
            ratings.add(rating);
            specificSerial.getAllSeasons().get(seasonNumber - 1).setRatings(ratings);

            return "success -> " + specificSerial.getTitle() + " was rated with "
                    + rating + " by " + this.username;
        }

        // Third case => the seasonNumber has already been rated
        return "error -> " + specificSerial.getTitle() + " has been already rated";
    }
}
