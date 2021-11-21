package databases;

import entities.User;
import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// maintain a Singleton Database for each type of input: in this case users
public class UsersDB {

    // list of users
    private List<User> allUsers;
    public static UsersDB instance = null;

    public static UsersDB getInstance() {
        if (instance == null) {
            instance = new UsersDB();
        }
        return instance;
    }

    // constructor
    private UsersDB() {
        this.allUsers = new ArrayList<User>();
    }

    // bring information for users from input
    public void setUsersDB(Input input) {
        // iterate through the list of users from input and add it to my own database
        for (UserInputData inputUser : input.getUsers()) {
            User newUser = new User(inputUser.getUsername(), inputUser.getSubscriptionType(), inputUser.getHistory(), inputUser.getFavoriteMovies());
            this.allUsers.add(newUser);
        }
    }
    // used in Commands to get a user by username
    public User getSpecificUser(String username) {
       for (User user : allUsers) {
           if (user.getUsername().equals(username)) {
               return user;
           }
       }
       // user not found
       return null;
    }
}
