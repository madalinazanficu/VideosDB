package entities;

import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import actor.ActorsAwards;
import databases.VideosDB;
import entertainment.Movie;
import entertainment.Serial;


public final class Actor {

    // the name of the actor
    private final String name;

    // every actor has a career description
    private final String careerDescription;

    // the videos in which the actor played
    private final ArrayList<String> filmography;

    // the award that the actor gained, and their number
    private final Map<ActorsAwards, Integer> awards;

    // every actor has an average for the videos he played in
    private Double averageVideosRating;

    // every actor has a total number of awards
    private Integer numberAwards;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.averageVideosRating = 0.0;
        this.numberAwards = 0;
    }

    public Actor(final Actor assign) {
        this.name = assign.getName();
        this.careerDescription = assign.getCareerDescription();
        this.filmography = assign.getFilmography();
        this.awards = assign.getAwards();
        this.averageVideosRating = assign.getAverageVideosRating();
        this.numberAwards = assign.getNumberAwards();

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

    /**
     * Method used for getting the averageRating of all videos the actor played in
     * @return the averageRating of all videos the actor played in
     */
    public Double getAverageVideosRating() {
        computeAverageVideosRating();
        return this.averageVideosRating;
    }

    /**
     * Method used for getting the number of awards of the actor
     * @return the number of awards of the actor
     */
    public Integer getNumberAwards() {
        computeNumberAwards();
        return this.numberAwards;
    }

    /**
     * Method used for computing the averageRating of all videos the actor played in
     */
    public void computeAverageVideosRating() {
        int cnt = 0;
        Double sum = 0.0;
        for (String videoName : filmography) {
            // search the type of video
            String type = VideosDB.getInstance().videoType(videoName);
            // the video was not found in videos' database
            if (type.equals("Video type not found")) {
                continue;
            } else {
                if (type.equals("Movie")) {
                    Movie specificMovie = VideosDB.getInstance().getSpecificMovie(videoName);
                    specificMovie.computeAverageRating();
                    sum += specificMovie.getAverageRating();
                    if (specificMovie.getAverageRating() != 0) {
                        cnt += 1;
                    }
                }
                if (type.equals("Serial")) {
                    Serial specificSerial = VideosDB.getInstance().getSpecificSerial(videoName);
                    specificSerial.computeAverageRating();
                    sum += specificSerial.getAverageRating();
                    if (specificSerial.getAverageRating() != 0) {
                        cnt += 1;
                    }
                }
            }
        }
        if (cnt != 0) {
            this.averageVideosRating = sum / cnt;
        } else {
            this.averageVideosRating = 0.0;
        }
    }

    /**
     * Method used for computing the number of awards of actor
     */
    public void computeNumberAwards() {
        int nrAwards = 0;
        Iterator<Map.Entry<ActorsAwards, Integer>> iterator = awards.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ActorsAwards, Integer> entry = iterator.next();
            nrAwards += entry.getValue();
        }
        this.numberAwards = nrAwards;
    }

    /**
     * Method used for searching specific keywords in actor description
     * Method is called in ActorsDB/sortActorsByFD
     * @param wordsRequired the keywords needed to be search in description
     * @return true - if all keywords are found / false - otherwise
     */
    public Boolean searchWordsRequired(final List<String> wordsRequired) {
        int cnt = 0;

        // make description all characters lowercase => to make the search case-insensitive
        String lCareerDescription = this.getCareerDescription().toLowerCase(Locale.ROOT);

        // check every word required to appear in career Description
        for (String keyWord : wordsRequired) {

            // make every keyWord lowerCse
            String lKeyWord = keyWord.toLowerCase(Locale.ROOT);

            Pattern word = Pattern.compile("[ ,!.')-]" + lKeyWord + "[ ,!.'(-]");
            Matcher match = word.matcher(lCareerDescription);

            if (match.find()) {
                cnt++;
            }
        }
        return cnt == wordsRequired.size();
    }
}
