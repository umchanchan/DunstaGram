import java.io.IOException;
import java.util.ArrayList;

public interface IBase {
    public Profile searchUser(String username) throws UserNotFoundException;
    public boolean signUp(String username, String password, int age, String gender) throws IOException;
    public boolean login(String username, String password) throws IOException, UserNotFoundException;
    public void readUserListFile() throws IOException;
    public void writeUserListFile() throws IOException;
    public void readPostListFile() throws IOException;
    public void writePostListFile() throws IOException;
    public boolean removePost(Post post);
    public ArrayList<Post> getAllPosts();
}
