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
    private ArrayList<Post> userPosts;
    private ArrayList<Profile> blockedList;

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
                    String friendUsername = parts[i];
                    friends.add(new Profile(friendUsername));
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

            for (int i = 0; i < friends.size(); i++) {
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

    public void reWriteUserListFile(ArrayList<String> lines) throws IOException {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter("userList.txt"))) {
            for (String updatedLine : lines) {
                bfw.write(updatedLine);
                bfw.newLine();
            }
            bfw.close();
        } catch (IOException e) {
            throw new IOException("User not found in the file");
        }
    }

    //getters

    public String getUsername() {
        return username;
    }

    private String getPassword() {
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

    public boolean acceptRequest(Profile friend) throws IOException, UserNotFoundException { //accepts requests of the user, removes that user from the list of friend requests
        try {
            File f = new File("userList.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> lines = new ArrayList<>();

            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            boolean userFound = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = line.split("_");
                if (parts.length > 0 && parts[0].equals(username)) {
                    StringBuilder sb = new StringBuilder(parts[0] + "_" + parts[1]);
                    for (int j = 2; j < parts.length; j++) {
                        sb.append("_").append(parts[j]);
                    }
                    sb.append("_").append(friend.getUsername());
                    lines.set(i, sb.toString());
                    userFound = true;
                    break;
                } else if (parts.length > 0 && parts[0].equals(friend.username)) {
                    StringBuilder sb = new StringBuilder(parts[0] + "_" + parts[1]);
                    for (int j = 2; j < parts.length; j++) {
                        sb.append("_").append(parts[j]);
                    }
                    sb.append("_").append(username);
                    lines.set(i, sb.toString());
                    userFound = true;
                    break;
                }
            }

            reWriteUserListFile(lines);

            if (!userFound) {
                throw new UserNotFoundException("User not found in the file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred when reading a file");
        }

        if (!friendRequests.contains(friend)) {
            return false;
        }
        this.friends.add(friend);
        friend.friends.add(this);
        this.friendRequests.remove(friend);

        return true;
    }

    public boolean rejectRequest(Profile profile) {
        if (!friendRequests.contains(profile)) {
            return false;
        }
        this.friendRequests.remove(profile);
        return true;
    }

    public void removeFriend(Profile f) {
        if(f.isFriends(this)) {
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
