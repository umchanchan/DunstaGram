import java.util.*;
import java.io.*;

/**
 * This class's task is to deal with methods that utilize file IO
 *
 */
public class Base {
    private ArrayList<Profile> users;
    private ArrayList<Post> allPosts;
    private Profile profile;
    private Post post;


    /*
    * not sure where this method should be
    */
    public Profile searchUser(String username) throws UserNotFoundException {
        for (Profile profile : users) {
            if (profile.getUsername().equals(username)) {
                return profile;
            } else {
                throw new UserNotFoundException("We can't find the user with" + username);
            }
        }
        return null;
    }


    public boolean signUp(String username, String password, int age, String gender) throws IOException {

        readUserListFile();
        for (Profile user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        Profile newProfile = new Profile(username, password, age, gender);
        users.add(newProfile);
        writeUserListFile();
        return true;
    }

    public boolean login(String username, String password) throws IOException, UserNotFoundException {
        readUserListFile();
        for (Profile user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            } else {
                throw new UserNotFoundException("Invalid login!");
            }
        }
        return false;
    }

    public void readUserListFile() throws IOException {
        try {
            File f = new File("userList.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            String line;
            while ((line = bfr.readLine()) != null) {
                profile = profile.makeProfile(line);
                users.add(profile);
            }
            bfr.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when reading a file");
        }

    }

    public void writeUserListFile() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("userListFile", true), true)) {

            for (Profile user : users) {
                String userInfo = user.toString();
                pw.println(userInfo);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when writing a file");
        }

    }

    public void readPostListFile() throws IOException {
        try {
            File f = new File("postList.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            String line;
            while ((line = bfr.readLine()) != null) {
                post = post.makePost(line);
                this.allPosts.add(post);
            }
            bfr.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when reading a file");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
