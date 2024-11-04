package org.example.onlineforum.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "thread_comments")
public class ThreadComment extends BaseEntity{
    private User author;
    private String content;
    private ForumThread thread;
    private LocalDateTime createdAt;
    private Set<Reaction> reactions;
    private ThreadComment parentComment;
    private Set<ThreadComment> replies;

    protected ThreadComment() {
    }

    public ThreadComment(User author, String content, ForumThread thread) {
        this.author = author;
        this.content = content;
        this.thread = thread;
    }

    @ManyToOne( optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_comment"))
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne( optional = false)
    @JoinColumn(name = "thread_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_thread_comment"))
    public ForumThread getThread() {
        return thread;
    }

    public void setThread(ForumThread thread) {
        this.thread = thread;
    }

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @OneToMany(mappedBy = "comment")
    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    @ManyToOne
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_parent_comment"))
    public ThreadComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(ThreadComment parentComment) {
        this.parentComment = parentComment;
    }

    @OneToMany(mappedBy = "parentComment")
    public Set<ThreadComment> getReplies() {
        return replies;
    }

    public void setReplies(Set<ThreadComment> replies) {
        this.replies = replies;
    }
}
