public interface IComment {
    public int getComments();
    public void addComment(Profile commenter, String content);
    public void deleteComment(Comment comment);

    public void addUpvote();
    public void subtractUpvote();
    public void addDownvote();
    public void subtractDownvote();
    public String getUsername();
    public String getCommentContents();
    public int getUpvote();
    public int getDownvote();
}
