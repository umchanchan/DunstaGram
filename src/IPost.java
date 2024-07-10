public interface IPost {
    public String getMessage();
    public void setMessage(String message);
    public int getUpvotes();
    public void setUpvotes(int upvotes);
    public int getDownvotes();
    public void setDownvotes(int downvotes);
    public Profile getPoster();
    public boolean isViewed();
    public int getComments();
    public String getCommentContent();
    public void setCommentContent(String commentContent);
    void addComment(Profile commenter, String content);
    public void deleteComment(Comment comment);

}
