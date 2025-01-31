package com.example.socialmediaapp.service;

import java.util.List;

import com.example.socialmediaapp.dto.PostsDto;
import com.example.socialmediaapp.enums.InteractionType;

public interface SocialMediaService {
    void registerUser(int userId, String username);
    String uploadPost(int userId, String content);
    void interactWithUser(InteractionType action, int userId1, int userId2);
    List<PostsDto> getFeed(int userId);
    void interactWithPost(InteractionType action, int userId, String postId);    
}
