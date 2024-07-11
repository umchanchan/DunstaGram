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
        Post newPost = new Post(message, profile);
        allPost.add(newPost);
    }

    public void upvotePost(Post post) {
        post.setUpvotes(post.getUpvotes() + 1);
    }

    public void cancelUpvotePost(Post post) {
        post.setUpvotes(post.getUpvotes() - 1);
    }

    public void downvotePost(Post post) {
        post.setDownvotes(post.getDownvotes() + 1);
    }

    public void cancelDownvotePost(Post post) {
        post.setDownvotes(post.getDownvotes() - 1);
    }

    public void hidePost() {

    }







}
