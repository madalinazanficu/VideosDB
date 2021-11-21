package databases;

import entities.Actor;
import fileio.ActorInputData;
import fileio.Input;

import java.util.ArrayList;
import java.util.List;

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
}
