package actions;

import databases.UsersDB;
import databases.VideosDB;
import entertainment.Video;
import entities.User;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;


public final class Recommendation {
    private final ActionInputData recommendation;
    public Recommendation(final ActionInputData recommendation) {
        this.recommendation = recommendation;
    }

    public ActionInputData getRecommendation() {
        return recommendation;
    }

    /**
     * Method used for getting the type of recommendation
     * @return the output message
     */
    public String executeRecommendation() {

        String username = this.getRecommendation().getUsername();
        User specificUser = UsersDB.getInstance().getSpecificUser(username);
        if (specificUser == null) {
            return "User not found!";
        }
        if (this.getRecommendation().getType().equals("standard")) {
            return executeRecommendationStandard(specificUser);
        }
        if (this.getRecommendation().getType().equals("best_unseen")) {
            return executeRecommendationBestUnseen(specificUser);
        }
        if (this.getRecommendation().getType().equals("search")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                 List<Video> sortedList = executeRecommendationSearch(specificUser);
                 return printSortedLists(sortedList);

            } else {
                return "SearchRecommendation cannot be applied!";
            }
        }
        if (this.getRecommendation().getType().equals("popular")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                return executePopularRecommendation(specificUser);
            } else {
                return "PopularRecommendation cannot be applied!";
            }
        }
        if (this.getRecommendation().getType().equals("favorite")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                return executeRecommendationFavorite(specificUser);
            } else {
                return "FavoriteRecommendation cannot be applied!";
            }
        }
        return "Not execute yet";
    }

    /**
     * Method used for searching the first unseen video from database for a specific user
     * @param user - the specific user to make him a recommendation
     * @return the output message
     */
    public String executeRecommendationStandard(final User user) {
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

    /**
     * Method used for searching the best unseen video (after averageRating) for a specific user
     * @param user - the specific user to make him a recommendation
     * @return the output message
     */
    public String executeRecommendationBestUnseen(final User user) {
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();
        List<Video> sortedVideos = new ArrayList<>();


        for (Video video : allVideos) {
            if (user.getHistory().get(video.getTitle()) == null) {
                sortedVideos.add(new Video(video));
            }
        }
        if (sortedVideos.size() == 0) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        Collections.sort(sortedVideos, Comparator.comparing(Video::getAverageRating).reversed()
                .thenComparing(Video::getIndex));
        return "BestRatedUnseenRecommendation result: " + sortedVideos.get(0).getTitle();
    }

    /**
     * Method used for searching the most favorite video in video database
     * The video should have not been saw by a specific user
     * @param specificUser - the user to make a recommendation
     * @return the output message
     */
    public String executeRecommendationFavorite(final User specificUser) {
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();
        List<Video> sortedVideos = new ArrayList<>();

        List<User> users = UsersDB.getInstance().getAllUsers();
        // iterate in videos database  to find the most liked video
        for (Video video : allVideos) {
            if (specificUser.getHistory().get(video.getTitle()) == null) {
                if (video.getNumberFavorite() != 0) {
                    sortedVideos.add(new Video(video));
                }
            }
        }
        if (sortedVideos.size() == 0) {
            return "FavoriteRecommendation cannot be applied!";
        }
        Collections.sort(sortedVideos, Comparator.comparing(Video::getNumberFavorite).reversed()
                .thenComparing(Video::getIndex));
        return "FavoriteRecommendation result: " + sortedVideos.get(0).getTitle();

    }

    /**
     * Method used for searching all the unseen videos with specific genres for a specific user
     * @param specificUser - the user to make a recommendation
     * @return the output message
     */
    public List<Video> executeRecommendationSearch(final User specificUser) {
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();
        List<Video> sortedVideos = new ArrayList<>();

        // search the genre in all videos database
        String filter = this.getRecommendation().getGenre();
        for (Video video : allVideos) {

            // the video should have not been seen by the specific user
            if (specificUser.getHistory().get(video.getTitle()) == null) {
                if (video.getGenre().contains(filter)) {
                    sortedVideos.add(new Video(video));
                }
            }
        }
        if (sortedVideos.size() == 0) {
            return null;
        }
        Collections.sort(sortedVideos, Comparator.comparing(Video::getAverageRating)
                        .thenComparing(Video::getTitle));
        return sortedVideos;
    }

    /**
     * Method used for getting the unseen video of the specific user
     * The video should have in genre list the most popular genre of all the videos
     * @param specificUser - the user to make recommendation to
     * @return the output message
     */
    public String executePopularRecommendation(final User specificUser) {

        String output = "PopularRecommendation result: ";
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();
        HashMap<String, Integer> populars = new HashMap<>();

        // search the post popular genre of video
        for (Video video : allVideos) {

            // every video has multiple genres
            List<String> genres = video.getGenre();
            Integer numberOfViews = video.getNumberViews();

            for (String genre : genres) {
                if (populars.get(genre) != null) {
                    Integer genreNrViews = populars.get(genre);
                    populars.put(genre, genreNrViews + numberOfViews);
                } else {
                    populars.put(genre, numberOfViews);
                }
            }
        }
        // create a list from elements of hashmap
        List<Map.Entry<String, Integer>> list = new LinkedList
                                                <Map.Entry<String, Integer>>(populars.entrySet());


        // sort the list of generes
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // search every genre in the sorting order
        for (Map.Entry<String, Integer> element : list) {
            // iterate in videos database to search the most favorite genre
            for (Video video : allVideos) {
                if (video.getGenre().contains(element.getKey())) {
                    // the video should have not been seen by the user
                    if (specificUser.getHistory().get(video.getTitle()) == null) {
                        return output + video.getTitle();
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Method used for printing a list
     * @param sortedList the list needed to be printed
     * @return the output message
     */
    public static String printSortedLists(final List<Video> sortedList) {

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
        if (nSortedList.size() != 0) {
            output = output + nSortedList.get(i).getTitle() + "]";
        } else {
            output = output + "]";
        }

        return output;
    }


}
