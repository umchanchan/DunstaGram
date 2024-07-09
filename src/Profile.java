/*
This is the profile class of Dunstagram that has some information.

@ version 7/8/24

@author Mukund Rao
 */

public class Profile {
    private String username;
    private String password;
    private int followers;
    private int friends;
    private int age;
    private String gender;

    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;

        this.followers = 0;
        this.friends = 0;
    }


}
