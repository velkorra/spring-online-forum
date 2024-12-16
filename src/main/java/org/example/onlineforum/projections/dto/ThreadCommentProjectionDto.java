package org.example.onlineforum.projections.dto;


import java.time.LocalDateTime;

public class ThreadCommentProjectionDto {

    private String id;
    private String authorUsername;
    private String authorDisplayName;
    private String threadId;
    private String parentCommentId;
    private String content;
    private LocalDateTime createdAt;
    private Integer reactionsCount;
    private Integer repliesCount;
    private Integer likesCount;
    private Integer dislikesCount;

    public ThreadCommentProjectionDto(){}

    public ThreadCommentProjectionDto(org.example.onlineforum.projections.ThreadCommentProjection projection) {
        this.id = projection.getId();
        this.authorUsername = projection.getAuthorUsername();
        this.authorDisplayName = projection.getAuthorDisplayName();
        this.threadId = projection.getThreadId();
        this.parentCommentId = projection.getParentCommentId();
        this.content = projection.getContent();
        this.createdAt = projection.getCreatedAt();
        this.reactionsCount = projection.getReactionsCount();
        this.repliesCount = projection.getRepliesCount();
        this.likesCount = projection.getLikesCount();
        this.dislikesCount = projection.getDislikesCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getReactionsCount() {
        return reactionsCount;
    }

    public void setReactionsCount(Integer reactionsCount) {
        this.reactionsCount = reactionsCount;
    }

    public Integer getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Integer dislikesCount) {
        this.dislikesCount = dislikesCount;
    }
}