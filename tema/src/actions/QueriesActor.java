package actions;

import databases.ActorsDB;
import entities.Actor;
import fileio.ActionInputData;

import java.util.List;

public final class QueriesActor {
    private QueriesActor() {
    }
    /**
     * Method used for getting the criteria type of sorting for actors
     * @param query - input data to extract multiple attributes
     * @return the output message
     */
    public static String executeQueryforActors(final ActionInputData query) {

        // 3 types of criteria => average / awards / filter_description
        if (query.getCriteria().equals("average")) {
            List<Actor> sortedList = executeQueryAverage(query);
            return printSortedList(sortedList);
        }
        if (query.getCriteria().equals("awards")) {
            List<Actor> sortedList = executeQueryAwards(query);
            return printSortedList(sortedList);
        }
        if (query.getCriteria().equals("filter_description")) {
            List<Actor> sortedList = executeQueryFD(query);
            return printSortedList(sortedList);
        }
        return "Query result: []";
    }

    /**
     * Method used for getting the sorted actors by averageGrade
     * Sorted the actors by grade in ActorsDB
     * @param query - input data to extract information
     * @return the sorted list
     */
    public static List<Actor> executeQueryAverage(final ActionInputData query) {
        return ActorsDB.getInstance().sortActorsByAverage(query);
    }

    /**
     * Method used for getting the sorted actors by awards
     * Sorted the actors by grade in ActorsDB
     * @param query - input data to extract information
     * @return the sorted list
     */
    public static List<Actor> executeQueryAwards(final ActionInputData query) {
        return ActorsDB.getInstance().sortActorsByAwards(query);
    }

    /**
     * Method used for getting the sorted actors by Filter Description
     * Sorted the actors by Filter Description in ActorsDB
     * @param query - input data to extract information
     * @return the sorted list
     */
    public static List<Actor> executeQueryFD(final ActionInputData query) {
        return ActorsDB.getInstance().sortActorsByFD(query);
    }

    /**
     * Method used for printing a list
     * @param sortedList - the list needed to be printed
     * @return the output message
     */
    public static String printSortedList(final List<Actor> sortedList) {
        String output = "Query result: [";
        int i;
        for (i = 0; i < sortedList.size() - 1; i++) {
            output = output + sortedList.get(i).getName() + ", ";
        }
        if (sortedList.size() != 0) {
            output = output + sortedList.get(i).getName() + "]";
        } else {
            output = output + "]";
        }
        return output;
    }
}
