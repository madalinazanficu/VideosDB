package actions;

import databases.UsersDB;
import entities.User;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class QueriesUser {
    private QueriesUser() { }
    /**
     * Method used for getting the criteria type of sorting for user
     * @param query - input data to extract information
     * @return the output message
     */
    public static String executeQueryforUser(final ActionInputData query) {
        if (query.getCriteria().equals("num_ratings")) {
            List<User> sortedUsers = executeQueryNrRatings(query);
            return printSortedLists(sortedUsers, query.getNumber());
        }
        return "Query result: []";
    }

    /**
     * Method used for sorting the users by the number of rating given
     * Added in sorted users list only the users with nr rating different with 0
     * @param query - input data to extract information
     * @return the list of sorted users
     */
    public static List<User> executeQueryNrRatings(final ActionInputData query) {
        List<User> users = UsersDB.getInstance().getAllUsers();
        List<User> sortedUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getNrRatings() != 0) {
                sortedUsers.add(new User(user));
            }
        }
        Collections.sort(sortedUsers, Comparator.comparing(User::getNrRatings)
                    .thenComparing(User::getUsername));
        if (query.getSortType().equals("desc")) {
            Collections.reverse(sortedUsers);
        }
        return sortedUsers;
    }

    /**
     * Method used for printing the users
     * @param sortedList - list needed to be printed
     * @param n - number of elements from the list requested to be printed
     * @return the output message
     */
    public static String printSortedLists(final List<User> sortedList, final int n) {
        String output = "Query result: [";

        List<User> nSortedList = new ArrayList<>();
        int i;
        for (i = 0; i < n && i < sortedList.size(); i++) {
            nSortedList.add(sortedList.get(i));
        }
        for (i = 0; i < nSortedList.size() - 1; i++) {
            output = output + nSortedList.get(i).getUsername() + ", ";
        }
        if (nSortedList.size() != 0) {
            output = output + nSortedList.get(i).getUsername() + "]";
        } else {
            output = output + "]";
        }

        return output;
    }
}
