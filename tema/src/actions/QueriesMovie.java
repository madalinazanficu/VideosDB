package actions;

import databases.VideosDB;
import entertainment.Movie;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public final class QueriesMovie {
    private QueriesMovie() {
    }

    /**
     * Method used for geting the criteria type of sorting for movie
     * @param query - input data to extract multiple attributes
     * @return the output message
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

    /** Method used for sorting the movies by averageRating
     * Filtered the movies by specific criteria (year / genres) in VideosDB
     * Added in sorted movies list only the movies with averageRating different with 0
     * @param query - input data to extract information
     * @return the list of sorted movies
     */
    public static List<Movie> executeQueryRatings(final ActionInputData query) {
        List<Movie> filteredMovies = VideosDB.getInstance().getFilteredMovies(query);
        List<Movie> sortedMovies = new ArrayList<>();

        for (Movie movie : filteredMovies) {
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

    /** Method used for sorting the movies by the number of likes from users
     * Filtered the movies by specific criteria in VideoDB
     * Added in sorted movies list only the movies with numberFavorite ("likes") different with 0
     * @param query - input data to extract information
     * @return the list of sorted movies
     */

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
     * Method used for sorting the movies depending on their duration
     * Selected the movies by specific criteria requested in input
     * @param query - input data to extract information
     * @return the list of sorted movies
     */
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
     * Method used for sorting the movies depending on the number of views
     * Selected the movies by specific criteria requested in input
     * Added in sorted movies list only the movies with number of views different with 0
     * @param query - input data to extract information
     * @return the list of sorted movies
     */

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
     * Method used for printing the movies
     * @param sortedList - list needed to be printed
     * @param n - number of elements from the list requested to be printed
     * @return the output message
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
