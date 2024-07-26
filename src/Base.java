import java.util.*;
import java.io.*;
import java.util.stream.DoubleStream;

/**
 * Team Project - Base
 * <p>
 * Base class that mainly reads and write a file. It updates the file everytime there is a change.
 * </p>
 */

public class Base implements IBase {
    private static ArrayList<Profile> users = new ArrayList<>();
    private ArrayList<Post> allPosts = new ArrayList<>();
    private Profile profile = new Profile();
    private Post post = new Post();
    private static Object obj = new Object();
    private ArrayList<Post> userHidePosts = new ArrayList<>();
    private static ArrayList<String> upvoteDownvote = new ArrayList<>();

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


    public ArrayList<String> getUserInfo(Profile toGet) {
        ArrayList<String> retrievedUserInfo = new ArrayList<>();
        retrievedUserInfo.add(toGet.getUsername());
        retrievedUserInfo.add(String.valueOf(toGet.getAge()));
        retrievedUserInfo.add(toGet.getGender());
        retrievedUserInfo.add(String.valueOf(toGet.getFollowing().size()));
        retrievedUserInfo.add(String.valueOf(toGet.getMyPosts().size()));
        return retrievedUserInfo;
    }


    public boolean signUp(String username, String password, int age, String gender) throws IOException {
        synchronized (obj) {
            if (username.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                return false;
            }
            readAllListFile();
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
            readAllListFile();
            boolean found = false;
            for (Profile user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
            if (!found) {
                throw new UserNotFoundException("Invalid login!");
            }
            return null;
        }
    }

    public boolean follow(Profile profile, Profile toAdd) throws IOException {
        synchronized (obj) {
            boolean worked = false;
            int index = 0;
            for (Profile user : users) {
                if (user.equals(profile)) {
                    user.follow(toAdd);
                    System.out.println(user.getFollowing().size());
                    profile.follow(toAdd);
                    System.out.println(profile.getFollowing().size());
                    users.set(index, user);
                    worked = true;
                    writeUserListFile();
                    readAllListFile();
                    break;
                }
                index++;
            }
            return worked;
        }
    }

    public boolean unFollow(Profile profile, Profile toUnfollow) throws IOException {
        synchronized (obj) {
            boolean worked = false;
            for (Profile user : users) {
                if (user.equals(profile)) {
                    user.unfollow(toUnfollow);
                    profile.unfollow(toUnfollow);
                    worked = true;
                    writeUserListFile();
                    readAllListFile();
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
                    if (user.blockUser(toBlock)) {
                        worked = true;
                        writeUserListFile();
                        readAllListFile();
                    }
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
                    user.unblockUser(unBlock);
                    worked = true;
                    writeUserListFile();
                    readAllListFile();
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
            clearAllPosts();
            try {
                File f = new File("postList.txt");
                FileReader fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);

                String line;
                while ((line = bfr.readLine()) != null) {
                    post = post.makePost(line, users);
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


    private void readHidePostListFile() throws IOException {
        synchronized (obj) {
            try {
                BufferedReader bfr = new BufferedReader(new FileReader("hidePostList.txt"));
                String line;
                while ((line = bfr.readLine()) != null) {
                    for (Profile user : users) {
                        user.startHidePostList(line, this);
                    }
                }
            } catch (IOException e) {
                throw new IOException("Error occurred when reading a file");
            }
        }
    }

    public void writeHidePostListFile(Profile user, Post post) throws IOException {
        synchronized (obj) {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream("hidePostList.txt", true), true)) {

                String username = post.getPoster().getUsername();
                String content = post.getMessage();
                int upvote = post.getUpvotes();
                int downvote = post.getDownvotes();

                String result = user.getUsername() + "_" + username + "_" + content + "_" + upvote + "_" + downvote;
                pw.println(result);
                pw.flush();


//                for (Profile user : users) {
//                    ArrayList<String> hideString = user.hidePostToString();
//                    for (String line : hideString) {
//                        pw.println(line);
//                        pw.flush();
//                    }
//                }
            } catch (IOException e) {
                throw new IOException("Error occurred when writing a file");
            }
        }
    }

    public void readAllListFile() throws IOException {
        readUserListFile();
        readPostListFile();
        readHidePostListFile();
    }

    public void clearUsers() {
        users.clear();
    }

    public void clearAllPosts() {
        allPosts.clear();
    }

    public ArrayList<Profile> getUsers() {
        return users;
    }

    public void editUserInfo(Profile p, int age, String gender, String password) {
        int index = users.indexOf(p);
        Profile copiedProfile = users.get(index);

        copiedProfile.setAge(age);


        copiedProfile.setGender(gender);

        if (!password.isEmpty()) {
            copiedProfile.setPassword(password);
        }
        users.set(index, copiedProfile);
    }

    public ArrayList<Post> getAllPosts() {
        return allPosts;
    }

    public Post makeNewPost(Profile poster, String message) throws IOException {
        synchronized (obj) {
            for (Profile user : users) {
                if (user.getUsername().equals(poster.getUsername())) {
                    for (Post samePost : allPosts) {
                        if (user.getUsername().equals(samePost.getPoster().getUsername()) &&
                                message.equals(samePost.getMessage())) {
                            return null;
                        }
                    }
                    Post post = new Post(user, message);
                    user.addMyPost(post);
                    allPosts.add(post);
                    writePostListFile();
                    readAllListFile();
                    return post;
                }
            }
            return null;
        }
    }

    public boolean removePost(Profile poster, Post post) throws IOException {
        synchronized (obj) {
            if (poster.getUsername().equals(post.getPoster().getUsername())) {
                boolean change = false;
                for (Post post1 : allPosts) {
                    if (post1.equals(post)) {
                        allPosts.remove(post1);
                        change = true;
                        break;
                    }
                }
                for (Profile user : users) {
                    if (user.getUsername().equals(poster.getUsername())) {
                        poster.removeMyPost(post);
                    }
                }
                writePostListFile();
                readAllListFile();
                return change;
            }
            return false;
        }
    }

    public void hidePost(String mainUser, String poster, String message) throws IOException {
        synchronized (obj) {
            for (Profile user : users) {
                if (user.getUsername().equals(mainUser)) {
                    if (!(user.getUsername().equals(poster))) {
                        for (Post post1 : allPosts) {
                            if (post1.getPoster().getUsername().equals(poster) && post1.getMessage().equals(message)) {

                                System.out.println(user.getFollowingPosts().size());
                                user.hidePost(poster, message);
                                writeHidePostListFile(user, post1);
                                readAllListFile();
                                return;
                            }
                        }

                    }
                }
            }
        }
    }

    public void unHidePost(Profile profile, Post post) throws IOException {
        synchronized (obj) {
            if (!(profile.getUsername().equals(post.getPoster().getUsername()))) {
                for (Post post1 : allPosts) {
                    if (post.equals(post1)) {
                        profile.unHidePost(post1);
                        writeHidePostListFile(profile , post);
                    }
                }

                readAllListFile();
            }
        }
    }

    public void addUpvote(Post post) throws IOException {
        synchronized (obj) {
            for (Post post1 : allPosts) {
                if (post1.equals(post)) {
                    post1.addUpvote();
                }
            }
            writePostListFile();
            readAllListFile();
        }
    }

    public void addDownvote(Post post) throws IOException {
        synchronized (obj) {
            for (Post post1 : allPosts) {
                if (post1.equals(post)) {
                    post1.addDownvote();
                }
            }
            writePostListFile();
            readAllListFile();
        }
    }
    //we will add cancel addUpvote and addDownvote if we could implement this in GUI

    public void addUpvote(Post post, Comment comment) throws IOException {
        synchronized (obj) {
            for (Post post1 : allPosts) {
                if (post1.equals(post)) {
                    for (Comment comment1 : post1.getComments()) {
                        if (comment1.equals(comment)) {
                            comment1.addUpvote();
                        }
                    }
                }
            }
            writePostListFile();
            readAllListFile();
        }
    }

    public void addDownvote(Post post, Comment comment) throws IOException {
        synchronized (obj) {
            for (Post post1 : allPosts) {
                if (post1.equals(post)) {
                    for (Comment comment1 : post1.getComments()) {
                        if (comment.equals(comment1)) {
                            comment1.addDownvote();
                        }
                    }
                }
            }
            writePostListFile();
            readAllListFile();
        }
    }


    public Comment makeComment(Post post, Profile commenter, String message) throws IOException {
        synchronized (obj) {
            readAllListFile();
            for (Post samePost : allPosts) {
                if ((post.getPoster().getUsername().equals(samePost.getPoster().getUsername())) &&
                        (post.getMessage().equals(samePost.getMessage()))) {
                    ArrayList<Comment> comments = samePost.getComments();
                    for (Comment comment1 : comments) {
                        if (comment1.getCommenter().getUsername().equals(commenter.getUsername()) &&
                                comment1.getCommentContents().equals(message)) {
                            return null;
                        }
                    }
                    Comment comment = samePost.addComment(commenter, message);
                    writePostListFile();
                    readAllListFile();
                    return comment;
                }
            }
            return null;
        }
    }

    public boolean deleteComment(Post post, Profile profile, Comment comment) throws IOException {
        synchronized (obj) {
            readAllListFile();
            for (Post samePost : allPosts) {
                if ((post.getPoster().getUsername().equals(samePost.getPoster().getUsername())) &&
                        (post.getMessage().equals(samePost.getMessage()))) {

                    if (profile.getUsername().equals(post.getPoster().getUsername()) ||
                            profile.getUsername().equals(comment.getCommenter().getUsername())) {
                        ArrayList<Comment> comments = samePost.getComments();
                        for (Comment comment1 : comments) {
                            if (comment1.getCommenter().getUsername().equals(comment.getCommenter().getUsername()) &&
                                    comment1.getCommentContents().equals(comment.getCommentContents())) {
                                samePost.deleteComment(comment1);
                                break;
                            }
                        }
                        writePostListFile();
                        readAllListFile();
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
