import java.util.*;
import java.io.*;

/*
This is the profile class of Dunstagram that has some information.

@ version 7/8/24

@author Mukund Rao
 */


// login, signup, friend interaction, add or remove posts and comments
//

public class Profile implements IProfile {
    private String username;
    private String password;
    private int followers;
    private ArrayList<Profile> friends;
    private int age;
    private String gender;
    private ArrayList<Profile> friendRequests;
    private int numFriends;
    private ArrayList<Post> userPosts;
    private ArrayList<Profile> blockedList;
    private ArrayList<Profile> users;

    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
        this.age = 0;
        this.gender = "";

        this.followers = 0;
        this.numFriends = 0;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<Profile>();
    }

    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;

        this.followers = 0;
        this.numFriends = 0;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<Profile>();
    }

    public Profile(String username) {
        this.username = username;
        this.password = "";
        this.age = 0;
        this.gender = "";

        this.followers = 0;
        this.numFriends = 0;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.userPosts = new ArrayList<>();
        this.blockedList = new ArrayList<>();
    }
    //invalid constructor

    public Profile() {
        this.username = "Profile_Not_Found";
        this.age = -1;
        this.gender = "Null";

    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(username).append("_").append(password).append("_")
                .append(age).append("_").append(gender).append("_");
        for (int i = 0; i < friends.size(); i++) {
            result.append(friends.get(i)).append("_");
        }

        return result.toString();
    }

    public Profile makeProfile(String userInfo) {
        String[] parts = userInfo.split("_");
        String username = parts[0];
        String password = parts[1];
        int age = Integer.parseInt(parts[2]);
        String gender = parts[3];
        Profile newProfile = new Profile(username, password, age, gender);
        for (int i = 4; i < parts.length; i++) {
            newProfile.friends.add(new Profile(parts[i]));
        }
        return newProfile;
    }

    //getters
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public int getFollowers() {
        return followers;
    }


    public ArrayList<Profile> getFriends() {
        return friends;
    }


    public int getAge() {
        return age;
    }


    public String getGender() {
        return gender;
    }


    public ArrayList<Profile> getFriendRequests() {
        return friendRequests;
    }


    public ArrayList<Post> getMyPosts() {
        return this.userPosts;
    }


    public ArrayList<Profile> getBlockedList() {
        return this.blockedList;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }


    public void setFollowers(int followers) {
        this.followers = followers;
    }


    public void setFriends(ArrayList<Profile> friends) {
        this.friends = friends;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setFriendRequests(Profile profile) { //Adds this profile to user's friend requests
        this.friendRequests.add(profile);
    }

    //friend methods

    public void addFriend(Profile profile) { //sends a friend request to the desired user.
        profile.setFriendRequests(this);
    }


    public boolean isFriends(Profile profile) {
        return friends.contains(profile) && profile.getFriends().contains(this);
    }


    /**
     * After this method is used writeUserList method must be implemented to update the file.
     */
    public boolean acceptRequest(Profile friend) {
        //accepts requests of the user, removes that user from the list of friend requests

        if (!friendRequests.contains(friend)) {
            return false;
        }
        friends.add(friend);
        friend.friends.add(this);
        friendRequests.remove(friend);

        return true;
    }


    public boolean rejectRequest(Profile profile) {
        if (!friendRequests.contains(profile)) {
            return false;
        }
        this.friendRequests.remove(profile);
        return true;
    }

    /**
     * After this method is used writeUserList method must be implemented to update the file.
     */
    public void removeFriend(Profile f) {
        if (f.isFriends(this)) {
            friends.remove(f);
            f.removeFriend(this);
        } else return;
    }


    public void blockUser(Profile user) {
        if (user.isFriends(this)) {
            this.removeFriend(user);
            user.removeFriend(this);
            blockedList.add(user);
        } else {
            blockedList.add(user);
        }
    }

    //post methods

    public void makePost(String msg) {
        Post newPost = new Post(msg, this);
        newPost.setMessage(msg);
        this.userPosts.add(newPost);
    }

    public void upVote(Post post) { //you can also do this with comments since it's a subclass :)
        post.setUpvotes(post.getUpvotes() + 1);
    }

    public void downVote(Post post) {
        post.setDownvotes(post.getDownvotes() - 1);
    }

    public void comment(Post post, String msg) {
        post.addComment(this, msg);
    }


}
