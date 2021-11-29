package actions;

import databases.VideosDB;
import entertainment.Serial;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class QueriesSerial {
    private QueriesSerial() {
    }

    /**
     * Method used for getting the type of criteria type of sorting for series
     * @param query - input data to extract information
     * @return the output message
     */
    public static String executeQueryforSerial(final ActionInputData query) {
        // 4 types of criteria for movies => favorite/ ratings / longest/ most_viewd

        if (query.getCriteria().equals("ratings")) {
            List<Serial> sortedList = executeQueryRatings(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("favorite")) {
            List<Serial> sortedList = executeQueryFavorite(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("longest")) {
            List<Serial> sortedList = executeQueryLongest(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("most_viewed")) {
            List<Serial> sortedList = executeQueryMostViewed(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        return "Query result: []";
    }

    /**
     * Method used for sorting the series by averageRating
     * Selected the series by specific criteria (year / genres) in VideosDB
     * Added in sorted series list only the movies with averageRating different with 0
     * @param query - input data to extract information
     * @return the list of sorted series
     */
    public static List<Serial> executeQueryRatings(final ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);
        List<Serial> sortedSeries = new ArrayList<>();

        for (Serial serial : filteredSeries) {
            serial.computeAverageRating();
            if (serial.getAverageRating() != 0) {
                sortedSeries.add(new Serial(serial));
            }
        }
        Collections.sort(sortedSeries, Comparator.comparing(Serial::getAverageRating)
                        .thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedSeries);
        }
        return sortedSeries;
    }

    /**
     * Method used for sorting the series by the number of likes from users
     * Selected the movies by specific criteria request from input
     * Added in sorted series list only the series with numberFavorite ("likes") different with 0
     * @param query - input data to extract information
     * @return the list of sorted series
     */
    public static List<Serial> executeQueryFavorite(final ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);
        List<Serial> sortedSeries = new ArrayList<>();

        for (Serial serial : filteredSeries) {
            if (serial.getNumberFavorite() != 0) {
                sortedSeries.add(new Serial(serial));
            }
        }
        Collections.sort(sortedSeries, Comparator.comparing(Serial::getNumberFavorite)
                        .thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedSeries);
        }
        return sortedSeries;
    }

    /**
     * Method used for sorting the series depending on their duration
     * Selected the series by specific criteria requested from input
     * @param query - input data to extract information
     * @return the list of sorted series
     */
    public static List<Serial> executeQueryLongest(final ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);

        Collections.sort(filteredSeries, Comparator.comparing(Serial::getDuration)
                        .thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(filteredSeries);
        }
        return filteredSeries;
    }

    /**
     * Method used for sorting the series depending on the number of views
     * Selected the series by specific criteria requested in input
     * Added in sorted series list only the series with number of views different with 0
     * @param query - input data to extract information
     * @return the list of sorted series
     */
    public static List<Serial> executeQueryMostViewed(final ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);
        List<Serial> sortedSeries = new ArrayList<>();

        for (Serial serial : filteredSeries) {
            if (serial.getNumberViews() != 0) {
                sortedSeries.add(new Serial(serial));
            }
        }
        Collections.sort(sortedSeries, Comparator.comparing(Serial::getNumberViews)
                .thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedSeries);
        }
        return sortedSeries;
    }

    /**
     * Method used for printing the series
     * @param sortedList - list needed to be printed
     * @param n - number of elements from the list requested to be printed
     * @return the output message
     */
    public static String printSortedLists(final List<Serial> sortedList, final int n) {
        String output = "Query result: [";

        List<Serial> nSortedList = new ArrayList<>();
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
