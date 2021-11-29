package databases;

import entities.User;
import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public final class UsersDB {

    private final List<User> allUsers;
    private static UsersDB instance = null;

    /**
     * Method used for creating and to getting the instance of Database (Singleton pattern)
     * @return the instance of Users' Database
     */
    public static UsersDB getInstance() {
        if (instance == null) {
            instance = new UsersDB();
        }
        return instance;
    }
    public List<User> getAllUsers() {
        return this.allUsers;
    }
    private UsersDB() {
        this.allUsers = new ArrayList<User>();
    }

    /**
     * Method used for bringing the information from input to database
     * @param input - input information to set the list of users
     */
    public void setUsersDB(final Input input) {
        // iterate through the list of users from input and add it to my own database
        for (UserInputData inputUser : input.getUsers()) {
            User newUser = new User(inputUser.getUsername(), inputUser.getSubscriptionType(),
                                    inputUser.getHistory(), inputUser.getFavoriteMovies());
            this.allUsers.add(newUser);
        }
    }

    /**
     * Method used for getting a User entity from users' database
     * Used in Commands and not only
     * @param username - the requested information for searching the user
     * @return the user or null in case username is not assign with any user
     */
    // used in Commands to get a user by username
    public User getSpecificUser(final String username) {
       for (User user : allUsers) {
           if (user.getUsername().equals(username)) {
               return user;
           }
       }
       // user not found
       return null;
    }

    /**
     *  Method used to clear the information from users' database
     *  Helped to reuse safely the database
     */
    public void clearUsersDB() {
        this.allUsers.clear();
    }
}
