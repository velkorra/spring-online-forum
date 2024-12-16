package org.example.onlineforum.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "forum_threads")
public class ForumThread extends BaseEntity {
    private User author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int viewsCount;
    private Category category;
    private Boolean isDeleted;
    private Set<Reaction> reactions;
    private Set<ThreadComment> comments;
    private Set<PinnedThreads> userPins;

    protected ForumThread() {
    }

    public ForumThread(User author, String title, String content, Category category) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_thread"))
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "views_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    @Column(name = "is_deleted", nullable = false, insertable = false)
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_category_thread"))
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "thread")
    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    @OneToMany(mappedBy = "thread")
    public Set<ThreadComment> getComments() {
        return comments;
    }

    public void setComments(Set<ThreadComment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "thread")
    public Set<PinnedThreads> getUserPins() {
        return userPins;
    }

    public void setUserPins(Set<PinnedThreads> userPins) {
        this.userPins = userPins;
    }


}
