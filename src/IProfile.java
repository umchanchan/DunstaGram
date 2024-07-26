import java.util.ArrayList;

public interface IProfile {
    String toString();

    Profile makeProfile(String userInfo);
    boolean equals(Profile toCompare);
    void startHidePostList(String info, Base base);
    ArrayList<String> hidePostToString();
    void hidePost(String poster, String message);

    void addMyPost(Post post);

    void removeMyPost(Post post);

    void follow(Profile p);

    void unfollow(Profile p);
    boolean isFollowing(Profile follow);

    boolean isFollowing(String follow);

    boolean blockUser(Profile user);
    void unblockUser(Profile unblock);

    String getUsername();

    String getPassword();


    int getAge();

    String getGender();

    ArrayList<String> getFollowing();

    ArrayList<Post> getMyPosts();

    ArrayList<String> getBlockedList();

    void setUsername(String username);

    void setAge(int age);

    void setGender(String gender);

}
