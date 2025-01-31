package com.example.socialmediaapp.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private int userId;
    private String username;
    private Set<UsersDto> following;
    private List<PostsDto> posts;

    public UsersDto(int userId, String username) {
        this.userId = userId;
        this.username = username;
        this.following = new HashSet<>();
        this.posts = new ArrayList<>();
    }

    public void followUser(UsersDto user) {
        this.following.add(user);
    }

    public void unfollowUser(UsersDto user) {
        this.following.remove(user);
    }
}
