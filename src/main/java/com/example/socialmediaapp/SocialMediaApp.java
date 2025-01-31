package com.example.socialmediaapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.socialmediaapp.dto.PostsDto;
import com.example.socialmediaapp.dto.UsersDto;
import com.example.socialmediaapp.enums.InteractionType;
import com.example.socialmediaapp.exceptions.PostNotFoundException;
import com.example.socialmediaapp.exceptions.UserAlreadyExistsException;
import com.example.socialmediaapp.exceptions.UserNotFoundException;
import com.example.socialmediaapp.serviceImpl.SocialMediaServiceImpl;

@Component
public class SocialMediaApp {

    @Autowired
    private SocialMediaServiceImpl service;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (true) {
            System.out.println("Enter command:");
            String input = scanner.nextLine();
            String[] commands = input.split(" ", 3);

            try {
                if (commands[0].equalsIgnoreCase("RegisterUser")) {
                    int userId = Integer.parseInt(commands[1]);
                    String username = commands[2];
                    service.registerUser(userId, username);
                    System.out.println(username + " Registered!!");

                } else if (commands[0].equalsIgnoreCase("UploadPost")) {
                    int userId = Integer.parseInt(commands[1]);
                    String content = commands[2];
                    String postId = service.uploadPost(userId, content);
                    System.out.println("Upload Successful with post ID: " + postId);

                } else if (commands[0].equalsIgnoreCase("InteractWithUser")) {
                    InteractionType action = InteractionType.valueOf(commands[1].toUpperCase());
                    int userId1 = Integer.parseInt(commands[2].split(" ")[0]);
                    int userId2 = Integer.parseInt(commands[2].split(" ")[1]);
                    service.interactWithUser(action, userId1, userId2);
        
                    String message = (action == InteractionType.FOLLOW) ? "Followed " : "Unfollowed ";
                    System.out.println(message + " successfully!");
        
                    List<UsersDto> followers = service.getFollowers(userId2);
                    if (!followers.isEmpty()) {
                        System.out.println("You are following:");
                        for (UsersDto follower : followers) {
                            System.out.println("- " + follower.getUsername());
                        }
                    } else {
                        System.out.println("You has no followers yet.");
                    } 

                } else if (commands[0].equalsIgnoreCase("ShowFeed")) {
                    int userId = Integer.parseInt(commands[1]);
                    List<PostsDto> feed = service.getFeed(userId);

                    if (feed.isEmpty()) {
                        System.out.println("No posts available.");
                    } else {
                        for (PostsDto post : feed) {
                            System.out.println("UserName - " + post.getUsername());
                            System.out.println("Post - " + post.getContent());
                            System.out.println("Post time - " + post.getRelativeTime());
                            System.out.println("# of Likes - " + post.getLikes());
                            System.out.println("# of Dislikes - " + post.getDislikes());
                            System.out.println();
                        }
                    }

                } else if (commands[0].equalsIgnoreCase("InteractWithPost")) {
                    InteractionType action = InteractionType.valueOf(commands[1].toUpperCase());
                    int userId = Integer.parseInt(commands[2].split(" ")[0]);
                    String postId = commands[2].split(" ")[1];
                    service.interactWithPost(action, userId, postId);
                    System.out.println("Post Interaction Successful!!");

                } else if (input.equalsIgnoreCase("exit")) {
                    break;
                } else if (input.equalsIgnoreCase("help")) {
                    printHelp();
                } else {
                    System.out.println("Invalid command. Type 'help' for available commands.");
                }
            } catch (UserNotFoundException | PostNotFoundException | UserAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid interaction type: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace(); 
            }
        }

        scanner.close();
    }

    public void printHelp() {
        System.out.println("Available Commands:");
        System.out.println("1. RegisterUser <user_id> <user_name> - Register a new user");
        System.out.println("   Example: RegisterUser 1 Akash");
        System.out.println("2. UploadPost <user_id> <post> - Upload a post");
        System.out.println("   Example: UploadPost 1 \"This is my first post.\"");
        System.out.println("3. InteractWithUser <FOLLOW/UNFOLLOW> <user_id1> <user_id2> - Follow/Unfollow a user");
        System.out.println("   Example: InteractWithUser FOLLOW 2 1");
        System.out.println("4. ShowFeed <user_id> - Show feed for a user");
        System.out.println("   Example: ShowFeed 1");
        System.out.println("5. InteractWithPost <LIKE/DISLIKE> <user_id> <post_id> - Like/Dislike a post");
        System.out.println("   Example: InteractWithPost LIKE 2 001");
        System.out.println("6. Type 'exit' to quit the program");
        System.out.println("7. Type 'help' to see available commands");
    }
}