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


public class Recommendation {
    /**
     * @param recommendation
     * @return
     */
    public static String executeRecommendation(final ActionInputData recommendation) {

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
                 List<Video> sortedList = executeRecommendationSearch(recommendation, specificUser);
                 return printSortedLists(sortedList);

            } else {
                return "SearchRecommendation cannot be applied!";
            }
        }
        if (recommendation.getType().equals("popular")) {
            if (specificUser.getSubscription().equals("PREMIUM")) {
                return executePopularRecommendation(recommendation, specificUser);
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

    /**
     * @param recommendation
     * @param user
     * @return
     */
    public static String executeRecommendationStandard(final ActionInputData recommendation,
                                                       final User user) {
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
     * @param recommendation
     * @param user
     * @return
     */
    public static String executeRecommendationBestUnseen(final ActionInputData recommendation,
                                                         final User user) {
        List<Video> allVideos = VideosDB.instance.getAllVideos();
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
     * @param recommendation
     * @param specificUser
     * @return
     */
    public static String executeRecommendationFavorite(final ActionInputData recommendation,
                                                       final User specificUser) {
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();
        List<Video> sortedVideos = new ArrayList<>();

        List<User> users = UsersDB.instance.getAllUsers();
        // iterate in video to find the most liked video
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
     * @param recommendation
     * @param specificUser
     * @return
     */
    public static List<Video> executeRecommendationSearch(final ActionInputData recommendation,
                                                          final User specificUser) {
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();
        List<Video> sortedVideos = new ArrayList<>();

        // search the genre
        String filter = recommendation.getGenre();
        for (Video video : allVideos) {
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
     * @param recommendation
     * @param specificUser
     * @return
     */
    public static String executePopularRecommendation(final ActionInputData recommendation,
                                                      final User specificUser) {

        String output = "PopularRecommendation result: ";
        List<Video> allVideos = VideosDB.getInstance().getAllVideos();

        // keep (genre, numberOfvisualisation) in a map
        HashMap<String, Integer> populars = new HashMap<>();


        // search the post popular genre
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


        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // verificarea fiecarui gen in parte
        for (Map.Entry<String, Integer> element : list) {

            // iterez in lista de video-uri pentru a cauta genul favorit

            for (Video video : allVideos) {

                // daca apartine genului favorite
                if (video.getGenre().contains(element.getKey())) {

                    // daca videoclipul nu a fost vizualizat de catre user
                    if (specificUser.getHistory().get(video.getTitle()) == null) {
                        return output + video.getTitle();
                    }
                }
            }
        }

        return "PopularRecommendation cannot be applied!";
    }

    /**
     * @param sortedList
     * @return
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
