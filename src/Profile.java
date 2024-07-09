import java.util.*;
import java.io.*;

/*
This is the profile class of Dunstagram that has some information.

@ version 7/8/24

@author Mukund Rao
 */



// login, signup, friend interaction, add or remove posts and comments
//
public class Profile {
    private String username;
    private String password;
    private int followers;
    private int numFriends;
    private int age;
    private String gender;
    private ArrayList<Profile> friends;

    public Profile(String username, String password, int age, String gender) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.friends = new ArrayList<>();

        this.followers = 0;
        this.numFriends = 0;
    }

    public boolean signUp(String username, String password) {

    }

    public boolean login(String username, String password) {


    }





}
