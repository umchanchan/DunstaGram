import java.util.ArrayList;

public interface IProfile {
    String toString();

    Profile makeProfile(String userInfo);
    boolean equals(Profile toCompare);
    void startHidePostList(String info);
    ArrayList<String> hidePostToString();
    void hidePost(Post post);

    void addMyPost(Post post);

    void removeMyPost(Post post);

    void follow(Profile p);

    void unfollow(Profile p);
    boolean isFollowing(Profile follow);

    boolean blockUser(Profile user);
    void unblockUser(Profile unblock);

    String getUsername();

    String getPassword();


    int getAge();

    String getGender();

    ArrayList<Profile> getFollowing();

    ArrayList<Post> getMyPosts();

    ArrayList<Profile> getBlockedList();

    void setUsername(String username);

    void setAge(int age);

    void setGender(String gender);

}
