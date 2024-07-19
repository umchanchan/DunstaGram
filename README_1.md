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
The `Comment` class represents comments made on posts. It includes details about the commenter, the content of the comment, and interactions like upvoting and downvoting.

#### Functionality
- **Comment Content**: Stores the content of the comment and the profile of the commenter.
- **Interaction Tracking**: Manages upvotes and downvotes specific to comments.

#### Contribution
- **User Engagement**: Allows users to engage with posts by adding comments.
- **Interaction Management**: Tracks the popularity and engagement level of comments through upvotes and downvotes.

#### Methods
- **Constructors**: Initialize comment with content and commenter.
- **`toString()`**: Provides a formatted string representation of the comment.
- **`addUpvote()`**: Increments upvote count.
- **`addDownvote()`**: Increments downvote count.

#### Testing
- Validated creation of comments and interaction functionality.

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
The `ClientHandler` class manages communication with a single client, processing client requests, and interacting with the `Base` object to perform operations.

#### Functionality
- **Request Processing**: Handles incoming client requests and performs necessary operations.
- **Data Interaction**: Interfaces with the `Base` class to manage user and post data.

#### Contribution
- **Request Handling**: Processes client requests and ensures correct interaction with the server’s data.

#### Methods
- **`run()`**: Continuously processes client requests and sends responses.
- **`processRequest(String request)`**: Handles and processes individual client requests.

#### Testing
- Verified handling of client requests and interaction with the `Base` object.

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
- **`readUserListFile()`**: Reads user data from a file.
- **`readPostListFile()`**: Reads post data from a file.
- **`readHidePostListFile()`**: Reads hidden post data from a file.
- **`saveUserListFile()`**: Saves user data to a file.
- **`savePostListFile()`**: Saves post data to a file.
- **`getUser(String username)`**: Retrieves a user profile.
- **`getPost(String postId)`**: Retrieves a post.
- **`login(String username, String password)`**: Validates user login credentials.
- **`signup(String username, String password)`**: Registers a new user profile.

#### Testing
- Validated file operations and data retrieval.
- Verified user authentication methods (`login` and `signup`).

### 9. `UserNotFoundException`

#### Description
The `UserNotFoundException` class is a custom exception used to indicate errors when user operations reference non-existent users.

#### Functionality
- **Exception Handling**: Provides a mechanism for handling errors related to missing user data.

