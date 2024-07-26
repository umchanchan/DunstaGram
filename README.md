# Project Name: DunstaGram

## One Liner

DunstaGram is a sophisticated social media platform consisting of several classes that manage user profiles, posts, comments, and interactions such as upvoting, downvoting, following, and blocking, using server-client communication for handling user requests.

## Instructions to compile and run the project
To compile and run the project, follow these steps:
1. Clone this repository to your local device.
2. Navigate to the project folder and open it in a java IDE.
3. Run the `Server` class.
4. Run the `Client` class to initiate a client.

## Classes

### 1. `Profile`

#### Description
The `Profile` class represents a user profile within the social media platform. It encapsulates user-specific information and handles various interactions, such as following other profiles, blocking users, hiding posts, and managing personal posts. The class is central to the platform's user management system.

#### Functionality
- **User Information Management**: Stores and manages user details such as username, password, age, and gender.
- **Interaction Management**: Allows users to follow or unfollow other profiles, block or unblock users, and hide posts.
- **Post Management**: Handles creation and deletion of posts, as well as hiding specific posts.

#### Contribution
- **User Interaction**: Manages how users interact with each other through following and blocking.
- **Post Visibility**: Controls which posts are visible to the user based on their preferences and interactions.
- **Data Storage**: Maintains lists of posts and interactions crucial for the functionality of the news feed and user profiles.

#### Methods
- **Constructors**: Initialize profile with various details.
- **`toString()`**: Provides a formatted string of profile details.
- **`makeProfile(String userInfo)`**: Creates a profile from a string of user information.
- **`equals(Profile toCompare)`**: Checks equality between profiles.
- **`startHidePostList(String info)`**: Manages hidden posts.
- **`addMyPost(Post post)`**: Adds posts to the user’s profile.
- **`follow(Profile p)`**: Allows the user to follow another profile.
- **`blockUser(Profile user)`**: Blocks a profile to prevent interactions.

#### Testing
- Validated profile creation and updating.
- Ensured correct functionality of following, blocking, and hiding posts.
- Verified interaction with posts and maintenance of the user's post list.

### 2. `Post`

#### Description
The `Post` class models a post created by a user, including its content, associated profile, and interactions like upvoting and downvoting. It serves as the primary medium for user-generated content on the platform.

#### Functionality
- **Content Management**: Stores the message of the post and the profile of the poster.
- **Interaction Tracking**: Tracks upvotes and downvotes, and manages comments associated with the post.

#### Contribution
- **Content Creation**: Allows users to create and share content on the platform.
- **Engagement Tracking**: Manages how other users interact with the post through voting and commenting.
- **Comment Management**: Facilitates the addition and deletion of comments.

#### Methods
- **Constructors**: Initialize post with message and poster profile.
- **`toString()`**: Provides a detailed string representation of the post.
- **`addComment(Profile commenter, String content)`**: Adds a comment to the post.
- **`addUpvote()`**: Increments upvote count.
- **`addDownvote()`**: Increments downvote count.

#### Testing
- Verified creation, upvoting, and downvoting of posts.
- Ensured comments are correctly added and displayed.

### 3. `Comment`

#### Description
The `Comment` class represents a comment on a post. It extends the `Post` class and implements the `IComment` interface. It stores details about the commenter, comment contents, and vote counts.

#### Functionality
- **Comment Management**: Manages attributes related to comments such as commenter, contents, upvotes, and downvotes.
- **Voting**: Provides methods to increment upvotes and downvotes.
- **Comparison and Serialization**: Supports comparison with other comments and serialization for network communication.

#### Contribution
- **Comment Representation**: Encapsulates details of a comment and provides methods to manipulate and retrieve these details.
- **Data Handling**: Ensures comments can be easily compared and serialized for storage or network transmission.

#### Methods
- `Comment(Profile commenter, String contents, int upvotes, int downvotes)`: Constructor to initialize the comment with a commenter, content, and vote counts.
- `String toString()`: Returns a string representation of the comment, including the commenter, contents, and vote counts.
- `boolean equals(Comment comment)`: Checks if the given comment is equal to the current comment based on commenter and contents.
- `void addUpvote()`: Increments the upvote count.
- `void addDownvote()`: Increments the downvote count.
- `String getCommentContents()`: Retrieves the contents of the comment.
- `int getUpvote()`: Retrieves the number of upvotes.
- `int getDownvote()`: Retrieves the number of downvotes.
- `Profile getCommenter()`: Retrieves the profile of the commenter.
- `void setCommentContents(String contents)`: Sets the contents of the comment.

#### Testing
- Verified correct initialization and string representation of comments.
- Ensured upvote and downvote methods function as expected.
- Tested equality method to confirm accurate comparison of comments.
- Validated getter and setter methods for correct data retrieval and modification.


### 4. `NewsFeed`

#### Description
The `NewsFeed` class manages the display and interaction of posts in a user’s feed. It aggregates posts from profiles that the user follows and handles user interactions with these posts.

#### Functionality
- **Feed Management**: Aggregates and filters posts based on the user’s follow list.
- **Interaction Handling**: Manages user actions such as upvoting, downvoting, and commenting on posts.

#### Contribution
- **Content Aggregation**: Provides a personalized feed based on user interactions.
- **Interaction Processing**: Facilitates user interactions with posts in the feed.

#### Methods
- **`filterPost(Profile follow)`**: Filters posts from followed profiles.
- **`upvotePost(Post post)`**: Manages upvoting of posts in the feed.
- **`comment(Post post, String msg)`**: Adds comments to posts in the feed.

#### Testing
- Verified filtering of posts and interaction functionality.

### 5. `Server`

#### Description
The `Server` class sets up and manages the server infrastructure for handling client connections. It listens for incoming client requests, processes them, and maintains the overall server state.

#### Functionality
- **Server Management**: Listens for client connections and handles incoming requests.
- **Thread Management**: Uses a thread pool to handle multiple client connections concurrently.

#### Contribution
- **Client Handling**: Manages client connections and delegates tasks to `ClientHandler` instances.
- **Server Lifecycle**: Handles starting and stopping of the server.

#### Methods
- **`startServer()`**: Initializes and starts the server, listening for client connections.
- **`stopServer()`**: Stops the server and closes resources.

#### Testing
- Validated server startup, client connection handling, and shutdown processes.

### 6. `Client`

#### Description
The `Client` class represents a client connecting to the server. It manages communication with the server, sending requests, and receiving responses.

#### Functionality
- **Connection Management**: Establishes and maintains a connection to the server.
- **Communication**: Sends and receives messages between the client and server.

#### Contribution
- **Client-Server Interaction**: Manages the exchange of data between the client and server.

#### Methods
- **`connect()`**: Establishes a connection to the server.
- **`send(String message)`**: Sends messages to the server.
- **`receive()`**: Receives responses from the server.

#### Testing
- Validated connection setup and message exchange with the server.

### 7. `ClientHandler`

#### Description
The `ClientHandler` class manages client-server interactions. Each instance handles requests from a specific client, processes commands, and communicates responses. It implements the `IClientHandler` interface.

#### Functionality
- **Request Handling**: Processes various client requests such as sign up, login, follow/unfollow, and post management.
- **Server Communication**: Manages communication between the client and the server using `ObjectInputStream` and `ObjectOutputStream`.
- **Profile Management**: Handles operations related to user profiles including following, blocking, and editing profiles.

#### Contribution
- **Client Management**: Ensures efficient handling of client requests and maintains connection integrity.
- **Error Handling**: Provides robust error handling for various scenarios such as invalid inputs and connection issues.

#### Methods
- `ClientHandler(Socket clientSocket, Base base)`: Constructor to initialize the client handler with the client socket and base.
- `void run()`: Main method that handles client requests and manages server-client communication.
- `void handleSignUp(ObjectInputStream ois, ObjectOutputStream oos)`: Processes sign-up requests.
- `void handleLogin(ObjectInputStream ois, ObjectOutputStream oos)`: Processes login requests.
- `void handleFollow(ObjectInputStream ois, ObjectOutputStream oos)`: Handles follow requests.
- `void handleUnfollow(ObjectInputStream ois, ObjectOutputStream oos)`: Handles unfollow requests.
- `void handleBlock(ObjectInputStream ois, ObjectOutputStream oos)`: Processes block requests.
- `void handleUnblock(ObjectInputStream ois, ObjectOutputStream oos)`: Handles unblock requests.
- `void handleViewPosts(ObjectOutputStream oos)`: Retrieves and sends the following posts to the client.
- `void handleListMyPosts(ObjectInputStream ois, ObjectOutputStream oos)`: Retrieves and sends the user's posts to the client.
- `void handleMakePost(ObjectInputStream ois, ObjectOutputStream oos)`: Creates a new post.
- `void handleRemovePost(ObjectInputStream ois, ObjectOutputStream oos)`: Removes a specified post.
- `void handleHidePost(ObjectInputStream ois)`: Hides a specified post.
- `void handleViewHidePost(ObjectOutputStream oos)`: Retrieves and sends the hidden posts to the client.
- `void handleMakeComment(ObjectInputStream ois, ObjectOutputStream oos)`: Adds a comment to a post.
- `void handleDeleteComment(ObjectInputStream ois, ObjectOutputStream oos)`: Deletes a comment from a post.
- `void handleUpvotePost(ObjectInputStream ois)`: Adds an upvote to a post.
- `void handleDownvotePost(ObjectInputStream ois)`: Adds a downvote to a post.
- `void handleUpvoteComment(ObjectInputStream ois)`: Adds an upvote to a comment.
- `void handleDownvoteComment(ObjectInputStream ois)`: Adds a downvote to a comment.
- `void handleShowNumComments(ObjectInputStream ois, ObjectOutputStream oos)`: Shows the number of comments on a post.
- `void handleShowNumFollowing(ObjectInputStream ois, ObjectOutputStream oos)`: Shows the number of users a profile is following.
- `void handleSearchUser(ObjectInputStream ois, ObjectOutputStream oos)`: Searches for a user by username.
- `void handleViewProfile(ObjectInputStream ois, ObjectOutputStream oos)`: Retrieves and sends profile information.
- `void handleEditProfile(ObjectInputStream ois, ObjectOutputStream oos)`: Edits user profile information.
- `void handleGetUserInfo(ObjectInputStream ois, ObjectOutputStream oos)`: Retrieves and sends user information.
- `void handleGetAllUser(ObjectOutputStream oos)`: Retrieves and sends a list of all users.
- `void handleGetFollowing(ObjectOutputStream oos)`: Retrieves and sends the list of users that the current profile is following.
- `void handleGetThisFollowing(ObjectInputStream ois, ObjectOutputStream oos)`: Retrieves and sends the following list of a specific user.
- `void handleGetBlockList(ObjectOutputStream oos)`: Retrieves and sends the block list of the current profile.

#### Testing
- Validated handling of various client requests and server communication.
- Verified error handling and connection management.
- Tested profile management functionalities and interaction with the `Base` class.


### 8. `Base`

#### Description
The `Base` class manages the core data for the platform, including user profiles, posts, and hidden posts. It handles file operations for persistent storage of data.

#### Functionality
- **Data Management**: Reads and writes user and post data to/from files.
- **Data Retrieval**: Provides methods for accessing user profiles and posts.

#### Contribution
- **Data Storage**: Ensures data persistence and retrieval across server restarts.
- **Data Access**: Facilitates access to user and post information for other classes.

#### Methods
- `updateFiles()`: Updates user and post files.
- `searchUser(String username)`: Searches for a user by username.
- `searchPost(Post post)`: Searches for a post.
- `getUserInfo(Profile toGet)`: Retrieves basic user information.
- `signUp(String username, String password, int age, String gender)`: Registers a new user.
- `login(String username, String password)`: Authenticates a user.
- `follow(Profile profile, Profile toAdd)`: Allows a user to follow another user.
- `unFollow(Profile profile, Profile toUnfollow)`: Allows a user to unfollow another user.
- `block(Profile profile, Profile toBlock)`: Allows a user to block another user.
- `unBlock(Profile profile, Profile unBlock)`: Allows a user to unblock another user.
- `readUserListFile()`: Reads user data from a file.
- `writeUserListFile()`: Writes user data to a file.
- `readPostListFile()`: Reads post data from a file.
- `writePostListFile()`: Writes post data to a file.
- `readHidePostListFile()`: Reads hidden post data from a file.
- `writeHidePostListFile(Profile user, Post post)`: Writes hidden post data to a file.
- `readAllListFile()`: Reads all data from files.
- `clearUsers()`: Clears the user list.
- `clearAllPosts()`: Clears the post list.
- `getUsers()`: Returns the list of users.
- `editUserInfo(Profile p, int age, String gender, String password)`: Edits user information.
- `getAllPosts()`: Returns the list of all posts.
- `makeNewPost(Profile poster, String message)`: Creates a new post.
- `removePost(Profile poster, Post post)`: Removes a post.
- `hidePost(String mainUser, String poster, String message)`: Hides a post for a user.
- `unHidePost(Profile profile, Post post)`: Unhides a post for a user.
- `addUpvote(Post post)`: Adds an upvote to a post.
- `addDownvote(Post post)`: Adds a downvote to a post.
- `addUpvote(Post post, Comment comment)`: Adds an upvote to a comment.
- `addDownvote(Post post, Comment comment)`: Adds a downvote to a comment.
- `makeComment(Post post, Profile commenter, String message)`: Adds a comment to a post.
- `deleteComment(Post post, Profile profile, Comment comment)`: Deletes a comment from a post.

#### Testing
- Validated file operations and data retrieval.
- Verified user authentication methods (`login` and `signup`).

### 9. `UserNotFoundException`

#### Description
The `UserNotFoundException` class is a custom exception that we used to indicate errors when user operations reference non-existent users.

#### Functionality
- **Exception Handling**: Provides a mechanism for handling errors related to missing user data.

### 10. `BlockListGUI`

#### Description
The `BlockListGUI` class provides the GUI for managing a user's block list. It allows users to view and unblock profiles from their block list. This class extends `JFrame` and implements `Runnable` to run the GUI in a separate thread.

#### Functionality
- **Display Block List**: Shows the list of blocked users in a GUI.
- **Unblock Users**: Provides functionality to unblock selected users.
- **File Operations**: Handles input and output streams for server communication.

#### Contribution
- **User Interaction**: Enhances user experience by providing a visual interface to manage the block list.
- **Server Communication**: Ensures smooth communication with the server for retrieving and updating block list data.

#### Methods
- `BlockListGUI(Profile currentUser, ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with the current user and I/O streams.
- `void run()`: Initializes and displays the GUI.
- `void initializeGUI()`: Sets up the GUI components and layout.
- `void loadBlockList()`: Loads the block list from the server.
- `Profile getUserByUsername(String username)`: Retrieves a `Profile` object for a given username.

#### Testing
- Verified GUI display and responsiveness.
- Tested server communication for loading and unblocking users.
- Ensured correct handling of input/output streams.

### 11. `EditProfileGUI`

#### Description
The `EditProfileGUI` class is the GUI for editing a user's profile. It allows the user to update their age, gender, and password. It interacts with a server through object input and output streams to apply and save these changes.

#### Functionality
- **Profile Editing**: Provides fields for updating user information like age and gender.
- **Password Management**: Offers functionality to change and confirm a new password.
- **Communication**: Communicates with the server to send profile updates and receive feedback.

#### Contribution
- **User Interface**: Facilitates user interaction for profile modifications with a user-friendly GUI.
- **Server Interaction**: Handles communication with the server to update and confirm profile changes.

#### Methods
- `EditProfileGUI(Profile p, ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with a profile and streams for server communication.
- `void run()`: Initializes and displays the profile editing interface, including age and gender fields, and buttons for submitting changes, changing password, and going back.
- `String changePasswordGUI()`: Displays a dialog for the user to enter a new password.
- `boolean confirmPasswordGUI(String pass)`: Prompts the user to confirm the new password and checks if it matches the original.

#### Testing
- Validated correct GUI initialization and layout of text fields and buttons.
- Ensured proper interaction with the server for updating profile details and handling responses.
- Verified password change functionality, including new password entry and confirmation.
- Tested GUI responses for different actions like submitting changes, changing password, and navigating back.

### 12. `FollowingGUI`

#### Description
The `FollowingGUI` class represents the GUI for managing the list of users that the current user is following. It allows users to view their following list, and provides options to unfollow or block users.

#### Functionality
- **View Following List**: Displays a list of users that the current user is following.
- **Unfollow User**: Removes a selected user from the following list.
- **Block User**: Blocks a selected user and removes them from the following list.
- **Server Communication**: Sends requests and processes responses from the server to update the following list.

#### Contribution
- **User Interaction**: Facilitates user interaction for managing the following list with an intuitive GUI.
- **Server Interaction**: Handles communication with the server for retrieving the following list and processing user management requests.

#### Methods
- `FollowingGUI(Profile currentUser, ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with the current user's profile and server communication streams.
- `void run()`: Initializes and displays the GUI, setting it to be visible and centered on the screen.
- `void initializeGUI()`: Sets up the GUI components including the following list, buttons for unfollowing and blocking users, and handles layout and event listeners.
- `void loadFollowingList()`: Loads the list of users that the current user is following from the server and updates the `JList` model.
- `Profile getUserByUsername(String username)`: Retrieves a `Profile` object of a user based on the username by communicating with the server.

#### Testing
- Validated GUI initialization and layout of components including buttons and list display.
- Ensured functionality for loading and displaying the following list.
- Verified unfollow and block actions, including correct server communication and handling responses.
- Tested error handling for server communication issues and user management actions.

### 13. `InitialGUI`

#### Description
The `InitialGUI` class provides the initial GUI for user authentication, allowing users to either sign up or log in to the DunstaGram platform.

#### Functionality
- **Welcome Screen**: Displays a welcome message and options for user authentication.
- **Sign Up**: Initiates the sign-up process when the "Sign Up" button is clicked.
- **Login**: Initiates the login process when the "Login" button is clicked.
- **Server Communication**: Sends commands to the server to indicate the chosen action (sign-up or login).

#### Contribution
- **User Authentication**: Provides a straightforward entry point for users to either sign up or log in.
- **Server Interaction**: Communicates user choices to the server to handle authentication.

#### Methods
- `InitialGUI(ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with server communication streams.
- `void run()`: Sets up and displays the initial login menu GUI, including buttons for sign-up and login.

#### Testing
- Verified that the GUI displays the welcome message and buttons correctly.
- Tested that clicking "Sign Up" or "Login" disposes of the frame and sends the correct command to the server.
- Ensured error handling for IOException during server communication.

### 14. `LoginGUI`

#### Description
The `LoginGUI` class provides the GUI for user login and account creation. It handles user authentication by communicating with the server and displays appropriate messages based on the login status.

#### Functionality
- **Login Interface**: Provides fields for username and password input.
- **Login Action**: Sends login credentials to the server and processes the server's response.
- **Sign Up Redirection**: Redirects to the sign-up interface if the user does not have an account.
- **Error Handling**: Displays error messages for login failures or communication issues.

#### Contribution
- **User Authentication**: Allows users to log in and handles the transition to the main GUI upon successful login.
- **Sign Up**: Redirects users to the sign-up interface if they do not have an account.
- **Resource Management**: Closes connections and resources properly when the window is closed.

#### Methods
- `LoginGUI(ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with server communication streams.
- `void run()`: Sets up and displays the login menu GUI, including fields for username and password, and buttons for login and sign-up.
- `ActionListener actionListener`: Handles actions for login and sign-up buttons, including server communication and response processing.

#### Testing
- Verified that the GUI displays fields and buttons correctly.
- Tested that clicking "Login" sends the correct credentials to the server and handles responses appropriately.
- Ensured that the "Sign Up" button redirects to the sign-up interface.
- Checked that the application closes resources and connections properly when the window is closed.

### 15. `MainGUI`

#### Description
The `MainGUI` class provides the main user interface for our DunstaGram application, allowing users to view posts, make new posts, manage posts, and interact with comments. It supports various actions like searching, following users, adjusting settings, and handling user comments and posts.

#### Functionality
- **Post Management**: Allows users to view, upvote, downvote, hide, and refresh posts.
- **Comment Management**: Facilitates commenting on posts, upvoting, downvoting, and deleting comments.
- **Navigation**: Provides buttons for searching, following, settings, and posting.
- **Dynamic Refresh**: Updates the GUI with the latest posts and comments from the server.

#### Contribution
- **User Interaction**: Manages interactions with posts and comments, including creating, updating, and deleting posts and comments.
- **Resource Management**: Handles socket connections and ensures proper cleanup of resources when the GUI is closed.
- **GUI Updates**: Refreshes the user interface to reflect the latest data from the server.

#### Methods
- `MainGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with user profile and server communication streams.
- `void run()`: Sets up and displays the main GUI, including panels for posts, comments, and user interactions.
- `void displayCommentGUI(Post post)`: Displays the comment interface for a selected post.
- `void displayPostGUI()`: Displays the interface for creating a new post.
- `static JTextField getjTextField()`: Returns a configured `JTextField` for user input with placeholder text.
- `void generatePostPanel()`: Generates and displays panels for each post, including buttons for interactions.
- `JLabel showDunstaLogo()`: Returns a `JLabel` containing the DunstaGram logo.
- `void refresh()`: Refreshes the main GUI by re-displaying posts.
- `void receivePostList()`: Retrieves and updates the list of posts from the server.
- `private void refreshComments(Post post)`: Refreshes and displays comments for a given post.

#### Testing
- Verified that the GUI displays correctly and handles user actions for posts and comments.
- Tested interaction buttons (e.g., upvote, downvote, hide post) to ensure they send the correct data to the server and update the GUI.
- Ensured that commenting, upvoting, and downvoting functionality works as expected, including error handling for server communication issues.
- Checked that the GUI refreshes properly to show the latest posts and comments.

### 16. ManagePostGUI

#### Description
`ManagePostGUI` is the GUI that allows users to manage their posts on the DunstaGram platform. Users can view, delete posts, and review comments associated with their posts.

#### Functionality
- Displays a window where users can see a list of their posts.
- Provides options to delete posts or review comments on individual posts.
- Allows users to inspect and delete individual comments on their posts.

#### Contribution
- **Constructor**: Initializes the GUI, retrieves the user's posts, and sets up the main frame.
- **run()**: Sets up the main frame and populates it with the user's posts.
- **showOption(Post p)**: Presents options to delete the post or review its comments. Executes the selected action.
- **showComments(Post post)**: Displays a new window with a list of comments on the selected post.
- **inspectComment(Post p, Comment c)**: Opens a window to inspect and possibly delete a specific comment.

#### Methods

- `public ManagePostGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) throws IOException, ClassNotFoundException`: Constructor that initializes the `ManagePostGUI` with the given user, input, and output streams. Fetches the user's posts from the server.
- `public void run()`: Initializes and displays the main frame for managing posts. Populates it with the user's posts and sets up action listeners.
- `public boolean showOption(Post p) throws IOException, ClassNotFoundException`: Shows options to delete the post or review its comments. Executes the chosen action and returns whether the post was removed.
- `private void showComments(Post post)`: Displays a new window with comments on the selected post. Allows the user to select and inspect individual comments.
- `public void inspectComment(Post p, Comment c)`: Opens a window to inspect a specific comment. Provides an option to delete the comment.

#### Testing
- Verified that the `ManagePostGUI` initializes properly and retrieves the user's posts.
- Checked that the GUI is correctly populated with posts and that action listeners function as expected.
- Ensured that options for deleting posts and reviewing comments work correctly and that actions are performed as intended.
- Validated that comments are displayed correctly and that the user can select and inspect comments.
- Tested that the comment inspection window functions correctly and allows comments to be deleted if needed.

### 17. `NewSearchGUI`

#### Description
The `NewSearchGUI` class provides the GUI for searching user profiles within the DunstaGram application. It allows users to input a username, search for profiles, and view profile details. The class also handles server communication for performing searches and following users.

#### Functionality
- **Search Interface**: Provides a search bar and button to input and submit search queries.
- **Profile Display**: Displays search results as clickable buttons that show profile details when selected.
- **Profile Details**: Presents detailed information about a user profile, including options to follow other users.
- **Server Communication**: Sends search queries and follow requests to the server and handles server responses.

#### Contribution
- **User Interaction**: Manages user inputs for searching and viewing profiles, including updating the GUI with search results.
- **Server Communication**: Handles communication with the server to perform searches and manage follow requests.
- **GUI Management**: Updates and refreshes the user interface to display search results and profile details.

#### Methods
- `NewSearchGUI(ObjectInputStream ois, ObjectOutputStream oos, Profile viewer)`: Constructor to initialize the GUI with server communication streams and the current user profile.
- `void run()`: Sets up and displays the main search GUI, including panels for search input and results.
- `private boolean searchProfile() throws IOException, ClassNotFoundException`: Performs a search query and updates the results panel based on the server response.
- `private void showProfileDetails(String username, String age, String gender, String following, String posts)`: Displays detailed profile information in a dialog, including an option to follow the user if applicable.

#### Testing
- Verified that the search functionality works correctly, including handling server responses and displaying search results.
- Tested profile detail display to ensure accurate information is shown and follow actions are correctly processed.
- Ensured that error handling works for scenarios such as no users found or communication issues with the server.
- Checked that the user interface updates and refreshes appropriately based on user interactions and server data.

### 18. `SearchGUI`

#### Description
The `SearchGUI` class provides the GUI for searching and managing user profiles in the DunstaGram application. It allows users to search for other profiles, view profile details, and perform actions such as following or blocking users. The class handles interactions with the server to fetch user profiles and process follow/block requests.

#### Functionality
- **Search Interface**: Provides a search bar and button for users to input and submit search queries.
- **Profile List Management**: Loads all user profiles from the server and displays search results based on user input.
- **Profile Actions**: Allows users to follow or block other profiles, and displays detailed profile information.
- **Server Communication**: Handles communication with the server to load profiles, follow, and block users.

#### Contribution
- **User Interaction**: Manages user inputs for searching, viewing profiles, and performing actions like follow and block.
- **Server Communication**: Facilitates interaction with the server to load profiles and process follow/block requests.
- **GUI Management**: Updates and refreshes the user interface to display search results and profile details.

#### Methods
- `SearchGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the GUI with the current user profile and server communication streams.
- `void run()`: Initializes and displays the search GUI.
- `void initializeGUI()`: Sets up the GUI layout and components, including the search bar and results panel.
- `private void loadProfiles()`: Loads all user profiles from the server.
- `private void searchProfiles()`: Searches through the loaded profiles and updates the results panel with matching profiles.
- `private void showProfileDetails(Profile profile)`: Displays detailed information about a selected profile, including options to follow or block the user.
- `private String getFriendsList(Profile profile)`: Retrieves and formats the list of friends for the specified profile.

#### Testing
- Verified that the search functionality accurately filters and displays user profiles.
- Tested profile detail display to ensure correct information and functionality for follow and block actions.
- Ensured that server communication works for loading profiles, and handling follow and block requests.
- Checked error handling for scenarios like server communication issues and invalid user actions.

### 19. `SettingsGUI`

#### Description
The `SettingsGUI` class provides the GUI for managing user settings in the DunstaGram application. It allows users to view and edit their profile, manage their block list, and log out of the application. The class handles interactions with the server to perform these actions and update the GUI accordingly.

#### Functionality
- **Profile Management**: Enables users to view and edit their profile.
- **Block List Management**: Provides access to manage the user's block list.
- **Logout**: Allows users to log out of the application.
- **Navigation**: Includes options to navigate back to the main GUI or log out.

#### Contribution
- **User Interaction**: Manages user interactions for viewing, editing profiles, managing block lists, and logging out.
- **Server Communication**: Facilitates communication with the server to perform profile-related actions and handle user logout.
- **GUI Management**: Updates and displays the GUI for various user settings and actions.

#### Methods
- `SettingsGUI(Profile p, ObjectInputStream in, ObjectOutputStream out)`: Constructor to initialize the GUI with the user's profile and server communication streams.
- `void run()`: Sets up and displays the settings GUI, including buttons for profile management, block list management, logout, and navigation.
- `void initializeGUI()`: Configures the layout and components of the settings window, including buttons for different actions.
- `private void viewProfileAction()`: Handles viewing the user's profile and opens the `ViewProfileGUI`.
- `private void logoutAction()`: Manages user logout and transitions to the `LoginGUI`.
- `private void backAction()`: Navigates back to the `MainGUI`.
- `private void editProfileAction()`: Opens the `EditProfileGUI` for editing user details.
- `private void blockListAction()`: Opens the `BlockListGUI` to manage the block list.

#### Testing
- Verified that all buttons and actions in the settings GUI function correctly.
- Tested interactions for viewing, editing profiles, and managing the block list.
- Ensured logout functionality works as expected and transitions to the login screen.
- Checked that the GUI disposes of properly and handles user actions without errors.

### 20. `SignUpGUI`

#### Description
The `SignUpGUI` class provides the GUI for user registration in the DunstaGram application. It allows users to create a new account by entering their username, password, age, and gender. The class handles user input, communicates with the server to process sign-up requests, and manages the UI components related to the sign-up process.

#### Functionality
- **User Registration**: Allows users to enter their credentials and register a new account.
- **Form Validation**: Ensures user input is valid and provides feedback for errors (e.g., username already taken, invalid age).
- **Navigation**: Provides options to return to the login screen if the user decides not to sign up.

#### Contribution
- **User Interaction**: Manages user inputs for registration and provides feedback based on server responses.
- **Server Communication**: Sends user data to the server and handles responses to determine the success or failure of the registration.
- **GUI Management**: Configures and displays the sign-up form and handles window closing events.

#### Methods
- `SignUpGUI(ObjectInputStream ois, ObjectOutputStream oos)`: Constructor to initialize the sign-up GUI with server communication streams.
- `void run()`: Sets up and displays the sign-up GUI, including form fields and buttons for user registration and navigation.
- `void signUpUser()`: Retrieves user input, sends it to the server for registration, and handles the server's response to display appropriate messages.
- `void writeObject()`: Writes user registration data to the output stream for processing by the server.

#### Testing
- Verified that the sign-up form displays correctly and handles user input.
- Tested the `signUpUser()` method to ensure it correctly sends registration data to the server and handles various server responses.
- Checked the functionality of the "Back To Login" button to ensure it navigates to the `LoginGUI` without issues.
- Ensured that the GUI handles window closing events properly, including resource cleanup and communication with the server.

### 21. `ViewProfileGUI`

#### Description
The `ViewProfileGUI` class is responsible for displaying a user's profile information in GUI. It takes user information in the form of a list of strings and presents it in a visually organized manner, including profile picture, username, age, gender, and additional details such as posts and followers.

#### Functionality
- **Profile Display**: Presents the user's profile details, including a profile picture, username, age, gender, and other relevant information.
- **User Feedback**: Uses a dialog box to show the profile information to the user.

#### Contribution
- **Profile Visualization**: Converts user information into a graphical representation, making it easier for users to view and interact with their profile data.
- **UI Layout**: Utilizes Swing components to create a user-friendly interface for displaying profile information.

#### Methods
- `ViewProfileGUI(ArrayList<String> userInfo)`: Constructor to initialize the profile viewer with user information.
- `void run()`: Executes the profile display logic by calling `showProfile()`.
- `private void showProfile()`: Creates a `JPanel` to layout the profile information and displays it in a `JOptionPane`.

#### Testing
- Verified that the `showProfile()` method correctly displays all user profile information.
- Tested the `ViewProfileGUI` class to ensure the profile picture and user details are presented correctly.
- Checked the proper functioning of the `JOptionPane` to ensure it displays the profile panel as intended.

