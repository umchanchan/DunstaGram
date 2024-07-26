import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Team Project - Profile
 * <p>
 * Profile class that has attributes username, password, age, gender, follower, and followers.
 * It parses user information, constructs a profile based on a line in a file, and all follow and block implications.
 * </p>
 */

public class Profile implements IProfile, Serializable {
    private String username;
    private String password;
    private ArrayList<String> following;
    private int age;
    private String gender;
    private ArrayList<Post> userPosts;
    private ArrayList<Post> followingPosts;
    private ArrayList<Post> hidePosts;
    private ArrayList<String> blockedList;

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
        this.following = new ArrayList<>();
        this.followingPosts = new ArrayList<>();
        this.hidePosts = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<>();
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
        this.followingPosts = new ArrayList<>();
        this.hidePosts = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<>();
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
        this.followingPosts = new ArrayList<>();
        this.hidePosts = new ArrayList<>();
        this.userPosts = new ArrayList<>();
        this.blockedList = new ArrayList<>();
    }

    /**
     * Invalid constructor
     */
    public Profile() {
        this.username = null;
        this.age = 0;
        this.gender = null;
        this.password = null;
        this.following = null;
        this.hidePosts = null;
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
            result.append(following.get(i)).append("_");
        }

        if (!blockedList.isEmpty()) {
            result.append("===="); //String that splits friend list and blocked list
            for (int i = 0; i < blockedList.size(); i++) {
                result.append(blockedList.get(i)).append("_");
            }

        }

        return result.toString();
    }

    /**
     * makeProfile method that constructors a new profile based on the information sent from Base class
     *
     * @param userInfo - a string line passed from Base class
     * @return newProfile
     */
    public Profile makeProfile(String userInfo) {
        String[] firstParse = userInfo.split("====");
        String basicInfo = firstParse[0];
        String blockList = "";
        if (firstParse.length > 1) {
            blockList = firstParse[1];
        }

        String[] parts = basicInfo.split("_");
        String username = parts[0];
        String password = parts[1];
        int age = Integer.parseInt(parts[2]);

        String gender = parts[3];
        Profile newProfile = new Profile(username, password, age, gender);
        for (int i = 4; i < parts.length; i++) {
            newProfile.getFollowing().add(parts[i]);
        }

        if (!blockList.isEmpty()) {
            String[] parts2 = blockList.split("_");

            for (String blockName : parts2) {
                newProfile.blockedList.add(blockName);
            }
        }

        return newProfile;
    }

    /**
     * equals method that returns boolean if input profile has the same attributes with this profile
     *
     * @param toCompare - profile to compare
     * @return true if they are equal
     */
    public boolean equals(Profile toCompare) {
        return username.equals(toCompare.getUsername()) &&
                password.equals(toCompare.getPassword()) &&
                gender.equals(toCompare.getGender()) &&
                age == toCompare.getAge();
    }

    public void startHidePostList(String info, Base base) {
        String[] parts = info.split("_");
//        if (!(username.equals(parts[0])) && hidePosts.isEmpty()) {
//            fillFollowingPosts(base);
//            return;
//        }

        if (!(username.equals(parts[0]))) {
            return;
        }
        fillFollowingPosts(base);

        for (Post post : followingPosts) {
            if (post.getPoster().getUsername().equals(parts[1]) && post.getMessage().equals(parts[2])) {
                NewsFeed newsFeed = new NewsFeed(this);
                newsFeed.hidePost(post);
                hidePosts.add(post);
                followingPosts.remove(post);
                break;
            }
        }
    }

    public ArrayList<String> hidePostToString() {
        ArrayList<String> hideString = new ArrayList<>();
        for (Post post : hidePosts) {
            hideString.add(username + "_" + post.getPoster().getUsername() + "_" + post.getMessage() +
                    "_" + post.getUpvotes() + "_" + post.getDownvotes());
        }
        return hideString;
    }

    public void addMyPost(Post post) {
        userPosts.add(post);
    }

    public void removeMyPost(Post post) {
        userPosts.remove(post);
    }

    public void hidePost(String poster, String message) {

        System.out.println(followingPosts.size());
        for (Post post2 : followingPosts) {
            if (post2.getPoster().getUsername().equals(poster) && post2.getMessage().equals(message)) {
                followingPosts.remove(post2);
                hidePosts.add(post2);
                break;
            }
        }
        System.out.println(followingPosts.size());

    }

    public void unHidePost(Post post) {
        NewsFeed newsFeed = new NewsFeed(this);
        newsFeed.hidePost(post);
        followingPosts.add(post);
        hidePosts.remove(post);
    }

    public void follow(Profile p) {
        following.add(p.getUsername());
    }

    public void unfollow(Profile p) {
        for (String username : following) {
            if (username.equals(p.getUsername())) {
                following.remove(username);
                break;
            }
        }
    }

    /**
     * @param follow - profile to check if this profile follows
     * @return
     */
    public boolean isFollowing(Profile follow) {
        return isFollowing(follow.getUsername());
    }

    public boolean isFollowing(String follow) {
        for (String username : following) {
            if (username.equals(follow)) {
                return true;
            }
        }
        return false;
    }

    public boolean blockUser(Profile user) { //assumes input is a valid user
        boolean isFollow = false;
        boolean isBlock = false;
        for (String username : following) {
            if (username.equals(user.getUsername())) {
                isFollow = true;
                break;
            }
        }
        for (String block : blockedList) {
            if (block.equals(user.getUsername())) {
                isBlock = true;
                break;
            }
        }
        if (!isBlock) {
            blockedList.add(user.getUsername());
        } else {
            return false;
        }
        if (isFollow) {
            this.unfollow(user);
        }
        return true;
    }

    public void unblockUser(Profile unblock) {
        for (String block : blockedList) {
            if (block.equals(unblock.getUsername())) {
                blockedList.remove(block);
                break;
            }
        }
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

    public ArrayList<String> getFollowing() {
        return this.following;
    }

    public ArrayList<Post> getMyPosts() {
        return this.userPosts;
    }

    public void fillFollowingPosts(Base base) {
        NewsFeed myNewsFeed = new NewsFeed(this);
        for (String friend : following) {
            followingPosts = myNewsFeed.filterPost(friend, base, hidePosts);
        }
    }

    public ArrayList<Post> getFollowingPosts() {
        return followingPosts;
    }

    public ArrayList<Post> getHidePosts() {
        return hidePosts;
    }

    public ArrayList<String> getBlockedList() {
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

    public void setPassword(String password) {
        this.password = password;
    }

}
