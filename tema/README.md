                                                        Homework OOP - VideosDB
                                                    Madalina-Valentina Zanficu 323CA

    ---MAIN IDEA---

Developed a simplified platform that offers information about movies, series, actors and users.

The input data is loaded from JSON files as entry in tests executed by the checker
(design by the OOP team).
Fileio package consists of helpful classes (designed by the OOP team) that contains input
data for different type of objects such as: action data, actor data, movie data and 
others. Used these classes as guidance to create my own custom ones in order to apply 
specific actions on them.

The platform is designed to respond to different types of actions required by their users,
such as:
- commands
- queries
- recommendations


    ---CUSTOM CLASSES AND DESIGN---

In order to maintain the code cleaner and to respect the OOP design,
the structure of the project is:

* Actions(package)
-> Commands - executeCommand method is implemented in order to get the type of 
              the command and to call the specific method to execute it.

-> Queries - executeQuery method is implemented in order to get the object type on
              which the query will be executed (actor/movie/serial/user)
            - designed separated classes for each object type: -> QueriesActor
                                                               -> QueriesMovie
                                                               -> QueriesSerial
                                                               -> QueriesUser

-> Recommendations - executeRecommendation used for getting the recommendation type

* Databases(package)
For all my databases I decided to apply the Singleton pattern in order
to maintain a single instance of the class. This assures the safety that 
the input information is stored in only one place.

-> ActorsDB 
+ a list of actors given from the input
+ methods for sorting (deep-copied) list of actors, used in QueriesActors 

-> UsersDB
+ a list of users given from the input

-> VideosDB
+ a list of all videos (both movies and series)
+ a list of movies
+ a list of series
+ a videoIndex - to record the order of the videos in the database
+ methods for filtering the series/movies by specific criteria


* Entertainment(package)
Both Movie class and Serial class inherit the Video class
due to the common attributes of the classes.
-> Video
-> Movie
-> Serial


* Entities(package)
-> Actor
+ specific attributes for actor(name, carrerDescription, filmography) given in input
+ averageVideoRating - computed in order to resolve QueriesActor


-> User
+ specific attributes for user(username, subscription type, history map, favorite list)
+ method for adding videos to favorite list of the user
+ method for adding videos to history map of the user
+ methods for rating videos seen by the user


    ---FLOW OF THE PROGRAM---

FOR THE COMMANDS:
1. FavoriteCommand -> add a video to a specific user favorite list
2. ViewCommand -> add a video to a specific user history
3. RatingCommand -> the specific user gives rating to a video 

-> All the actions are applied directly on users, and indirectly
on videos.
-> In this way, I used the users' database to get the specific user
to apply different operations (directly in user class)
-> Indirectly when a video is rated by the user, there exists
modifications on video/movie/serial classes as well.

FOR THE QUERRIES:
-> In order to sort the objects I used Java8 Comparator interface
to sort Collections by multiple fields

-> QueriesActor consists of 3 queries:
1. executeQueryAverage -> N actors sorted by the average rating of the 
videos they played in
2. executeQueryAwards -> actors sorted by the number of awards 
(with specific awards required)
3. executeQueryFD-> actors sorted by keywords in Filter Description 
(with specific keywords required)

-> QueriesMovie/QueriesSerial
I used the methods implemented in VideosDB to get filtered 
movies/series by specific criteria.

1. executeQueryRatings -> sorted movies/series by averageRating
2. executeQueryFavorite -> sorted movies/series by the number of 
"likes" from users
3. executeQueryLongest -> sorted movies/series by total duration
4. executeQueryMostViewed -> sorted movies/series by number of views

-> QueriesUser
1. executeQueryNrRatings -> sorted users by the number of reviews given


For recommendations:
-> There exists 2 categories of recommendation based on the
subscription type of the user
-> In every method I filtered all the videos from VideosDB
(not only movies or series) to make personal recommendation
based on the specific user history.

For sorting, I used Java8 Comparator interface as well, 
but also for executePopularRecommendation, I designed a new Comparator 
and override the compare method to compare the value field from my map entry.