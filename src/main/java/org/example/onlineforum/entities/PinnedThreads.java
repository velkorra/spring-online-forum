package org.example.onlineforum.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PinnedThreads extends BaseEntity{
    private ForumThread thread;
    private User user;
    private LocalDateTime pinnedOn;

    protected PinnedThreads() {
    }

    public PinnedThreads(ForumThread thread, User user) {
        this.thread = thread;
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn( name = "thread_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_thread_pinned"))
    public ForumThread getThread() {
        return thread;
    }

    public void setThread(ForumThread thread) {
        this.thread = thread;
    }

    @ManyToOne(optional = false)
    @JoinColumn( name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_pinned"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "pinned_on", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime getPinnedOn() {
        return pinnedOn;
    }

    public void setPinnedOn(LocalDateTime pinnedOn) {
        this.pinnedOn = pinnedOn;
    }
}
