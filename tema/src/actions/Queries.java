package actions;

import databases.ActorsDB;
import entities.Actor;
import fileio.ActionInputData;
import actions.QueriesActor;

import java.util.Collection;
import java.util.List;

import static actions.QueriesActor.executeQueryforActors;

public class Queries {
    public String executeQuery(ActionInputData query) {
        // 3 types of objects => actors / movies / users
        if (query.getObjectType().equals("actors")) {
            return executeQueryforActors(query);
        }
        if(query.getObjectType().equals("movies")) {
            return "Query result: []";
        }
        if(query.getObjectType().equals("users")) {
            return "Query result: []";
        }
        return "Query result: []";

    }
}
