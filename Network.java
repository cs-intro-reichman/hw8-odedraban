/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name == null) return null;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) return users[i];
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (name == null) {
            System.out.println("\n...Can't follow a null user...");
            return false;
        }
        else if (users.length == userCount) {
            System.out.println("\n...Can't add " + name + " to full network... ");
            return false;
        }
        else if (getUser(name) != null) {
            System.out.println("\n...Can't add " + name + " again the network... ");
            return false;
        }
        else {
            users[userCount] = new User (name);
            userCount++;
            return true;
        }
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null) {
            System.out.println("\n...Can't do this action with a null user...");
            return false;
        }
        if (name1.toLowerCase().equals(name2.toLowerCase())) {
            System.out.println("\n...Can't do this action for the same user...");
            return false;
        }
        if (getUser(name1) == null || getUser(name2) == null) {
            System.out.println("\n...Can't do this action with user that doesn't exict in the list...");
            return false;
        }
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        return user1.addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (name == null) return null;
        if (user == null) return null;
        User mostRecommendedUserToFollow = null;
        int maxMatualNum = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null) {
                if (user.getName().equals(users[i].getName())) continue;
                if (users[i].countMutual(getUser(name)) > maxMatualNum) {
                    mostRecommendedUserToFollow = users[i];
                    maxMatualNum = users[i].countMutual(getUser(name));
                }
            }
        }
        return mostRecommendedUserToFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) return null;
        User mostPopular = null;
        int maxFolowees = 0;
        for (int i = 0; i < userCount; i++) {
            if (mostPopular == null || followeeCount(users[i].getName()) > maxFolowees) {
                mostPopular = users[i];
                maxFolowees = followeeCount(users[i].getName());
            }
        }
        return mostPopular.getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount (String name) {
        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name) && !users[i].getName().equals(name)) counter++;
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String str = "Network:";
       for (int i = 0; i < userCount; i++) {
            str += "\n" + users[i].toString();
        }
       return str;
    }

 }
 
