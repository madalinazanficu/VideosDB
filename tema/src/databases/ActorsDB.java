package databases;

import actions.Commands;
import actor.ActorsAwards;
import entities.Actor;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;

import java.util.*;

// maintain a Singleton Database for each type of input: in this case actors
public class ActorsDB {
    // list of actors
    private final List<Actor> allActors;
    public static ActorsDB instance = null;

    public static ActorsDB getInstance() {
        if (instance == null) {
            instance = new ActorsDB();
        }
        return instance;
    }

    // construct the list of actors
    private ActorsDB (){
        this.allActors = new ArrayList<Actor>();
    }

    // bring information for actors from input
    public void SetActorsDB (Input input) {
        for (ActorInputData inputActor : input.getActors()) {
            // iterate through the list of actors from input and add it to my own database
            Actor newActor = new Actor(inputActor.getName(), inputActor.getCareerDescription(), inputActor.getFilmography(), inputActor.getAwards());
            this.allActors.add(newActor);
        }
    }

    public List<Actor> getAllActors() {
        return allActors;
    }

    public List<Actor> sortActorsByAverge(ActionInputData action) {
        List<Actor> sortedList = new ArrayList<>();
        List<Actor> nSortedList = new ArrayList<>();

        for (Actor actor : this.allActors) {
            sortedList.add(new Actor(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards()));
        }
        Collections.sort(sortedList, Comparator.comparing(Actor::getAverageVideosRating).thenComparing(Actor::getName));

        if (action.getSortType().equals("desc")) {
            Collections.reverse(sortedList);
        }
        Integer n = action.getNumber();
        for (Actor actor : sortedList) {
            if (actor.getAverageVideosRating() != 0 && n > 0) {
                nSortedList.add(new Actor(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards()));
                n--;
            }
        }
        return nSortedList;
    }
    public List <Actor> sortActorsByAwards(ActionInputData query) {
        // extract the list of awards required
        List <String> awardsRequired = query.getFilters().get(3);

        // the filterd actors by awards
        List <Actor> filterdActors = new ArrayList<>();

        // iterate in all actors
       for (Actor actor : this.allActors) {

           // iterate in awards required
           Boolean exist = true;
           for (String award : awardsRequired) {
               if (actor.getAwards().get(award) == null) {
                   exist = false;
               }
           }
           if (exist) {
               Actor copyActor = new Actor(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards());
               copyActor.computeNumberAwards();
               filterdActors.add(copyActor);
           }

       }
       Collections.sort(filterdActors, Comparator.comparing(Actor::getNumberAwards).thenComparing(Actor::getName));
       if (query.getSortType().equals("desc")) {
           Collections.reverse(filterdActors);
       }
       return filterdActors;
    }

    public List <Actor> sortActorsByFD(ActionInputData query) {
        // extract the list of words required
        List<String> wordsRequired = query.getFilters().get(2);

        // the filtered actors by words
        List <Actor> filteredActors = new ArrayList<>();

        // iterate in all actors
        for (Actor actor : this.allActors) {

            // check the words required
            Boolean exist = actor.searchWordsRequired(wordsRequired);
            if (exist) {
                Actor copyActor = new Actor(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards());
                filteredActors.add(copyActor);
            }
        }
        Collections.sort(filteredActors, Comparator.comparing(Actor::getName));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(filteredActors);
        }

        return filteredActors;
    }


    public void clearActorDB() {
        instance = null;
        allActors.clear();

    }

}
