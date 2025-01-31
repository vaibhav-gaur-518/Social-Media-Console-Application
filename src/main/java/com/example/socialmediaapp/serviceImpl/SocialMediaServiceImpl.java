package com.example.socialmediaapp.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.socialmediaapp.dto.PostsDto;
import com.example.socialmediaapp.dto.UsersDto;
import com.example.socialmediaapp.enums.InteractionType;
import com.example.socialmediaapp.exceptions.PostNotFoundException;
import com.example.socialmediaapp.exceptions.UserAlreadyExistsException;
import com.example.socialmediaapp.exceptions.UserNotFoundException;
import com.example.socialmediaapp.service.SocialMediaService;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private Map<Integer, UsersDto> users = new HashMap<>();
    private Map<String, PostsDto> posts = new HashMap<>();
    private int postCounter = 1;

    @Override
    public void registerUser(int userId, String username) {
        if (users.containsKey(userId)) {
            throw new UserAlreadyExistsException("User with ID " + userId + " already exists.");
        }
        UsersDto newUser = new UsersDto(userId, username);
        users.put(userId, newUser);
    }

    @Override
    public String uploadPost(int userId, String content) {
        UsersDto user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }
        String postId = String.format("%03d", postCounter++);
        PostsDto newPost = new PostsDto(postId, content, user.getUsername());
        user.getPosts().add(newPost);
        posts.put(postId, newPost);
        return postId.toString();
    }

    @Override
    public void interactWithUser(InteractionType action, int userId1, int userId2) {
        UsersDto user1 = users.get(userId1);
        UsersDto user2 = users.get(userId2);

        if (user1 == null || user2 == null) {
            throw new UserNotFoundException("User(s) not found.");
        }

        if (action == InteractionType.FOLLOW) {
            user1.followUser(user2);
        } else if (action == InteractionType.UNFOLLOW) {
            user1.unfollowUser(user2);
        }
    }

    public List<UsersDto> getFollowers(int userId) {
        UsersDto user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        List<UsersDto> followers = new ArrayList<>();
        for (UsersDto otherUser : users.values()) {
            if (otherUser.getFollowing().contains(user)) {
                UsersDto followerDto = new UsersDto();
                followerDto.setUserId(otherUser.getUserId());
                followerDto.setUsername(otherUser.getUsername());
                followers.add(followerDto);
            }
        }
        return followers;
    }

    @Override
    public List<PostsDto> getFeed(int userId) {
        UsersDto currentUser = users.get(userId);
        if (currentUser == null) {
            throw new UserNotFoundException("User not found!");
        }

        List<PostsDto> feed = new ArrayList<>();

        List<PostsDto> followedPosts = currentUser.getFollowing().stream()
                .flatMap(followedUser -> followedUser.getPosts().stream()) 
                .collect(Collectors.toList());

        List<PostsDto> otherPosts = users.values().stream()
                .filter(user -> !currentUser.getFollowing().contains(user) && user != currentUser) 
                .flatMap(user -> user.getPosts().stream())
                .collect(Collectors.toList());

        followedPosts.sort(Comparator.comparing(PostsDto::getPostTime).reversed());

        otherPosts.sort(Comparator.comparing(PostsDto::getPostTime).reversed());

        for (PostsDto post : followedPosts) {
            feed.add(convertToDto(post));
        }
        for (PostsDto post : otherPosts) {
            feed.add(convertToDto(post));
        }
        return feed;
    }

    private PostsDto convertToDto(PostsDto post) {
        PostsDto postDto = new PostsDto();
        postDto.setPostId(post.getPostId());
        postDto.setContent(post.getContent());
        postDto.setPostTime(post.getPostTime());
        postDto.setRelativeTime(post.getRelativeTime());
        postDto.setLikes(post.getLikes());
        postDto.setDislikes(post.getDislikes());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    @Override
    public void interactWithPost(InteractionType action, int userId, String postId) {
        UsersDto user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        PostsDto post = posts.get(postId);
        if (post == null) {
            throw new PostNotFoundException("Post not found!");
        }

        if (action == InteractionType.LIKE) {
            post.likePost();
        } else if (action == InteractionType.DISLIKE) {
            post.dislikePost();
        }
    }
}