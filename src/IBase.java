import java.io.IOException;
import java.util.ArrayList;

public interface IBase {
    void updateFiles() throws IOException;
    Profile searchUser(String username) throws UserNotFoundException;
    ArrayList<String> getUserInfo(Profile toGet);
    Post searchPost(Post post);
    boolean signUp(String username, String password, int age, String gender) throws IOException;

    Profile login(String username, String password) throws IOException, UserNotFoundException;

    boolean follow(Profile profile, Profile toAdd) throws IOException;

    boolean unFollow(Profile profile, Profile toUnfollow) throws IOException;

    boolean block(Profile profile, Profile toBlock) throws IOException;

    boolean unBlock(Profile profile, Profile unBlock) throws IOException;

    void readAllListFile() throws IOException;

    void writeUserListFile() throws IOException;

    void writePostListFile() throws IOException;

    void writeHidePostListFile(Profile user, Post post) throws IOException;

    void clearUsers();

    ArrayList<Profile> getUsers();

    Post makeNewPost(Profile poster, String message) throws IOException;

    boolean removePost(Profile poster, Post post) throws IOException;

    void hidePost(String profile, String poster, String contents) throws IOException;

    void addUpvote(Post post) throws IOException;

    void addDownvote(Post post) throws IOException;

    Comment makeComment(Post post, Profile commenter, String message) throws IOException;

    boolean deleteComment(Post post, Profile profile, Comment comment) throws IOException;

    ArrayList<Post> getAllPosts();
}
