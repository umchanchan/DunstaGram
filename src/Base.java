import java.util.*;
import java.io.*;

/**
 * Team Project - Base
 * <p>
 * Base class that mainly reads and write a file. It updates the file everytime there is a change.
 * </p>
 */

//It has to implement block method into file io
public class Base implements IBase {
    private ArrayList<Profile> users = new ArrayList<>();
    private ArrayList<Post> allPosts = new ArrayList<>();
    private Profile profile = new Profile();
    private Post post = new Post();
    private Object obj = new Object();

    public Profile searchUser(String username) throws UserNotFoundException {
        synchronized (obj) {
            for (Profile profile : users) {
                if (profile.getUsername().equals(username)) {
                    return profile;
                }
            }
            throw new UserNotFoundException("We can't find the user with " + username);
        }
    }


    public boolean signUp(String username, String password, int age, String gender) throws IOException {
        synchronized (obj) {
            if (username.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                return false;
            }
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
    }

    public Profile login(String username, String password) throws IOException, UserNotFoundException {
        synchronized (obj) {
            if (username.isEmpty() || password.isEmpty()) {
                return null;
            }
            readUserListFile();
            for (Profile user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return new Profile(user.getUsername(), user.getPassword(), user.getAge(), user.getGender());
                } else {
                    throw new UserNotFoundException("Invalid login!");
                }
            }
            return null;
        }
    }

    public boolean follow(Profile profile, Profile toAdd) throws IOException {
        synchronized (obj) {
            boolean worked = false;
            for (Profile user : users) {
                if (user.getUsername().equals(profile.getUsername())) {
                    profile.follow(toAdd);
                    worked = true;
                    writeUserListFile();
                    break;
                }
            }
            return worked;
        }
    }

    public boolean unFollow(Profile profile, Profile toUnfollow) throws IOException {
        synchronized (obj) {
            boolean worked = false;
            for (Profile user : users) {
                if (user.getUsername().equals(profile.getUsername())) {
                    profile.unfollow(toUnfollow);
                    worked = true;
                    writeUserListFile();
                    break;
                }
            }
            return worked;
        }
    }

    public boolean block(Profile profile, Profile toBlock) throws IOException {
        synchronized (obj) {
            boolean worked = false;
            for (Profile user : users) {
                if (user.getUsername().equals(profile.getUsername())) {
                    profile.blockUser(toBlock);
                    worked = true;
                    writeUserListFile();
                    break;
                }
            }
            return worked;
        }
    }

    public boolean unBlock(Profile profile, Profile unBlock) throws IOException {
        synchronized (obj) {
            boolean worked = false;
            for (Profile user : users) {
                if (user.getUsername().equals(profile.getUsername())) {
                    profile.unblockUser(unBlock);
                    worked = true;
                    writeUserListFile();
                    break;
                }
            }
            return worked;
        }
    }

    public void readUserListFile() throws IOException {
        synchronized (obj) {
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
    }

    public void writeUserListFile() throws IOException {
        synchronized (obj) {
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
    }

    public void readPostListFile() throws IOException {
        synchronized (obj) {
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

    public void writePostListFile() throws IOException {
        synchronized (obj) {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream("postList.txt", false), true)) {

                for (Post post : allPosts) {
                    String postInfo = post.toString();
                    pw.println(postInfo);
                    pw.flush();
                }
                pw.close();
            } catch (IOException e) {
                throw new IOException("Error occurred when writing a file");
            }
        }
    }

    public void readHidePostListFile() throws IOException {
        synchronized (obj) {
            try {
                BufferedReader bfr = new BufferedReader(new FileReader("hidePostList.txt"));
                String line;
                while ((line = bfr.readLine()) != null) {
                    profile.startHidePostList(line);
                }
            } catch (IOException e) {
                throw new IOException("Error occurred when reading a file");
            }
        }
    }

    public void writeHidePostListFile() throws IOException {
        synchronized (obj) {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream("hidePostList.txt", false), true)) {
                for (Profile user : users) {
                    ArrayList<String> hideString = user.hidePostToString();
                    for (String line : hideString) {
                        pw.println(line);
                        pw.flush();
                    }
                }
            } catch (IOException e) {
                throw new IOException("Error occurred when writing a file");
            }
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

    public Post makeNewPost(Profile poster, String message) throws IOException {
        synchronized (obj) {
            Post post = new Post(poster, message);
            poster.addMyPost(post);
            allPosts.add(post);
            writePostListFile();
            return post;
        }
    }

    public boolean removePost(Profile poster, Post post) throws IOException {
        synchronized (obj) {
            if (poster.equals(post.getPoster())) {
                allPosts.remove(post);
                poster.removeMyPost(post);
                writePostListFile();
                return true;
            }
            return false;
        }
    }

    public void hidePost(Profile profile, Post post) throws IOException {
        synchronized (obj) {
            if (!(profile.equals(post.getPoster()))) {
                profile.hidePost(post);
                writeHidePostListFile();
            }
        }
    }

    public void addUpvote(Post post) throws IOException {
        synchronized (obj) {
            post.addUpvote();
            writePostListFile();
        }
    }

    public void addDownvote(Post post) throws IOException {
        synchronized (obj) {
            post.addDownvote();
            writePostListFile();
        }
    }
    //we will add cancel addUpvote and addDownvote if we could implement this in GUI

    public void addUpvote(Post post, Comment comment) {
        synchronized (obj) {
            for (Comment comment1 : post.getComments()) {
                if (comment.equals(comment1)) {
                    comment.addUpvote();
                }
            }
        }
    }

    public void addDownvote(Post post, Comment comment) {
        synchronized (obj) {
            for (Comment comment1 : post.getComments()) {
                if (comment.equals(comment1)) {
                    comment.addDownvote();
                }
            }
        }
    }


    public Comment makeComment(Post post, Profile commenter, String message) throws IOException {
        synchronized (obj) {
            for (Post samePost : allPosts) {
                if ((post.getPoster().equals(samePost.getPoster())) && (post.getMessage().equals(samePost.getMessage()))) {
                    Comment comment = post.addComment(commenter, message);
                    writePostListFile();
                    return comment;
                }
            }
            return null;
        }
    }

    public boolean deleteComment(Post post, Profile profile, Comment comment) throws IOException {
        synchronized (obj) {
            for (Post samePost : allPosts) {
                if ((post.getPoster().equals(samePost.getPoster())) && (post.getMessage().equals(samePost.getMessage()))) {
                    if (profile.equals(post.getPoster()) || profile.equals(comment.getCommenter())) {
                        post.deleteComment(comment);
                        writePostListFile();
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
