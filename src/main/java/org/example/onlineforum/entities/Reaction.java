package org.example.onlineforum.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reactions")
public class Reaction extends BaseEntity{
    private User author;
    private ThreadComment comment;
    private ForumThread thread;
    private Boolean like;
    private LocalDateTime createdOn;

    protected Reaction() {
    }

    public Reaction(User author, ThreadComment comment, Boolean isLike) {
        this.author = author;
        this.comment = comment;
        this.like = isLike;
    }

    public Reaction(User author, ForumThread thread, Boolean isLike) {
        this.author = author;
        this.thread = thread;
        this.like = isLike;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_reaction"))
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_comment_reaction"))
    public ThreadComment getComment() {
        return comment;
    }

    public void setComment(ThreadComment comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "thread_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_thread_reaction"))
    public ForumThread getThread() {
        return thread;
    }

    public void setThread(ForumThread thread) {
        this.thread = thread;
    }

    @Column(name = "is_like", nullable = false)
    public Boolean isLike() {
        return like;
    }

    private void setLike(boolean like) {
        this.like = like;
    }

    @Column(name = "created_on", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    private void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @PrePersist
    @PreUpdate
    private void validateCommentOrThread() {
        if ((comment == null) == (thread == null)) {
            throw new IllegalStateException("Either comment or thread must be set, but not both");
        }
    }
}
