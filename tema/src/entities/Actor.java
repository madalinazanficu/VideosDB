package entities;

import java.util.*;

import actor.ActorsAwards;
import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;
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

    // every actor has an average for the videos he played in
    private Double averageVideosRating;

    // every actor has a total number of awards
    private Integer numberAwards;

    public Actor(String name, String careerDescription, ArrayList<String> filmography, Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.averageVideosRating = 0.0;
        this.numberAwards = 0;
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

    public Double getAverageVideosRating() {
        computeAverageVideosRating();
        return this.averageVideosRating;
    }
    public Integer getNumberAwards() {
        computeNumberAwards();
        return this.numberAwards;
    }

    // compute average Rating of the videos the actor played in
    public void computeAverageVideosRating() {
        int cnt = 0;
        Double sum = 0.0;
        for (String videoName : filmography) {

            // caut in baza de date daca gasesc numele videoclipului
            String type = VideosDB.getInstance().videoType(videoName);
            // nu am gasit videoclipul in baza mea de date
            if (type.equals("Video type not found")) {
                continue;
            } else {

                // in caz ca video ul meu este movie
                // ii calculez rating-ul
                // si fac average
                if (type.equals("Movie")) {
                    Movie specificMovie = VideosDB.instance.getSpecificMovie(videoName);
                    specificMovie.computeAverageRating();
                    sum += specificMovie.getAverageRating();
                    if (specificMovie.getAverageRating() != 0)
                        cnt += 1;
                }
                if (type.equals("Serial")) {
                    Serial specificSerial = VideosDB.instance.getSpecificSerial(videoName);
                    specificSerial.computeAverageRating();
                    sum += specificSerial.getAverageRating();
                    if (specificSerial.getAverageRating() != 0)
                        cnt += 1;
                }
            }
        }
        if (cnt != 0)
            this.averageVideosRating = sum / cnt;
        else
            this.averageVideosRating = 0.0;
    }
    public void computeNumberAwards() {
        int nrAwards = 0;
        Iterator<Map.Entry<ActorsAwards, Integer>> iterator = awards.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<ActorsAwards, Integer> entry = iterator.next();
            nrAwards += entry.getValue();
        }
        this.numberAwards = nrAwards;
    }
    public Boolean searchWordsRequired(List<String> wordsRequired) {

        // make description all characters lowercase => to make the search case insensitive
        String lCareerDescription = this.getCareerDescription().toLowerCase(Locale.ROOT);

        // check every word required to appear in carrer Description
        for (String keyWord : wordsRequired) {

            // make every keyWord lowerCse
            String lKeyWord = keyWord.toLowerCase(Locale.ROOT);

            if (!this.careerDescription.contains(lKeyWord)) {
                return false;
            }
        }
        return true;
    }
}
