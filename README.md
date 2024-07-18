Welcome To DunstaGram!


Instructions for Compilation and Execution of the Program:

1. Ensure that the Java Development Kit is installed on your system.
2. In your system open a terminal or command prompt
3. Navigate through the directory containing the source files and compile all Java files using the command javac [filename].java

For Execution:
1. In a terminal, start the server with the command "java Server"
2. In a different terminal or command prompt, start the client with the command "java Client"

Student Submissions:
[NAME HERE] - Submitted Report on Brightspace
[NAME HERE] - Submitted on Vocareum Workspace

Class Descriptions: 
The following information is a description of all of the classes, their functionalities, their test cases, and their relationships to other classes within the program. 

Base.java:
The purpose of this class is to manage file I/O operations as well as user profiles and posts. This includes things like signing in, logging in, following or unfollowing other users, blocking or unblocking other users, and managing posts and comments. This class works with other classes such as Profile, Post, and Comment to manage users and their posts and implements the Base interface.

Test cases for this class is included in the BaseTest.java file. It creates a new user by passing through the "signUp" method. It then tests other methods in the class such as the "searchUser" method and "writeUserList" method to make sure the input passed into "signUp" worked.

Client.java:
The purpose of this class is to handle the client GUI for the application. It works with ClientHandler and Base classes to connect to the server and handle user interactions. 

ClientHandler.java:
This class handles communication between the client and the server in a run() loop that processes client requests.

Comment.java:
This class is what allows users to create comments on a post. This class extends the class Post and implements the IComment interface. It includes methods to add upvotes or downvotes to a comment, and can return a string with the Username of the commenter, plus the contents of the comment. 

Profile.java:
This class represents a User's profile. It manages things such as the user information, their posts, and their interactions. It implements the interface IProfile. The test case for this class is included in the ProfileTest.java. It creates 3 separate profiles with different information and tests several things within the class, such as whether or not the accounts were actually created with the correct parameters, and if the follow and unfollow methods are working. 

NewsFeed.java:
This is a class that is meant to display all of the current posts so that each user can view them. It implements the interface INewsFeed. It also allows for the ability to upvote and downvote different posts, as well as hiding certain posts and commenting on any post.


Post.java:
This is a class that allows a user to create a post. It implements the interface IPost. The class manages post content, post interaction, and comments. This class also includes a test case within the PostTest.java file. This test case verifies the Post class works by creating a user, a commenter, and a test comment to see that the input is correctly used in the methods within the Post class. 


Server.java:
This class is meant for handling server operations and client connections. It implements the IServer interface and works with the Client class to handle the Server-Client relationship.

UserNotFoundException.java:
This is a class that includes a custom exception to be thrown when a user cannot be found. This exception is used in the Base class, the Profile class, and the Post class. 
