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
    private ArrayList<Post> hidePost = new ArrayList<>();

    public NewsFeed(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Post> filterPost(String follow, Base base) {
        if (profile.isFollowing(follow)) {
            try {
                Profile p = base.searchUser(follow);
                allPost.addAll(p.getMyPosts());

            } catch (UserNotFoundException e) {
                return allPost;
            }
        }
        return allPost;
    }

    public ArrayList<Post> filterHidePost(ArrayList<Post> hidePost) {
        for (Post post : hidePost) {
            for (Post post1 : allPost) {
                if (post.equals(post1)) {
                    allPost.remove(post1);
                    break;
                }
            }
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

    public void unHidePost(Post post) {
        allPost.add(post);
    }

    public void comment(Post post, String msg) {
        post.addComment(profile, msg);
    }

    public void setAllPost(ArrayList<Post> p) {
        this.allPost = p;
    }


}
