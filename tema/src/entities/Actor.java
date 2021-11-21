package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import actor.ActorsAwards;
import entertainment.Video;

public class Actor {

    // the name of the actor
    private String name;

    // every actor has a career description
    private String careerDescription;

    // the videos in which the actor played
    private ArrayList <String> filmography;

    // the award that the actor gained, and their number
    private Map<ActorsAwards, Integer> awards;

    public Actor(String name, String careerDescription, ArrayList<String> filmography, Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    // getters for every attribute
    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
}
