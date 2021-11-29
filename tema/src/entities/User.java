package entities;

import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;



public final class User {
    private final String username;

    // the types of subscription are: basic, premium
    private final String subscription;

    // the key is the name of the video, the value represents the number of visualisations
    private final Map<String, Integer> history;

    // user's favorite videos
    private final List<String> favorite;

    // user's number of rating
    private Integer nrRatings;

    // movies rated
    private final HashMap<String, Double> ratedMovies;

    // shows rated - contain the serial and a hashmap with seasons and their rating
    private final HashMap<String, HashMap<Integer, Double>> ratedShows;

    public User() {
        this.username = null;
        this.subscription = null;
        this.history = null;
        this.favorite = null;
        this.ratedMovies = null;
        this.ratedShows = null;
        this.nrRatings = 0;
    }

    public User(final User copyUser) {
        this.username = copyUser.getUsername();
        this.subscription = copyUser.getSubscription();
        this.history = copyUser.getHistory();
        this.favorite = copyUser.getFavorite();
        this.ratedMovies = copyUser.getRatedMovies();
        this.ratedShows = copyUser.getRatedShows();
        this.nrRatings = copyUser.getNrRatings();
    }

    public User(final String username, final String subscription, final Map<String,
                Integer> history, final List<String> favorite) {

        this.username = username;
        this.subscription = subscription;
        this.history = history;
        incrementViewsUser(history);
        this.favorite = favorite;
        incrementFavoriteUser(favorite);
        this.ratedMovies = new HashMap<String, Double>();
        this.ratedShows = new HashMap<String, HashMap<Integer, Double>>();
        this.nrRatings = 0;
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

    public HashMap<String, Double> getRatedMovies() {
        return ratedMovies;
    }

    public HashMap<String, HashMap<Integer, Double>> getRatedShows() {
        return ratedShows;
    }

    /**
     * Method used for incrementing the number of views for each video seen by the user
     * It is called in constructor, because user's history could contain videos directly from input
     * @param userHistory - the history of user
     */
    public void incrementViewsUser(final Map<String, Integer> userHistory) {
        // iterate in user's history
        Iterator<Map.Entry<String, Integer>> iterator = userHistory.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            // every video from history is the key from history map
            Video specificVideo = VideosDB.getInstance().getSpecificVideo(entry.getKey());

            // if that video is valid (movie or serial) increment the views of that video
            if (specificVideo != null) {
                specificVideo.incrementViews(entry.getValue());
            }
        }
    }

    /**
     * Method used for incrementing the number of favorite for each video saw by the user
     * It is called in constructor, because user's favoriteList could be given directly from input
     * @param userFavorite - the favorite list of user
     */
    public void incrementFavoriteUser(final List<String> userFavorite) {

        // iterate in user's favorite list
        for (String fav : userFavorite) {
            Video specificVideo = VideosDB.getInstance().getSpecificVideo(fav);

            // for every valid video increment the favorite number of that video
            if (specificVideo != null) {
                    specificVideo.incrementFavorite(1);
            }
        }
    }

    /**
     * Method used for searching a specific video in user's favorite list
     * @param searchFavorite - the specific video title
     * @return the video title if it is found or null otherwise
     */
    public String getFavoriteByName(final String searchFavorite) {
        for (String fav : favorite) {
            if (fav.equals(searchFavorite)) {
                return fav;
            }
        }
        return null;
    }

    /**
     * Method used for adding a new favorite movie in user's favorite list
     * It is called in Commands/favoriteCommand
     * @param newFavoriteVideo - the new favorite movie needed to be added
     * @return the output message - if the video is seen/ is already in favorite list/ was added
     */
    public String addNewFavoriteVideo(final String newFavoriteVideo) {
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
        // otherwise, add it Favorite list
        this.favorite.add(newFavoriteVideo);
        output = "success -> " + newFavoriteVideo + " was added as favourite";

        // increment the number of users that add this video at favorite
        Video specificVideo = VideosDB.getInstance().getSpecificVideo(newFavoriteVideo);
        specificVideo.incrementFavorite(1);

        // return it in Commands -> favoriteCommand
        return output;
    }

    /**
     * Method used for adding a new video is user's history
     * It is used in Commands/viewCommand
     * @param newHistoryVideo - the video needed to be added
     * @return the output message
     */
    public String addToHistory(final String newHistoryVideo) {

        // search in history the newHistoryVideo
        if (this.history.get(newHistoryVideo) == null) {
            this.history.put(newHistoryVideo, 1);
            Video specificVideo = VideosDB.getInstance().getSpecificVideo(newHistoryVideo);
            specificVideo.incrementViews(1);
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

    /**
     * Method used for rating a specific movie and adding the movie to rated movies map of user
     * @param specificMovie - the specific movie needed to be rated and added to the map
     * @param rating - the rating offered by the current user
     * @return the output message
     */
    public String rateMovie(final Movie specificMovie, final Double rating) {

        // First case => the movie has not been seen by the user => no rating allowed
        if (this.history.get(specificMovie.getTitle()) == null) {
            return "error -> " + specificMovie.getTitle() + " is not seen";
        }

        // Second case => the movie should not be already rated
        if (this.ratedMovies.get(specificMovie.getTitle()) == null) {
            this.ratedMovies.put(specificMovie.getTitle(), rating);
            this.nrRatings++;

            specificMovie.addRating(rating);
            return  "success -> " + specificMovie.getTitle() + " was rated with "
                    + rating + " by " + this.username;
        }

        // Third case => the movie has been already rated
        return "error -> " + specificMovie.getTitle() + " has been already rated";
    }

    /**
     * Method used for rating a specific serial and adding the serial to rated series map of user
     * The rating of the serial is done on seasons
     * @param specificSerial - the specific serial needed to be rated
     * @param rating - the rating offered by the current user
     * @param seasonNumber - the season number needed to be rated from the serial
     * @return the output message
     */
    public String rateSerial(final Serial specificSerial, final Double rating,
                             final Integer seasonNumber) {

        // First case => the serial has not been seen by the user => no rating allowed
        if (this.history.get(specificSerial.getTitle()) == null) {
            return "error -> " + specificSerial.getTitle() + " is not seen";
        }

        // The specificSerial has not been rated or seasonNumber has not been already rated
        if (this.ratedShows.get(specificSerial.getTitle()) == null
                || this.ratedShows.get(specificSerial.getTitle()).get(seasonNumber) == null) {

            // The specific serial has not been already rated => new hashmap entry
            HashMap<Integer, Double> entry;
            if (this.ratedShows.get(specificSerial.getTitle()) == null) {
                entry = new HashMap<>();
                entry.put(seasonNumber, rating);
            } else {
                // The specific serial has already been rated, but the specific season no
                entry = this.ratedShows.get(specificSerial.getTitle());
                entry.put(seasonNumber, rating);
            }

            this.ratedShows.put(specificSerial.getTitle(), entry);
            this.nrRatings++;

            // rete the season => every serial has a list of seasons
            // every season has a list of ratings
            List<Double> ratings = specificSerial.getAllSeasons()
                                  .get(seasonNumber - 1).getRatings();
            ratings.add(rating);
            specificSerial.getAllSeasons().get(seasonNumber - 1).setRatings(ratings);

            return "success -> " + specificSerial.getTitle() + " was rated with "
                    + rating + " by " + this.username;
        }

        // Third case => the seasonNumber has already been rated
        return "error -> " + specificSerial.getTitle() + " has been already rated";
    }

}
