package databases;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;


public final class VideosDB {

    private static VideosDB instance = null;

    private Integer videoIndex;

    // list for all videos both movies or series
    private final List<Video> allVideos;

    // list only for movies
    private final List<Movie> allMovies;

    // list only for shows
    private final List<Serial> allSeries;

    /**
     * Method used for creating and to getting the instance of Database (Singleton pattern)
     * @return the instance of Videos' Database
     */
    public static VideosDB getInstance() {
        if (instance == null) {
            instance = new VideosDB();
        }
        return instance;
    }


    // construct the database for videos
    private VideosDB() {
        this.videoIndex = 0;
        this.allVideos = new ArrayList<Video>();
        this.allMovies = new ArrayList<Movie>();
        this.allSeries = new ArrayList<Serial>();
    }

    /**
     * Method used for bringing the information from input to database
     * @param input - input information to set the list of movies
     */
    public void setAllMovies(final Input input) {
        for (MovieInputData movieInput : input.getMovies()) {
            Movie newMovie = new Movie(movieInput.getTitle(), movieInput.getGenres(),
                    movieInput.getYear(), movieInput.getDuration(), movieInput.getCast(),
                    0.0, 0, 0, this.videoIndex);

            // add in the list of movies
            this.allMovies.add(newMovie);

            // increment the VideoIndex
            incrementVideoIndex();
        }

    }


    /**
     * Method used for bringing the information from input to database
     * @param input - input information to set the list of series
     */
    public void setAllShows(final Input input) {
        for (SerialInputData serialInput : input.getSerials()) {

            Serial newSerial = new Serial(serialInput.getTitle(), serialInput.getGenres(),
                    serialInput.getYear(), serialInput.getSeasons(), serialInput.getNumberSeason(),
                    serialInput.getCast(), 0.0, 0, 0, videoIndex);

            // add in the list of series
            this.allSeries.add(newSerial);

            // increment the VideoIndex
            incrementVideoIndex();
        }

    }

    /**
     * @return the list of all videos (both movies and series)
     */
    public List<Video> getAllVideos() {
        List<Video> videos = new ArrayList<>();
        for (Movie movie : this.allMovies) {
            movie.computeAverageRating();
            videos.add(movie);
        }
        for (Serial serial : this.allSeries) {
            serial.computeAverageRating();
            videos.add(serial);
        }
        return videos;
    }

    /**
     * Method used for incrementing the number of videos added in Videos databse
     * Method is called in setAllShows and setAllMovies
     */
    public void incrementVideoIndex() {
        this.videoIndex = this.videoIndex + 1;
    }

    /**
     * Method used for getting the type of video
     * The video could be: movie or serial
     * Method is called in Commands/ratingCommand to rate the videos
     * @param video - the name of the video requested
     * @return the type of the video
     */
    public String videoType(final String video) {
        // search in allMovies
        for (Movie movie : allMovies) {
            if (movie.getTitle().equals(video)) {
                return "Movie";
            }
        }
        // search in allSeries
        for (Serial serial: allSeries) {
            if (serial.getTitle().equals(video)) {
                return "Serial";
            }
        }
        return "Video type not found";
    }

    /**
     * Method used for getting a Video from videos' database
     * @param videoName - the requested name for searching the video
     * @return the video or null in case videoName is not assign with any video
     */
    public Video getSpecificVideo(final String videoName) {
        for (Video video : getAllVideos()) {
            if (video.getTitle().equals(videoName)) {
                return video;
            }
        }
        // video not found
        return null;
    }

    /**
     * Method used for getting a Serial from videos' database
     * @param serialName - the requested name of the Serial
     * @return the serial of null in case serialName is not assign with any serial
     */
    public Serial getSpecificSerial(final String serialName) {
        for (Serial serial : allSeries) {
            if (serial.getTitle().equals(serialName)) {
                return serial;
            }
        }

        // serial not found
        return null;
    }

    /**
     * Method used for getting a Movie from videos' database
     * @param movieName - the requested name of the Movie
     * @return the movie or null in case movieName is not assign with any movie
     */
    public Movie getSpecificMovie(final String movieName) {
        for (Movie movie : allMovies) {
            if (movie.getTitle().equals(movieName)) {
                return movie;
            }
        }
        // movie not found
        return null;
    }


    /**
     * Method used for filtering the list of movies after specific criteria (year/genre)
     * Method is called in QueryMovies
     * @param query - input information
     * @return the list of filtered movies
     */
    public List<Movie> getFilteredMovies(final ActionInputData query) {

        List<Movie> filteredMovies = new ArrayList<>();

        // filters criteria
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
                Movie copyMovie = new Movie(movie);
                filteredMovies.add(copyMovie);
            }
        }
        return filteredMovies;
    }

    /**
     * Method used for filtering the list of series after specific criteria (year/genre)
     * Method is called in QuerySerial
     * @param query - input information
     * @return the list of filtered movies
     */

    public List<Serial> getFilteredSeries(final ActionInputData query) {
        List<Serial> filteredSeries = new ArrayList<>();

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
                Serial copySerial = new Serial(serial);
                filteredSeries.add(copySerial);
            }
        }
        return filteredSeries;
    }

    /**
     *  Method used to clear the information from users' database
     *  Helped to reuse safely the database
     */
    public void clearVideosDB() {
        this.videoIndex = 0;
        this.allMovies.clear();
        this.allVideos.clear();
        this.allSeries.clear();
    }
}
