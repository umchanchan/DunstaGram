import java.util.*;

/**
 * Team Project - Profile
 * <p>
 * Profile class that has attributes username, password, age, gender, follower, and followers.
 * It parses user information, constructs a profile based on a line in a file, and all follow and block implications.
 * </p>
 */

public class Profile implements IProfile {
    private String username;
    private String password;
    private ArrayList<Profile> following;
    private int age;
    private String gender;
    private ArrayList<Post> userPosts;
    private ArrayList<Profile> blockedList;

    /**
     * Profile constructor
     *
     * @param username
     * @param password
     */
    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
        this.age = 0;
        this.gender = "";
        this.following = new ArrayList<Profile>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<Profile>();
    }

    /**
     * Profile constructor
     *
     * @param username
     * @param password
     * @param age
     * @param gender
     */
    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.following = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<Profile>();
    }

    /**
     * Profile constructor
     *
     * @param username
     */
    public Profile(String username) {
        this.username = username;
        this.password = "";
        this.age = 0;
        this.gender = "";
        this.following = new ArrayList<>();
        this.userPosts = new ArrayList<>();
        this.blockedList = new ArrayList<>();
    }

    /**
     * Invalid constructor
     */
    public Profile() {
        this.username = null;
        this.age = Integer.parseInt(null);
        this.gender = null;
        this.password = null;
        this.following = null;
        this.userPosts = null;
        this.blockedList = null;



    }

    /**
     * toString method that parses the user information for file outputting
     *
     * @return result.toString - user information line
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(username).append("_").append(password).append("_")
                .append(age).append("_").append(gender).append("_");
        for (int i = 0; i < following.size(); i++) {
            result.append(following.get(i).getUsername()).append("_");
        }
        result.append("==+=="); //String that splits friend list and blocked list
        for (int i = 0; i < blockedList.size(); i++) {
            result.append(blockedList.get(i)).append("_");
        }

        return result.toString();
    }

    /**
     * makeProfile method that constructors a new profile based on the information sent from Base class
     * @param userInfo - a string line passed from Base class
     * @return newProfile
     */
    public Profile makeProfile(String userInfo) {
        String[] firstParse = userInfo.split("==+==");
        String basicInfo = firstParse[0];
        String blockList = firstParse[1];

        String[] parts = basicInfo.split("_");
        String username = parts[0];
        String password = parts[1];
        int age = Integer.parseInt(parts[2]);

        String gender = parts[3];
        Profile newProfile = new Profile(username, password, age, gender);
        for (int i = 4; i < parts.length; i++) {
            newProfile.following.add(new Profile(parts[i]));
        }

        String[] parts2 = blockList.split("_");
        for (String blockName : parts2) {
            newProfile.blockedList.add(new Profile(blockName));
        }

        return newProfile;
    }

    /**
     * equals method that returns boolean if input profile has the same attributes with this profile
     * @param toCompare - profile to compare
     * @return true if they are equal
     */
    public boolean equals(Profile toCompare) {
        return username.equals(toCompare.getUsername()) &&
                password.equals(toCompare.getPassword()) &&
                gender.equals(toCompare.getGender()) &&
                age == toCompare.getAge();
    }

    public void addMyPost(Post post) {
        userPosts.add(post);
    }

    public void removeMyPost(Post post) {
        userPosts.remove(post);
    }

    public void follow(Profile p) {
        following.add(p);
    }

    public void unfollow(Profile p) {
        following.remove(p);
    }

    /**
     *
     * @param follow - profile to check if this profile follows
     * @return
     */
    public boolean isFollowing(Profile follow) {
        return this.following.contains(follow);
    }

    public boolean blockUser(Profile user) { //assumes input is a valid user
        if (following.contains(user) || !blockedList.contains(user)) {
            user.unfollow(this);
            blockedList.add(user);
            return true;
        }
        return false;
    }

    public void unblockUser(Profile unblock) {
        blockedList.remove(unblock);
    }


    /**
     * Getters and Setters
     */
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<Profile> getFollowing() {
        return this.following;
    }

    public ArrayList<Post> getMyPosts() {
        return this.userPosts;
    }

    public ArrayList<Profile> getBlockedList() {
        return this.blockedList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
