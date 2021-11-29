package actions;

import databases.UsersDB;
import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;
import entities.User;
import fileio.ActionInputData;


public final class Commands {
    private final ActionInputData command;
    public Commands(final ActionInputData command) {
        this.command = command;
    }

    public ActionInputData getCommand() {
        return command;
    }

    /**
     * Method used for getting the command type
     * @return the output message
     */
    public String executeCommand() {
        if (this.getCommand().getType().equals("favorite")) {
            // instance where to add / in which user to add (by name) / what to add
            return favoriteCommand(UsersDB.getInstance(), this.getCommand().getUsername(),
                                    this.getCommand().getTitle());
        }
        if (this.getCommand().getType().equals("view")) {
            // instance where to add/ in which user to add (by name) / what to add
            return viewCommand(UsersDB.getInstance(), this.getCommand().getUsername(),
                                this.getCommand().getTitle());
        }
        if (this.getCommand().getType().equals("rating")) {
            return ratingCommand(UsersDB.getInstance(), this.getCommand());
        }
        return "Invalid command";
    }

    /**
     * Method used for adding a new favorite video for a specific user
     * Call addFavoriteVideo from user class to add the video in favorite list
     * @param users - the user database to extract the specific user
     * @param username - the username of the specific user
     * @param newFavoriteVideo - the new favorite video of the user
     * @return the output message
     */
    public String favoriteCommand(final UsersDB users, final String username,
                                  final String newFavoriteVideo) {

        User specificUser = users.getSpecificUser(username);
        if (specificUser == null) {
            return "user not found";
        }
        return specificUser.addNewFavoriteVideo(newFavoriteVideo);
    }

    /**
     * Method used for adding a new video in the history of a specific user
     * Call addToHistory from user class to add the video in user's history map
     * @param users - the user database to extract the specific user
     * @param username - the username of the specific user
     * @param newHistoryVideo - the viewd video
     * @return the output message
     */
    public String viewCommand(final UsersDB users, final String username,
                              final String newHistoryVideo) {

        User specificUser = users.getSpecificUser(username);
        if (specificUser == null) {
            return "user not found";
        }
        return specificUser.addToHistory(newHistoryVideo);
    }

    /**
     * Method used for rating a video for a specific user
     * Depends on videoType - movie or serial - computed the rating in different ways
     * @param users - the user database to extract the specific user
     * @param data - data from input to extract attributes such as: username and seasonNumber
     * @return the output message
     */
    public String ratingCommand(final UsersDB users, final ActionInputData data) {

        String username = data.getUsername();
        Double rating = data.getGrade();
        String rateVideo = data.getTitle();
        Integer seasonNumber = data.getSeasonNumber();

        User specificUser = users.getSpecificUser(username);
        if (specificUser == null) {
            return "user not found";
        }
        // the videoType - movie or serial -
        String type = VideosDB.getInstance().videoType(rateVideo);

        // rating for movie
        if (type.equals("Movie")) {
            Movie specificMovie = VideosDB.getInstance().getSpecificMovie(rateVideo);
            return specificUser.rateMovie(specificMovie, rating);
        } else {
            // rating for serial
            if (type.equals("Serial")) {
                Serial specificSerial = VideosDB.getInstance().getSpecificSerial(rateVideo);
                return specificUser.rateSerial(specificSerial, rating, seasonNumber);
            }
        }
        return "Error in ratingCommand";
    }
}
