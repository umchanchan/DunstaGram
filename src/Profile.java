import java.util.*;
import java.io.*;

/*
This is the profile class of Dunstagram that has some information.

@ version 7/8/24

@author Mukund Rao
 */


// login, signup, friend interaction, add or remove posts and comments
//

public class Profile {
    private String username;
    private String password;
    private int followers;
    private ArrayList<Profile> friends;
    private int age;
    private String gender;
    private ArrayList<Profile> friendRequests;
    private int numFriends;

    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.friends = new ArrayList<>();

        this.followers = 0;
        this.numFriends = 0;
        this.friends = new ArrayList<Profile>();
        this.friendRequests = new ArrayList<Profile>();
    }

    public boolean signUp(String username, String password) throws IOException {
        File f = new File("userList.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        String line;
        while ((line = bfr.readLine()) != null) {
            String[] parts = line.split("_");
            if (parts.length > 1) {
                String fileUsername = parts[0];
                String filePassword = parts[1];
                if (fileUsername.equals(username)) {
                    System.out.println("username not available");
                    bfr.close();
                    return false;
                }
            }
        }
        bfr.close();

        Profile newProfile = new Profile(username, password, age, gender);

        FileWriter fw = new FileWriter(f, true);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(username + "_" + password);
        bfw.newLine();
        bfw.close();

        return true;
    }

    public boolean login(String username, String password) throws IOException {
        String combinedString = username + "_" + password;

        File f = new File("userList.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        String line;
        while ((line = bfr.readLine()) != null) {
            String[] parts = line.split("_");
            String fileUsername = parts[0];
            String filePassword = parts[1];
            String usernameAndPasswordLine = fileUsername + "_" + filePassword;
            if (usernameAndPasswordLine.equals(combinedString)) {
                bfr.close();
                return true;
            }
        }
        bfr.close();
        return false;
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

    public void setFriendRequests(Profile profile) { //Adds this profile to user's friend requests
        this.friendRequests.add(profile);
    }
    //other methods (will be explained)

    public void addFriend(Profile profile) { //sends a friend request to the desired user.
        profile.setFriendRequests(this);
    }

    public void acceptRequest(int n) { //accepts requests of the user, removes that user from the list of friend requests
        this.friends.add(friendRequests.get(n));
        this.friendRequests.remove(n);
    }

    public void rejectRequest(int n) {
        this.friendRequests.remove(n);
    }



}
