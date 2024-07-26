import java.util.ArrayList;

public interface INewsFeed {

    ArrayList<Post> filterPost(String follow, Base base, ArrayList<Post> hidePost);

    void hidePost(Post post);

    void comment(Post post, String msg);

}
