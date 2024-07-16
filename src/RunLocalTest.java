//Test Cases

public class RunLocalTest {
    public static void main(String[] args) {
        Profile p = new Profile("John", "123", 44, "Male");
        Profile p1 = new Profile("Alex", "234", 24, "Male");
        System.out.println(p);
        Base b = new Base();
        NewsFeed n = new NewsFeed(p, b.getAllPosts());
        NewsFeed n1 = new NewsFeed(p1, b.getAllPosts());
        n.makePost("Hello");

        n1.comment(p.getMyPosts().getFirst(), "Goodbye");
        n1.upvotePost(p.getMyPosts().get(0));
        System.out.println(p.getMyPosts());
        System.out.println(p.getMyPosts().getFirst().toStringFileFormat());

    }

    public boolean testProfile() {
        Profile p = new Profile("John", "122", 44);

    }
}
