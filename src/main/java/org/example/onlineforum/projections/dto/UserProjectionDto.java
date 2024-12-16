package org.example.onlineforum.projections.dto;

import java.time.LocalDateTime;

public class UserProjectionDto {

    private String id;
    private String email;
    private String username;
    private String displayName;
    private LocalDateTime registeredOn;
    private String role;
    private long threadCount;
    private long commentCount;
    private long reactionCount;

    public UserProjectionDto(){}

    public UserProjectionDto(org.example.onlineforum.projections.UserProjection projection) {
        this.id = projection.getId();
        this.email = projection.getEmail();
        this.username = projection.getUsername();
        this.displayName = projection.getDisplayName();
        this.registeredOn = projection.getRegisteredOn();
        this.role = projection.getRole();
        this.threadCount = projection.getThreadCount();
        this.commentCount = projection.getCommentCount();
        this.reactionCount = projection.getReactionCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(long threadCount) {
        this.threadCount = threadCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getReactionCount() {
        return reactionCount;
    }

    public void setReactionCount(long reactionCount) {
        this.reactionCount = reactionCount;
    }
}