package actions;

import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QueriesSerial {
    public static String executeQueryforSerial(ActionInputData query) {
        // 4 types of criteria for movies => favorite/ ratings / longest/ most_viewd

        if (query.getCriteria().equals("ratings")) {
            List<Serial> sortedList = executeQueryRatings(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("favorite")) {
            List <Serial> sortedList = executeQueryFavorite(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("longest")) {
            List <Serial> sortedList = executeQueryLongest(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        if (query.getCriteria().equals("most_viewed")) {
            List <Serial> sortedList = executeQueryMostViewed(query);
            return printSortedLists(sortedList, query.getNumber());
        }
        return "Query result: []";
    }
    public static List<Serial> executeQueryRatings(ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);
        List<Serial> sortedSeries = new ArrayList<>();

        for (Serial serial : filteredSeries) {
            serial.computeAverageRating();
            if (serial.getAverageRating() != 0) {
                sortedSeries.add(new Serial(serial.getTitle(), serial.getGenre(), serial.getProductionYear(), serial.getAllSeasons(), serial.getNumberofSeasons(), serial.getCast(),  serial.getAverageRating(), serial.getNumberViews(), serial.getNumberFavorite()));
            }
        }
        Collections.sort(sortedSeries, Comparator.comparing(Serial::getAverageRating).thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedSeries);
        }
        return sortedSeries;
    }

    public static List<Serial> executeQueryFavorite(ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);
        List<Serial> sortedSeries = new ArrayList<>();

        for (Serial serial : filteredSeries) {
            if (serial.getNumberFavorite() != 0) {
                sortedSeries.add(new Serial(serial.getTitle(), serial.getGenre(), serial.getProductionYear(), serial.getAllSeasons(), serial.getNumberofSeasons(), serial.getCast(),  serial.getAverageRating(), serial.getNumberViews(), serial.getNumberFavorite()));
            }
        }
        Collections.sort(sortedSeries, Comparator.comparing(Serial::getNumberFavorite).thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedSeries);
        }
        return sortedSeries;
    }

    public static List<Serial> executeQueryLongest(ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);

        Collections.sort(filteredSeries, Comparator.comparing(Serial::getDuration).thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(filteredSeries);
        }
        return filteredSeries;
    }
    public static List<Serial> executeQueryMostViewed(ActionInputData query) {
        List<Serial> filteredSeries = VideosDB.getInstance().getFilteredSeries(query);
        List<Serial> sortedSeries = new ArrayList<>();

        for (Serial serial : filteredSeries) {
            if (serial.getNumberViews() != 0) {
                sortedSeries.add(new Serial(serial.getTitle(), serial.getGenre(), serial.getProductionYear(), serial.getAllSeasons(), serial.getNumberofSeasons(), serial.getCast(),  serial.getAverageRating(), serial.getNumberViews(), serial.getNumberFavorite()));
            }
        }
        Collections.sort(sortedSeries, Comparator.comparing(Serial::getNumberViews).thenComparing(Serial::getTitle));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedSeries);
        }
        return sortedSeries;
    }
    public static String printSortedLists(List<Serial> sortedList, int n) {
        String output = "Query result: [";

        List<Serial> nSortedList = new ArrayList<>();
        int i;
        for (i = 0; i < n && i < sortedList.size(); i++) {
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
