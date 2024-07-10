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

        this.followers = 0;
        this.numFriends = 0;
        this.friends = new ArrayList<Profile>();
        this.friendRequests = new ArrayList<Profile>();
    }

    public boolean signUp(String username, String password) throws IOException {


        ArrayList<String> fileInfo = readUserListFile();
        String fileUsername = fileInfo.get(0);
        String filePassword = fileInfo.get(1);


        if (fileUsername.equals(username)) {
            System.out.println("username not available");
            return false;
        }

        Profile newProfile = new Profile(username, password, age, gender);

        writeUserListFile(fileInfo);
        return true;
    }

    public boolean login(String username, String password) throws IOException, UserNotFoundException {

        ArrayList<String> fileInfo = readUserListFile();
        String fileUsername = fileInfo.get(0);
        String filePassword = fileInfo.get(1);

        if (fileUsername.equals(username) && filePassword.equals(password)) {
            return true;
        } else {
            throw new UserNotFoundException("Invalid login!");
        }
    }

    public ArrayList<String> readUserListFile() throws IOException {
        ArrayList<String> fileInfo = new ArrayList<>();

        try {
            File f = new File("userList.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            String line;
            while ((line = bfr.readLine()) != null) {
                String[] parts = line.split("_");
                if (parts.length < 1) {
                    throw new IOException("Error occurred when reading or writing a file");
                }

                String fileUsername = parts[0];
                String filePassword = parts[1];
                fileInfo.add(fileUsername);
                fileInfo.add(filePassword);
                for (int i = 2; i < parts.length; i++) {
                    String friend = parts[i];
                    fileInfo.add(friend);
                }
            }
            bfr.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when reading a file");
        }
        return fileInfo;
    }

    public void writeUserListFile(ArrayList<String> userInfo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("userListFile", true), true)) {
            String fileUsername = userInfo.get(0);
            String filePassword = userInfo.get(1);
            pw.print(fileUsername + "_" + filePassword);

            for (int i = 2; i < userInfo.size(); i++) {
                String friend = userInfo.get(i);
                pw.print("_" + friend);
            }
            pw.println();
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when writing a file");
        }

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

    //friend methods

    public void addFriend(Profile profile) { //sends a friend request to the desired user.
        profile.setFriendRequests(this);
    }

    public boolean acceptRequest(int n) { //accepts requests of the user, removes that user from the list of friend requests
        try {
            this.friends.add(friendRequests.get(n));
            this.friendRequests.remove(n);
        } catch(IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public void rejectRequest(int n) {
        try {
            this.friendRequests.remove(n);
        } catch(IndexOutOfBoundsException e) {
            return;
        }
    }

    //post methods

    public boolean makePost() {
        return true;
    }


}
