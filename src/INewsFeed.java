public interface INewsFeed {

    public void upvotePost(Post post);

    public void cancelUpvotePost(Post post);

    public void downvotePost(Post post);

    public void cancelDownvotePost(Post post);

    public void hidePost(Post post);

}
