import java.util.*;
/**
 * Team Project -
 * <p>
 *     A class showing all posts
 * </p>
 *
 *
 */
public class NewsFeed implements INewsFeed {
    private Profile profile;
    private ArrayList<Post> allPost;


    public NewsFeed(Profile profile, ArrayList<Post> allPost) {
        this.profile = profile;
        this.allPost = allPost;

    }

    public void makePost(String message) {
        Post newPost = new Post();

    }

    public void upvotePost() {

    }

    public void downvotePost() {

    }

    public void hidePost() {

    }







}
