import java.util.*;

/**
 * Team Project - NewsFeed
 * <p>
 * NewsFeed class displaying all posts that each user can view.
 * This class is going to be mainly implemented for phase 2.
 * </p>
 */
public class NewsFeed implements INewsFeed {
    private Profile profile;
    private ArrayList<Post> allPost = new ArrayList<>();

    public NewsFeed(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Post> filterPost(Profile follow) {
        if (profile.isFollowing(follow)) {
            allPost.addAll(follow.getMyPosts());
        }
        return allPost;
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

    public void hidePost(Post post) { //assumes allPost is different for each specific user
        allPost.remove(post);
    }

    public void comment(Post post, String msg) {
        post.addComment(profile, msg);
    }

    public void setAllPost(ArrayList<Post> p) {
        this.allPost = p;
    }


}
