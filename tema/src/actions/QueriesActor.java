package actions;

import databases.ActorsDB;
import entities.Actor;
import fileio.ActionInputData;

import java.util.List;

public class QueriesActor {
    public static String executeQueryforActors(ActionInputData query) {

        // 3 types of criteria => average / awards / filter_description
        if (query.getCriteria().equals("average")) {
            return executeQueryAverage(query);
        }
        if (query.getCriteria().equals("awards")) {
            return executeQueryAwards(query);
        }
        if (query.getCriteria().equals("filter_description")) {
            return executeQueryFD(query);
        }
        return "Query result: []";
    }


    // sortarea celor N actori in functie de nota video-urilor
    public static String executeQueryAverage(ActionInputData query) {
        String output = "Query result: [";

        List<Actor> nSortedList = ActorsDB.getInstance().sortActorsByAverge(query);
        int i;
        for (i = 0; i < nSortedList.size() - 1; i++) {
            output = output + nSortedList.get(i).getName() + ", ";
        }
        if (nSortedList.size() != 0)
            output = output + nSortedList.get(i).getName() + "]";
        else
            output = output + "]";
        return output;
    }

    public static String executeQueryAwards(ActionInputData query) {
        String output = "Query result: [";

        List<Actor> filteredActors = ActorsDB.getInstance().sortActorsByAwards(query);
        int i;
        for (i = 0; i < filteredActors.size() - 1; i++) {
            output = output + filteredActors.get(i) + "]";
        }
        if (filteredActors.size() != 0)
            output = output + filteredActors.get(i).getName() + "]";
        else
            output = output + "]";
        return output;
    }
    public static String executeQueryFD(ActionInputData query) {
        String output = "Query result: [";

        List<Actor> filteredActors = ActorsDB.getInstance().sortActorsByFD(query);
        int i;
        for (i = 0; i < filteredActors.size() - 1; i++) {
            output = output + filteredActors.get(i) + "]";
        }
        if (filteredActors.size() != 0)
            output = output + filteredActors.get(i).getName() + "]";
        else
            output = output + "]";

        return output;
    }
}
