# Social Media Console Application

This is a simple social media application implemented in Java using Spring Boot, designed to run in a console environment. It allows users to register, upload posts, follow/unfollow other users, view their feed, and interact with posts (like/dislike).  No database or web API is used; data is stored in memory.

## Features

*   **User Registration:** Users can register with a unique ID and username.
*   **Post Upload:** Users can upload posts with content. Each post is assigned a unique ID.
*   **Following/Unfollowing:** Users can follow or unfollow other users.
*   **Feed View:** Users can view a feed of posts. The feed prioritizes posts from followed users, sorted by posting time (most recent first). Posts from non-followed users are displayed afterward, also sorted by posting time.
*   **Post Interaction:** Users can like or dislike posts.
*   **Console Interface:** The application interacts with users through a command-line interface.

## Technologies Used

*   Java
*   Spring Boot
*   Lombok (for reducing boilerplate code)

## Commands

*   `RegisterUser <user_id> <user_name>`: Register a new user.  Example: `RegisterUser 1 Akash`
*   `UploadPost <user_id> <post_content>`: Upload a post. Example: `UploadPost 1 "Hello, world!"`
*   `InteractWithUser <FOLLOW/UNFOLLOW> <user_id1> <user_id2>`: Follow/Unfollow a user. Example: `InteractWithUser FOLLOW 2 1`
*   `ShowFeed <user_id>`: View the user's feed. Example: `ShowFeed 1`
*   `InteractWithPost <LIKE/DISLIKE> <user_id> <post_id>`: Like/Dislike a post. Example: `InteractWithPost LIKE 2 001`
*   `help`: Display available commands.
*   `exit`: Quit the application.