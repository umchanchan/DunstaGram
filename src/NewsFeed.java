import java.util.*;

/**
 * Team Project - NewsFeed
 * <p>
 * NewsFeed class displaying all posts that each user can view.
 * </p>
 */
public class NewsFeed implements INewsFeed {
    private Profile profile;
    private ArrayList<Post> allPost = new ArrayList<>();

    public NewsFeed(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Post> filterPost(String follow, Base base, ArrayList<Post> hidePost) {
        if (profile.isFollowing(follow)) {
            try {
                Profile p = base.searchUser(follow);
                allPost.addAll(p.getMyPosts());
                if (!(hidePost.isEmpty())) {
                    for (Post post : hidePost) {
                        for (Post post1 : allPost) {
                            if (post.equals(post1)) {
                                allPost.remove(post1);
                                break;
                            }
                        }
                    }
                }
            } catch (UserNotFoundException e) {
                return allPost;
            }
        }
        return allPost;
    }

    public void hidePost(Post post) { //assumes allPost is different for each specific user
        allPost.remove(post);
    }

    public void comment(Post post, String msg) {
        post.addComment(profile, msg);
    }



}
