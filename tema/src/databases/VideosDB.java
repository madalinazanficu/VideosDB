package databases;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.*;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

// maintain a Database for each type of input: in this case videos
// which include both movies and shows
public class VideosDB {

    public static VideosDB instance = null;

    private Integer VideoIndex;

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
        this.VideoIndex = 0;
        this.allVideos = new ArrayList<Video>();
        this.allMovies = new ArrayList<Movie>();
        this.allSeries = new ArrayList<Serial>();
    }
    public void show() {
        System.out.println(allMovies);
    }


    // bring information for movies from input
    public void setAllMovies(Input input) {
        for (MovieInputData movieInput : input.getMovies()) {
            //Movie newMovie = new Movie(movieInput.getTitle(), movieInput.getGenres(), movieInput.getYear(), movieInput.getDuration(), movieInput.getCast());
            Movie newMovie = new Movie(movieInput.getTitle(), movieInput.getGenres(), movieInput.getYear(), movieInput.getDuration(), movieInput.getCast(), 0.0, 0, 0, VideoIndex);

            // add in the list of movies
            this.allMovies.add(newMovie);

            // increment the VideoIndex
            incrementVideoIndex();
        }

    }

    // bring information for shows from input
    public void setAllShows(Input input) {
        for (SerialInputData serialInput : input.getSerials()) {

            // Serial newSerial = new Serial(serialInput.getTitle(), serialInput.getGenres(), serialInput.getYear(), serialInput.getSeasons(), serialInput.getNumberSeason(), serialInput.getCast());
            Serial newSerial = new Serial(serialInput.getTitle(), serialInput.getGenres(), serialInput.getYear(), serialInput.getSeasons(), serialInput.getNumberSeason(), serialInput.getCast(), 0.0, 0, 0, VideoIndex);

            // add in the list of series
            this.allSeries.add(newSerial);

            // increment the VideoIndex
            incrementVideoIndex();
        }

    }
    public List<Video> getAllVideos() {
        List<Video> allVideos = new ArrayList<>();
        for (Movie movie : allMovies) {
            movie.computeAverageRating();
            allVideos.add(movie);
        }
        for (Serial serial : allSeries) {
            serial.computeAverageRating();
            allVideos.add(serial);
        }
        return allVideos;
    }

    public void incrementVideoIndex() {
        this.VideoIndex = this.VideoIndex + 1;
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
        for (Video video : getAllVideos())
            if (video.getTitle().equals(videoName)) {
                return video;
            }
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


    // filter the list of movies after specific criteria year/genre to use it in QueryMovie
    public List<Movie> getFilteredMovies(ActionInputData query) {

        List <Movie> filteredMovies = new ArrayList<>();

        // filters criterias
        List<String> year = query.getFilters().get(0);
        List<String> genre = query.getFilters().get(1);

        for (Movie movie : this.allMovies) {
            boolean yearCheck = true;
            boolean genreCheck = true;

            if (year != null && year.get(0) != null) {
                Integer yearRequired = Integer.valueOf(year.get(0));
                yearCheck = movie.getProductionYear().equals(yearRequired);
            }
            if (genre != null && genre.get(0) != null) {
                genreCheck = movie.getGenre().containsAll(genre);
            }
            if (yearCheck && genreCheck) {
                Movie copyMovie = new Movie(movie.getTitle(), movie.getGenre(), movie.getProductionYear(), movie.getDuration(), movie.getCast(), movie.getAverageRating(), movie.getNumberViews(), movie.getNumberFavorite());
                // Movie copyMovie = new Movie(movie);
                filteredMovies.add(copyMovie);
            }
        }
        return filteredMovies;
    }

    // filter the list of series after specific criteria year/genre to use it in QuerySerial
    public List<Serial> getFilteredSeries(ActionInputData query) {
        List <Serial> filteredSeries = new ArrayList<>();

        // filters criteria
        List<String> year = query.getFilters().get(0);
        List<String> genre = query.getFilters().get(1);

        for (Serial serial : this.allSeries) {
            boolean yearCheck = true;
            boolean genreCheck = true;

            if (year != null && year.get(0) != null) {
                Integer yearRequired = Integer.valueOf(year.get(0));
                yearCheck = serial.getProductionYear().equals(yearRequired);
            }
            if (genre != null && genre.get(0) != null) {
                genreCheck = serial.getGenre().containsAll(genre);
            }

            if (yearCheck && genreCheck) {
                Serial copySerial = new Serial(serial.getTitle(), serial.getGenre(), serial.getProductionYear(), serial.getAllSeasons(), serial.getNumberofSeasons(), serial.getCast(), serial.getAverageRating(), serial.getNumberViews(), serial.getNumberFavorite());
                // Serial copySerial = new Serial(serial);
                filteredSeries.add(copySerial);
            }
        }
        return filteredSeries;
    }

    public void clearVideosDB() {
        instance = null;
        this.VideoIndex = 0;
        this.allMovies.clear();
        this.allVideos.clear();
        this.allSeries.clear();
    }
}
