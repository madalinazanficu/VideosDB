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


// maintain a Singleton Database for each type of input: in this case actors
public class ActorsDB {
    // list of actors
    private final List<Actor> allActors;
    public static ActorsDB instance = null;

    /**
     * @return
     */
    public static ActorsDB getInstance() {
        if (instance == null) {
            instance = new ActorsDB();
        }
        return instance;
    }

    // construct the list of actors
    private ActorsDB() {
        this.allActors = new ArrayList<Actor>();
    }

    /**
     * @param input
     */
    // bring information for actors from input
    public void SetActorsDB(final Input input) {
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
     * @param action
     * @return
     */
    public List<Actor> sortActorsByAverge(final ActionInputData action) {
        List<Actor> sortedList = new ArrayList<>();
        List<Actor> nSortedList = new ArrayList<>();

        for (Actor actor : this.allActors) {
            sortedList.add(new Actor(actor.getName(), actor.getCareerDescription(),
                            actor.getFilmography(), actor.getAwards()));
        }
        Collections.sort(sortedList, Comparator.comparing(Actor::getAverageVideosRating)
                        .thenComparing(Actor::getName));

        if (action.getSortType().equals("desc")) {
            Collections.reverse(sortedList);
        }
        Integer n = action.getNumber();
        for (Actor actor : sortedList) {
            if (actor.getAverageVideosRating() != 0 && n > 0) {
                nSortedList.add(new Actor(actor));
                n--;
            }
        }
        return nSortedList;
    }

    /**
     * @param query
     * @return
     */
    public List<Actor> sortActorsByAwards(final ActionInputData query) {
        // extract the list of awards required
        List<String> awardsRequired = query.getFilters().get(3);

        // the filterd actors by awards
        List<Actor> filterdActors = new ArrayList<>();

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
               filterdActors.add(copyActor);
           }

       }
       Collections.sort(filterdActors, Comparator.comparing(Actor::getNumberAwards)
                        .thenComparing(Actor::getName));
       if (query.getSortType().equals("desc")) {
           Collections.reverse(filterdActors);
       }
       return filterdActors;
    }

    /**
     * @param query
     * @return
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
     *
     */
    public void clearActorDB() {
        instance = null;
        allActors.clear();

    }

}
