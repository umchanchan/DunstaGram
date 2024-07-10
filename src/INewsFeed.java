public interface INewsFeed {
    public void makePost(String message);

    public void upvotePost(Post post);

    public void cancelUpvotePost(Post post);

    public void downvotePost(Post post);

    public void cancelDownvotePost(Post post);

    public void hidePost();

}
