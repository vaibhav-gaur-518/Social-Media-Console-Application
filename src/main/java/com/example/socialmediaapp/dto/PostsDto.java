package com.example.socialmediaapp.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDto {
    private String postId;
    private String content;
    private String relativeTime;
    private Date postTime;
    private int likes;
    private int dislikes;
    private String username;

    public PostsDto(String postId, String content, String username) {
        this.postId = postId;
        this.content = content;
        this.username = username;
        this.postTime = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.relativeTime = "";
    }

    public void likePost() {
        likes++;
    }

    public void dislikePost() {
        dislikes++;
    }

    public String getRelativeTime() {
        long diffInMillis = new Date().getTime() - this.postTime.getTime();
        long diffInSeconds = diffInMillis / 1000;
        long diffInMinutes = diffInSeconds / 60;
        long diffInHours = diffInMinutes / 60;

        if (diffInMinutes < 60) return diffInMinutes + "m ago";
        else if (diffInHours < 24) return diffInHours + "hr ago";
        else return (diffInMillis / (1000 * 60 * 60 * 24)) + " days ago";
    }
}
