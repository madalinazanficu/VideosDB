package actions;

import databases.UsersDB;
import databases.VideosDB;
import entertainment.Movie;
import entertainment.Video;
import entities.User;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Recommendation {
    public static String executeRecommendation(ActionInputData recommendation) {

        String username = recommendation.getUsername();
        User specificUser = UsersDB.getInstance().getSpecificUser(username);
        if (specificUser == null) {
            return "User not found!";
        }
        if (recommendation.getType().equals("standard")) {
            return executeRecommendationStandard(recommendation, specificUser);
        }
        if (recommendation.getType().equals("best_unseen")) {
            return executeRecommendationBestUnseen(recommendation, specificUser);
        }
        if (recommendation.getType().equals("search")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                 List <Video> sortedList = executeRecommendationSearch(recommendation, specificUser);
                 return printSortedLists(sortedList);

            } else {
                return "SearchRecommendation cannot be applied!";
            }
        }
        if (recommendation.getType().equals("popular")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                return "null_popular";
            } else {
                return "PopularRecommendation cannot be applied!";
            }
        }
        if (recommendation.getType().equals("favorite")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                return executeRecommendationFavorite(recommendation, specificUser);
            } else {
                return "FavoriteRecommendation cannot be applied!";
            }
        }
        return "Not execute yet";
    }
    public static String executeRecommendationStandard(ActionInputData recommendation, User user) {
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();

        // iterate in all videos from Database
        for (Video video : allVideos) {

            // search the video that do not appear in user's history
            if (user.getHistory().get(video.getTitle()) == null) {
                return "StandardRecommendation result: " + video.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }
    public static String executeRecommendationBestUnseen(ActionInputData recommendation, User user) {
        List <Video> allVideos = VideosDB.instance.getAllVideos();
        List <Video> sortedVideos = new ArrayList<>();


        for (Video video : allVideos) {
            if (user.getHistory().get(video.getTitle()) == null) {
                sortedVideos.add(new Video(video.getTitle(), video.getGenre(), video.getProductionYear(), video.getCast(), video.getAverageRating(), video.getNumberViews(), video.getNumberFavorite(), video.getIndex()));
            }
        }
        if (sortedVideos.size() == 0) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        Collections.sort(sortedVideos, Comparator.comparing(Video::getAverageRating).reversed().thenComparing(Video::getIndex));
        return "BestRatedUnseenRecommendation result: " + sortedVideos.get(0).getTitle();
    }

    public static String executeRecommendationFavorite(ActionInputData recommendation, User specificUser) {
        List <Video> allVideos = VideosDB.getInstance().getAllVideos();
        List <Video> sortedVideos = new ArrayList<>();

        List<User> users = UsersDB.instance.getAllUsers();
        // iterate in video to find the most liked video
        for (Video video : allVideos) {
            if (specificUser.getHistory().get(video.getTitle()) == null) {
                if (video.getNumberFavorite() != 0) {
                    sortedVideos.add(new Video(video.getTitle(), video.getGenre(), video.getProductionYear(), video.getCast(), video.getAverageRating(), video.getNumberViews(), video.getNumberFavorite(), video.getIndex()));
                }
            }
        }
        if (sortedVideos.size() == 0) {
            return "FavoriteRecommendation cannot be applied!";
        }
        Collections.sort(sortedVideos, Comparator.comparing(Video::getNumberFavorite).reversed().thenComparing(Video::getIndex));
        return "FavoriteRecommendation result: " + sortedVideos.get(0).getTitle();

    }
    public static List<Video> executeRecommendationSearch(ActionInputData recommendation, User specificUser) {
        List <Video> allVideos = VideosDB.getInstance().getAllVideos();
        List <Video> sortedVideos = new ArrayList<>();

        // search the genre
        String filter = recommendation.getGenre();
        for (Video video : allVideos) {
            if (specificUser.getHistory().get(video.getTitle()) == null) {
                if (video.getGenre().contains(filter)) {
                    sortedVideos.add(new Video(video.getTitle(), video.getGenre(), video.getProductionYear(), video.getCast(), video.getAverageRating(), video.getNumberViews(), video.getNumberFavorite(), video.getIndex()));
                }
            }
        }

        if (sortedVideos.size() == 0) {
            return null;
        }
        Collections.sort(sortedVideos, Comparator.comparing(Video::getAverageRating).reversed().thenComparing(Video::getTitle));
        return sortedVideos;
    }

    public static String printSortedLists(List<Video> sortedList) {

        if (sortedList == null) {
            return "SearchRecommendation cannot be applied!";
        }
        String output = "SearchRecommendation result: [";
        List<Video> nSortedList = new ArrayList<>();
        int i;
        for (i = 0; i < sortedList.size(); i++) {
            nSortedList.add(sortedList.get(i));
        }
        for (i = 0; i < nSortedList.size() - 1; i++) {
            output = output + nSortedList.get(i).getTitle() + ", ";
        }
        if (nSortedList.size() != 0)
            output = output + nSortedList.get(i).getTitle() + "]";
        else
            output = output + "]";

        return output;
    }


}
