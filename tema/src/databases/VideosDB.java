package databases;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

// maintain a Database for each type of input: in this case videos
// which include both movies and shows
public class VideosDB {

    public static VideosDB instance = null;

    // list for all videos both movies or series
    private List<Video> allVideos;

    // list only for movies
    private List<Movie> allMovies;

    // list only for shows
    private List<Serial> allSeries;

    public static VideosDB getInstance() {
        if (instance == null) {
            instance = new VideosDB();
        }
        return instance;
    }

    // construct the database for videos
    private VideosDB() {
        this.allVideos = new ArrayList<Video>();
        this.allMovies = new ArrayList<Movie>();
        this.allSeries = new ArrayList<Serial>();
    }

    // bring information for movies from input
    public void setAllMovies(Input input) {
        for (MovieInputData movieInput : input.getMovies()) {
            Movie newMovie = new Movie(movieInput.getTitle(), movieInput.getGenres(), movieInput.getYear(), movieInput.getDuration(), movieInput.getCast());

            // add in the list of movies
            this.allMovies.add(newMovie);

            // also add in the list of videos
            this.allVideos.add(newMovie);
        }

    }

    // bring information for shows from input
    public void setAllShows(Input input) {
        for (SerialInputData serialInput : input.getSerials()) {
            Serial newSerial = new Serial(serialInput.getTitle(), serialInput.getGenres(), serialInput.getYear(), serialInput.getSeasons(), serialInput.getNumberSeason(), serialInput.getCast());

            // add in the list of series
            this.allSeries.add(newSerial);

            // also add in the list of videos
            this.allVideos.add(newSerial);
        }

    }
    // is a movie or a serial? (used in Commands)
    public String videoType(String Video) {
        // search in allMovies
        for (Movie movie : allMovies) {
            if (movie.getTitle().equals(Video)) {
                return "Movie";
            }
        }
        // search in allSeries
        for (Serial serial: allSeries) {
            if (serial.getTitle().equals(Video)) {
                return "Serial";
            }
        }
        return "Video type not found";
    }

    public Video getSpecificVideo(String videoName) {
        for (Video video : allVideos)
            if (video.getTitle().equals(videoName))
                return video;

        // video not found
        return null;
    }

    public Serial getSpecificSerial(String serialName) {
        for (Serial serial : allSeries)
            if (serial.getTitle().equals(serialName))
                return serial;

        // serial not found
        return null;
    }
    public Movie getSpecificMovie(String movieName) {
        for (Movie movie : allMovies)
            if (movie.getTitle().equals(movieName))
                return movie;

        // movie not found
        return null;
    }
    public void clearVideosDB() {
        instance = null;
        this.allMovies.clear();
        this.allVideos.clear();
        this.allSeries.clear();
    }
}
