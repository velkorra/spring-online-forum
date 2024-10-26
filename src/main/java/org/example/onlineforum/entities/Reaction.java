package org.example.onlineforum.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction extends BaseEntity{
    private User user;
    private ThreadComment comment;
    private ForumThread thread;
    private boolean isLike;

    protected Reaction() {
    }

    public Reaction(User user, ThreadComment comment, boolean isLike) {
        this.user = user;
        this.comment = comment;
        this.isLike = isLike;
    }

    public Reaction(User user, ForumThread thread, boolean isLike) {
        this.user = user;
        this.thread = thread;
        this.isLike = isLike;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_reaction"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    @PrePersist
    @PreUpdate
    private void validateCommentOrThread() {
        if ((comment == null) == (thread == null)) {
            throw new IllegalStateException("Either comment or thread must be set, but not both");
        }
    }
}
