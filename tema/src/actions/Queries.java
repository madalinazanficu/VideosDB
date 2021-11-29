package actions;

import fileio.ActionInputData;

import static actions.QueriesActor.executeQueryforActors;
import static actions.QueriesMovie.executeQueryforMovie;
import static actions.QueriesSerial.executeQueryforSerial;
import static actions.QueriesUser.executeQueryforUser;

public final class Queries {
    private final ActionInputData query;
    public Queries(final ActionInputData query) {
        this.query = query;
    }

    public ActionInputData getQuery() {
        return query;
    }
    /**
     * Method used for getting the object type of the query
     * Called static methods from different classes actors/movies/shows/users to execute queries
     * @return the output message
     */
    public String executeQuery() {
        // 3 types of objects => actors / movies / users
        if (this.getQuery().getObjectType().equals("actors")) {
            return executeQueryforActors(this.getQuery());
        }
        if (this.getQuery().getObjectType().equals("movies")) {
            return executeQueryforMovie(this.getQuery());
        }
        if (this.getQuery().getObjectType().equals("shows")) {
            return executeQueryforSerial(this.getQuery());
        }
        if (this.getQuery().getObjectType().equals("users")) {
            return executeQueryforUser(this.getQuery());
        }
        return "Query result: []";
    }

}
