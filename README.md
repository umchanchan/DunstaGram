# Project Name : DunstaGram


## Compilation and Execution Instructions
To compile and run the project, follow these steps:

1. Ensure you have Java JDK installed on your system.
2. Navigate to the project directory where the `.java` files are located.
3. Compile the Java files.
4. Run the `Server` class.
5. Run the `ClientGUI` class for initiate a client.

## Class Descriptions

### Base Class
The `Foundation` class is the core component responsible for managing user accounts, posts, and file operations within the system. It ensures data persistence by reading from and writing to text files whenever changes occur.

- **Functionality**: Manages user data, posts, and interactions such as sign-up, login, follow/unfollow, block/unblock, post creation, commenting, and voting.
- **Testing**: Unit tests cover user creation, posting, follow/unfollow, and file operations to ensure class methods perform as expected.
- **Relationships**: 


### Client Class
Functions as the client which the user will see. Complex GUIs will be implemented in phase 2. This client exchanges information with the `ClientHandler` class, which is how it determines its inputs and outputs.

- **Functionality**: Functions as the client which the user will see, allows the user to interact with it, takes user inputs and displays information back to the user.
- **Testing**: Tested for alignment of inputs and outputs with `ClientHandler`, loops and switches. Tested using scanner for inputs.
- **Relationship to Other Classes**: Reads from and writes to `ClientHandler`.

### ClientHandler Class
Functions as a server for an instance of `ClientGUI` to interact with.

- **Functionality**: Functions as an individual server for an individual client to interact with. Instances of `ClientHandler` are initiated as a thread in `Server` for efficiency.
- **Testing**: Tested in conjunction with `ClientGUI` as `ClientGui` reads from this class for outputs.
- **Relationship to Other Classes**: Instances are created in threads by `Server`. Uses methods in `Base` to create outputs, which are sent to `ClientGUI` to parse into the client.

### Comment Class
Contains the contents of one comment.

- **Functionality**: 
- **Testing**: 
- **Relationship to Other Classes**: 

### Profile Class
Represents an individual user in the system.

- **Functionality**:
- **Testing**:
- **Relationship to Other Classes**:

### NewsFeed Class
Represents 

- **Functionality**:
- **Testing**:
- **Relationship to Other Classes**:

### Post Class
Represents

- **Functionality**:
- **Testing**:
- **Relationship to Other Classes**:

### Server Class
Runs indefinitely in the background, creating threads that runs the `ClientHandler` class.

- **Functionality**: The server will accept client connection as they come, creating `ClientHandler` instances accordingly.
- **Testing**: Tested in proxy when testing `ClientGUI` and `ClientHandler` as they require the functionality of `Server` to run.
- **Relationship to Other Classes**: Creates `ClientHandler` instances when a connection from `ClientGUI` is accepted.

## Exception Descriptions

### UserNotFoundException

An exception to be thrown when a user is not found. This is used by the `Profile` and `Base` classes as they search for an `Profile` object.




---

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
