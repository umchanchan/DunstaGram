/*
This is the profile class of Dunstagram that has some information.

@ version 7/8/24

@author Mukund Rao
 */

import java.util.ArrayList;

public class Profile {
    private String username;
    private String password;
    private int followers;
    private ArrayList<Profile> friends;
    private int age;
    private String gender;
    private ArrayList<Profile> friendRequests;

    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;

        this.followers = 0;
        this.friends = new ArrayList<Profile>();
        this.friendRequests = new ArrayList<Profile>();
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

    public void setFriendRequests(Profile profile) {
        this.friendRequests.add(profile);
    }
    //other methods (will be explained)

    public boolean isFriends(Profile firstUser, Profile secondUser) {
        return false;
    }

    public void addFriend(Profile profile) {
        profile.setFriendRequests(this);
    }
}
