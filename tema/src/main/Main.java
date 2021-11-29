package main;

import actions.Commands;
import actions.Queries;
import actions.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import databases.ActorsDB;
import databases.UsersDB;
import databases.VideosDB;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        // clear the databases
        UsersDB.getInstance().clearUsersDB();
        ActorsDB.getInstance().clearActorDB();
        VideosDB.getInstance().clearVideosDB();

        // keep the input information in databases for every objectType
        UsersDB users = UsersDB.getInstance();
        ActorsDB actors = ActorsDB.getInstance();
        VideosDB videos = VideosDB.getInstance();

        VideosDB.getInstance().setAllMovies(input);
        VideosDB.getInstance().setAllShows(input);

        // to set databases information
        UsersDB.getInstance().setUsersDB(input);
        ActorsDB.getInstance().setActorsDB(input);

        // actions required
        List<ActionInputData> actions = input.getCommands();

        for (ActionInputData action : actions) {
            // getActionType => command / recommendations / query

            if (action.getActionType().equals("command")) {
                // used Command class to make different commands
                Commands command = new Commands(action);
                String outputMessage = command.executeCommand();
                JSONObject result = fileWriter.writeFile(action.getActionId(), "", outputMessage);
                arrayResult.add(result);
            }
            if (action.getActionType().equals("recommendation")) {
                // used Recommendation class to make different recommendations
                Recommendation recommendation = new Recommendation(action);
                String outputMessage = recommendation.executeRecommendation();
                JSONObject result = fileWriter.writeFile(action.getActionId(), "", outputMessage);
                arrayResult.add(result);
            }
            if (action.getActionType().equals("query")) {
                // used Query class to manipulate queries
                Queries query = new Queries(action);
                String outputMessage = query.executeQuery();
                JSONObject result = fileWriter.writeFile(action.getActionId(), "", outputMessage);
                arrayResult.add(result);
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
