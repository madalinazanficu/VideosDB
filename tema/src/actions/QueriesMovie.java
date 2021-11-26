package actions;

import databases.VideosDB;
import entertainment.Movie;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class QueriesMovie {

    /**
     * @param query s
     * @return
     */
    public static String executeQueryforMovie(final ActionInputData query) {
        // 4 types of criteria for movies => favorite/ ratings / longest/ most_viewd
        if (query.getCriteria().equals("ratings")) {
            List<Movie> sortedList = executeQueryRatings(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("favorite")) {
            List<Movie> sortedList = executeQueryFavorite(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("longest")) {
            List<Movie> sortedList = executeQueryLongest(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("most_viewed")) {
            List<Movie> sortedList = executeQueryMostViewed(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        return "Query result: []";
    }

    /**
     * @param query
     * @return
     */
    //  sort the movies by rating
    //     filter the movies by specific criteria in .getFilteredMovie is implemented in VideosDB
    //     add in the sortedMovies only the movies with averageRating different with 0
     //    sort with Collection sort
    public static List<Movie> executeQueryRatings(final ActionInputData query) {
        List<Movie> filteredMovies = VideosDB.getInstance().getFilteredMovies(query);
        List<Movie> sortedMovies = new ArrayList<>();

        for (Movie movie : filteredMovies) {
            // movie.computeAverageRating();
            if (movie.getAverageRating() != 0) {
                sortedMovies.add(new Movie(movie));
            }
        }

        Collections.sort(sortedMovies, Comparator.comparing(Movie::getAverageRating)
                        .thenComparing(Movie::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedMovies);
        }
        return sortedMovies;
    }

    /**
     * @param query
     * @return
     */
//      sort the movies by the number of likes from user
//         filter the movies by specific criteria in .getFilteredMovie is implemented in VideosDB
//         add in the sortedMovies only the movies with the number of Favorite (likes) different with 0
//         sort with Collection sort
    public static List<Movie> executeQueryFavorite(final ActionInputData query) {
        List<Movie> filteredMovies = VideosDB.getInstance().getFilteredMovies(query);
        List<Movie> sortedMovies = new ArrayList<>();

        for (Movie movie : filteredMovies) {
            if (movie.getNumberFavorite() != 0) {
                sortedMovies.add(new Movie(movie));
            }
        }
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getNumberFavorite)
                        .thenComparing(Movie::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedMovies);
        }
        return sortedMovies;
    }

    /**
     * @param query
     * @return
     */
//      sort the movies depending of their duration
//         filter the movies by specific criteria in .getFilteredMovie is implemented in VideosDB
//         sort with Collection sort
    public static List<Movie> executeQueryLongest(final ActionInputData query) {
        List<Movie> filteredMovies = VideosDB.getInstance().getFilteredMovies(query);

        Collections.sort(filteredMovies, Comparator.comparing(Movie::getDuration)
                        .thenComparing(Movie::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(filteredMovies);
        }
        return filteredMovies;
    }


    /**
     * @param query
     * @return
     */
//       sort the movies depending the most_viewd field
//         filter the movies by specific criteria in .getFilteredMovie is implemented in VideosDB
//         add in the sortedMovies only the movies with the number of views different with 0
//         sort with Collection
    public static List<Movie> executeQueryMostViewed(final ActionInputData query) {
        List<Movie> filteredMovies = VideosDB.getInstance().getFilteredMovies(query);
        List<Movie> sortedMovies = new ArrayList<>();

        for (Movie movie : filteredMovies) {
            if (movie.getNumberViews() != 0) {
                sortedMovies.add(new Movie(movie));
            }
        }
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getNumberViews)
                        .thenComparing(Movie::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedMovies);
        }
        return sortedMovies;
    }

    /**
     * @param sortedList
     * @param n
     * @return
     */
    public static String printSortedLists(final List<Movie> sortedList, final int n) {
        String output = "Query result: [";

        List<Movie> nSortedList = new ArrayList<>();
        int i;
        for (i = 0; i < n && i < sortedList.size(); i++) {
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
