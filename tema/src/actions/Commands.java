package actions;

import databases.UsersDB;
import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;
import entities.User;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

// commands could be: favorite, view or rating
// Scopul clasei este de a vedea ce tip de comanda este in executecommand si de a o executa!
// Se va returna un string care va desemna outputMessage

public class Commands {

    public String executeCommand(ActionInputData command) {
        if (command.getType().equals("favorite")) {
            // instance where to add / in which user to add (by name) / what to add
            return favoriteCommand(UsersDB.getInstance(), command.getUsername(), command.getTitle());
        }
        if (command.getType().equals("view")) {
            // instance where to add/ in which user to add (by name) / what to add
            return viewCommand(UsersDB.getInstance(), command.getUsername(), command.getTitle());
        }
        if (command.getType().equals("rating")) {
           return ratingCommand(UsersDB.getInstance(), command);
        }
        return "Invalid command";
    }

    // add the movie/ serial to the favorite list of the user
    public String favoriteCommand(UsersDB users, String username, String newFavoriteVideo) {

        User specificUser = users.getSpecificUser(username);
        if (specificUser == null) {
            return "user not found";
        }
        return specificUser.addNewFavoriteVideo(newFavoriteVideo);
    }

    public String viewCommand(UsersDB users, String username, String newHistoryVideo) {

        User specificUser = users.getSpecificUser(username);
        if (specificUser == null) {
            return "user not found";
        }
        return specificUser.addToHistory(newHistoryVideo);
    }

    public String ratingCommand(UsersDB users, ActionInputData data) {

        String username = data.getUsername();
        Double rating = data.getGrade();
        String rateVideo = data.getTitle();
        Integer seasonNumber = data.getSeasonNumber();

        User specificUser = users.getSpecificUser(username);
        if (specificUser == null) {
            return "user not found";
        }
        // is a movie or a serial?
        String VideoType = VideosDB.getInstance().videoType(rateVideo);

        // rating for movie
        if (VideoType.equals("Movie")) {
            Movie specificMovie = VideosDB.getInstance().getSpecificMovie(rateVideo);
            return specificUser.rateMovie(specificMovie, rating);

        } else {

            // rating for serial
            if (VideoType.equals("Serial")) {
                Serial specificSerial = VideosDB.getInstance().getSpecificSerial(rateVideo);
                return specificUser.rateSerial(specificSerial, rating, seasonNumber);
            }
        }
        return "Error in ratingCommand";
    }
}
