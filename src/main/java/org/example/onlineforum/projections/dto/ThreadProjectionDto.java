package org.example.onlineforum.projections.dto;


import java.time.LocalDateTime;

public class ThreadProjectionDto {

    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int viewsCount;
    private String category;
    private String authorUsername;
    private String authorDisplayName;
    private int commentsCount;
    private int reactionsCount;
    private int likesCount;
    private int dislikesCount;

    public ThreadProjectionDto(){}

    public ThreadProjectionDto(org.example.onlineforum.projections.ThreadProjection projection) {
        this.id = projection.getId();
        this.title = projection.getTitle();
        this.content = projection.getContent();
        this.createdAt = projection.getCreatedAt();
        this.viewsCount = projection.getViewsCount();
        this.category = projection.getCategory();
        this.authorUsername = projection.getAuthorUsername();
        this.authorDisplayName = projection.getAuthorDisplayName();
        this.commentsCount = projection.getCommentsCount();
        this.reactionsCount = projection.getReactionsCount();
        this.likesCount = projection.getLikesCount();
        this.dislikesCount = projection.getDislikesCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getReactionsCount() {
        return reactionsCount;
    }

    public void setReactionsCount(int reactionsCount) {
        this.reactionsCount = reactionsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }
}