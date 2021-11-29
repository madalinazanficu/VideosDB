package databases;

import actor.ActorsAwards;
import entities.Actor;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import static common.Constants.AWARDS_FILTER;


public final class ActorsDB {

    private final List<Actor> allActors;
    private static ActorsDB instance = null;

    /**
     * Method used for creating or to getting the instance of Database (Singleton pattern)
     * @return the instance of Actors' Database
     */
    public static ActorsDB getInstance() {
        if (instance == null) {
            instance = new ActorsDB();
        }
        return instance;
    }

    private ActorsDB() {
        this.allActors = new ArrayList<Actor>();
    }

    /**
     * Method used for bringing the information from input to database
     * @param input - input information to set the list of actors
     */
    public void setActorsDB(final Input input) {
        for (ActorInputData inputActor : input.getActors()) {
            // iterate through the list of actors from input and add it to my own database
            Actor newActor = new Actor(inputActor.getName(), inputActor.getCareerDescription(),
                                        inputActor.getFilmography(), inputActor.getAwards());
            this.allActors.add(newActor);
        }
    }

    public List<Actor> getAllActors() {
        return allActors;
    }

    /**
     * Method used for sorting the actors by averageGrade of the videos they played in
     * This method is called in QueriesActor/executeQueryAverage
     * @param query - input information
     * @return the list of sorted actors
     */
    public List<Actor> sortActorsByAverage(final ActionInputData query) {
        List<Actor> sortedList = new ArrayList<>();
        List<Actor> nSortedList = new ArrayList<>();

        for (Actor actor : this.allActors) {
            sortedList.add(new Actor(actor.getName(), actor.getCareerDescription(),
                            actor.getFilmography(), actor.getAwards()));
        }
        Collections.sort(sortedList, Comparator.comparing(Actor::getAverageVideosRating)
                        .thenComparing(Actor::getName));

        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedList);
        }
        int n = query.getNumber();
        for (Actor actor : sortedList) {
            if (actor.getAverageVideosRating() != 0 && n > 0) {
                nSortedList.add(new Actor(actor));
                n--;
            }
        }
        return nSortedList;
    }

    /**
     * Method used for sorting the actors by numberAwards
     * This method is called in QueriesActor/executeQueryAwards
     * @param query - input information
     * @return the list of sorted actors
     */
    public List<Actor> sortActorsByAwards(final ActionInputData query) {
        // extract the list of awards required

        List<String> awardsRequired = query.getFilters().get(AWARDS_FILTER);

        // the filtered actors by awards
        List<Actor> filteredActors = new ArrayList<>();

        // iterate in all actors
       for (Actor actor : this.allActors) {
           // iterate in awards required
           Boolean exist = true;
           for (String award : awardsRequired) {
               ActorsAwards a = ActorsAwards.valueOf(award);
               if (actor.getAwards().get(a) == null) {
                   exist = false;
               }
           }
           if (exist) {
               Actor copyActor = new Actor(actor);
               copyActor.computeNumberAwards();
               filteredActors.add(copyActor);
           }
       }
       Collections.sort(filteredActors, Comparator.comparing(Actor::getNumberAwards)
                        .thenComparing(Actor::getName));
       if (query.getSortType().equals("desc")) {
           Collections.reverse(filteredActors);
       }
       return filteredActors;
    }

    /**
     * Method used for sorting the actors by Filter Description
     * This method is called in QueriesActor/executeQueryAwards
     * @param query - input information
     * @return the list of sorted actors
     */
    public List<Actor> sortActorsByFD(final ActionInputData query) {
        // extract the list of words required
        List<String> wordsRequired = query.getFilters().get(2);

        // the filtered actors by words
        List<Actor> filteredActors = new ArrayList<>();

        // iterate in all actors
        for (Actor actor : this.allActors) {
            // check the words required
            Boolean exist = actor.searchWordsRequired(wordsRequired);
            if (exist) {
                Actor copyActor = new Actor(actor);
                filteredActors.add(copyActor);
            }
        }
        Collections.sort(filteredActors, Comparator.comparing(Actor::getName));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(filteredActors);
        }

        return filteredActors;
    }

    /**
     * Method used to clear the information from actors' database
     * Helped to reuse safely the database
     */
    public void clearActorDB() {
        allActors.clear();
    }

}
