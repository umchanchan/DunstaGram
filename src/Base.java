import java.util.*;
import java.io.*;

/**
 * This class's task is to deal with methods that utilize file IO
 */
public class Base {
    private ArrayList<Profile> users = new ArrayList<>();
    private ArrayList<Post> allPosts = new ArrayList<>();
    private Profile profile = new Profile();
    private Post post;

    public Base() {
        this.allPosts = new ArrayList<Post>();
    }


    /*
     * not sure where this method should be
     */
    public Profile searchUser(String username) throws UserNotFoundException {
        for (Profile profile : users) {
            if (profile.getUsername().equals(username)) {
                return profile;
            }
        }
        throw new UserNotFoundException("We can't find the user with " + username);
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
        clearUsers();
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
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("userList.txt", false), true)) {

            for (Profile user : users) {
                String profileInfo = user.toString();
                pw.println(profileInfo);
                pw.flush();
            }
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

    public void writePostListFile() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("postList.txt", true), true)) {

            for (Post post : allPosts) {
                String postInfo = post.toString();
                pw.println(postInfo);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when writing a file");
        }

    }

    public void clearUsers() {
        users.clear();
    }

    public ArrayList<Profile> getUsers() {
        return users;
    }

    public ArrayList<Post> getAllPosts() {
        return allPosts;
    }

    public void makeNewPost(Profile poster, String message) throws IOException {
        Post post = new Post(poster, message);
        allPosts.add(post);
        writePostListFile();
    }

    public boolean removePost(Post post) throws IOException {
        if (allPosts.contains(post)) {
            allPosts.remove(post);
            writePostListFile();
            return true;
        }
        return false;
    }
}
