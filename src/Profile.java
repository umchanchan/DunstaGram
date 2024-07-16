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

    //private ArrayList<Profile> friends;
    //  private ArrayList<Profile> friendRequests;

    private int age;
    private String gender;

    //  private int numFriends;
    private ArrayList<Post> userPosts;
    private ArrayList<Profile> blockedList;

    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
        this.age = 0;
        this.gender = "";

        this.followers = new ArrayList<Profile>();
        //   this.numFriends = 0;
        //    this.friends = new ArrayList<>();
        //    this.friendRequests = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<Profile>();
    }

    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;

        this.followers = new ArrayList<Profile>();
        //  this.numFriends = 0;
        //   this.friends = new ArrayList<>();
        //  this.friendRequests = new ArrayList<>();
        this.userPosts = new ArrayList<Post>();
        this.blockedList = new ArrayList<Profile>();
    }

    public Profile(String username) {
        this.username = username;
        this.password = "";
        this.age = 0;
        this.gender = "";

        this.followers = new ArrayList<>();
        //  this.numFriends = 0;
        //  this.friends = new ArrayList<>();
        //  this.friendRequests = new ArrayList<>();
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
        for (int i = 0; i < followers.size(); i++) {
            result.append(followers.get(i).getUsername()).append("_");
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
            newProfile.followers.add(new Profile(parts[i]));
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


    public ArrayList<Profile> getFollowers() {
        return followers;
    }


  /*  public ArrayList<Profile> getFriends() {
        return friends;
    }*/


    public int getAge() {
        return age;
    }


    public String getGender() {
        return gender;
    }


/*    public ArrayList<Profile> getFriendRequests() {
        return friendRequests;
    }*/


    public ArrayList<Post> getMyPosts() {
        return this.userPosts;
    }

    public void addMyPost(Post post) {
        userPosts.add(post);
    }

    public void removeMyPost(Post post) {
        userPosts.remove(post);
    }

    public ArrayList<Profile> getBlockedList() {
        return this.blockedList;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }


    public void getFollowed(Profile p) {
        this.followers.add(p);
    }

    public boolean removeFollowers(Profile f) {
        if (followers.contains(f)) {
            followers.remove(f);
            return true;
        }
        return false;
    }

  /* public void setFriends(ArrayList<Profile> friends) {
        this.friends = friends;
    }*/


    public void setAge(int age) {
        this.age = age;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


   /* public void setFriendRequests(Profile profile) { //Adds this profile to user's friend requests
        this.friendRequests.add(profile);
    }

    //friend methods

   public void addFriend(Profile profile) { //sends a friend request to the desired user.
        profile.setFriendRequests(this);
    }


    public boolean isFriends(Profile profile) {
        return friends.contains(profile) && profile.getFriends().contains(this);
    }

*/

    /**
     * After this method is used writeUserList method must be implemented to update the file.
     *
     * @return
     */ /*
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

    public void removeFriend(Profile f) {
        if (f.isFriends(this)) {
            friends.remove(f);
            f.removeFriend(this);
        } else return;
    }

*/
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


    public void follow(Profile p) {
        p.getFollowed(this);
        following.add(p);
    }

    public void unfollow(Profile p) {
        following.remove(p);
        p.getFollowers().remove(this);
    }


}
