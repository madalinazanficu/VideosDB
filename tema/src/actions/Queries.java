package actions;

import databases.ActorsDB;
import entities.Actor;
import fileio.ActionInputData;
import actions.QueriesActor;

import java.util.Collection;
import java.util.List;

import static actions.QueriesActor.executeQueryforActors;
import static actions.QueriesMovie.executeQueryforMovie;
import static actions.QueriesSerial.executeQueryforSerial;
import static actions.QueriesUser.executeQueryforUser;

public class Queries {
    public static String executeQuery(ActionInputData query) {
        // 3 types of objects => actors / movies / users
        if (query.getObjectType().equals("actors")) {
            return executeQueryforActors(query);
        }
        if (query.getObjectType().equals("movies")) {
            return executeQueryforMovie(query);
        }
        if (query.getObjectType().equals("shows")) {
            return executeQueryforSerial(query);
        }
        if(query.getObjectType().equals("users")) {
            return executeQueryforUser(query);
        }
        return "Query result: []";
    }

}
