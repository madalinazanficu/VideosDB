package actions;

import databases.UsersDB;
import entities.User;
import fileio.ActionInputData;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QueriesUser {
    public static String executeQueryforUser(ActionInputData query) {
        if (query.getCriteria().equals("num_ratings")) {
            List<User> sortedUsers = executeQueryNrRatings(query);
            return printSortedLists(sortedUsers, query.getNumber());
        }
        return "Query result: []";
    }
    public static List<User> executeQueryNrRatings(ActionInputData query) {
        List<User> users = UsersDB.getInstance().getAllUsers();
        List<User> sortedUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getNrRatings() != 0) {
                sortedUsers.add(new User(user.getUsername(), user.getSubscription(), user.getHistory(), user.getFavorite(), user.getNrRatings()));
            }
        }
        Collections.sort(sortedUsers, Comparator.comparing(User::getNrRatings).thenComparing(User::getUsername));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedUsers);
        }

        return sortedUsers;
    }
    public static String printSortedLists(List <User> sortedList, int n) {
        String output = "Query result: [";

        List <User> nSortedList = new ArrayList<>();
        int i;
        for (i = 0; i < n && i < sortedList.size(); i++) {
            nSortedList.add(sortedList.get(i));
        }
        for (i = 0; i < nSortedList.size() - 1; i++) {
            output = output + nSortedList.get(i).getUsername()+ ", ";
        }
        if (nSortedList.size() != 0)
            output = output + nSortedList.get(i).getUsername() + "]";
        else
            output = output + "]";

        return output;
    }
}
