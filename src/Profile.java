import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

/*
This is the profile class of Dunstagram that has some information.

@ version 7/8/24

@author Mukund Rao
 */

public class Profile implements IProfile {
    private String username;
    private String password;
    private ArrayList<Profile> followers;
    private ArrayList<Profile> following;
    private int age;
    private String gender;
    private ArrayList<Post> userPosts;
    private ArrayList<Profile> blockedList;
    private ArrayList<Comment> comments;

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

        this.followers = new ArrayList<Profile>();
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

        this.followers = new ArrayList<Profile>();
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

        this.followers = new ArrayList<>();
        this.userPosts = new ArrayList<>();
        this.blockedList = new ArrayList<>();
    }

    /**
     * Invalid constructor
     */
    public Profile() {
        this.username = "Profile_Not_Found";
        this.age = -1;
        this.gender = "Null";

    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(username).append("_").append(password).append("_")
                .append(age).append("_").append(gender).append("_");
        for (int i = 0; i < followers.size(); i++) {
            result.append(followers.get(i).getUsername()).append("_");
        }
        result.append("==+=="); //String that splits friend list and blocked list
        for (int i = 0; i < blockedList.size(); i++) {
            result.append(blockedList.get(i)).append("_");
        }

        return result.toString();
    }

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
            newProfile.followers.add(new Profile(parts[i]));
        }

        String[] parts2 = blockList.split("_");
        for (String blockName : parts2) {
            newProfile.blockedList.add(new Profile(blockName));
        }

        return newProfile;
    }

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
        p.getFollowed(this);
        following.add(p);
    }

    public void unfollow(Profile p) {
        following.remove(p);
        p.getFollowers().remove(this);
    }

    public boolean removeFollowers(Profile f) {
        if (followers.contains(f)) {
            followers.remove(f);
            return true;
        }
        return false;
    }

    public boolean blockUser(Profile user) { //assumes input is a valid user
        if (followers.contains(user) || !blockedList.contains(user)) {
            if (user.getFollowers().contains(this)) {
                user.removeFollowers(this);
            }
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

    public ArrayList<Profile> getFollowers() {
        return followers;
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

    public void getFollowed(Profile p) {
        this.followers.add(p);
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
