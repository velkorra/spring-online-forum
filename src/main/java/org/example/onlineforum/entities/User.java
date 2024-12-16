package org.example.onlineforum.entities;

import jakarta.persistence.*;
import org.example.onlineforum.constants.UserRoles;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String displayName;
    private UserRoles role;
    private LocalDateTime createdOn;
    private Boolean isDeleted;
    private Set<ForumThread> forumThreads;
    private Set<ThreadComment> threadComments;
    private Set<Reaction> reactions;
    private Set<PinnedThreads> pinnedThreads;

    protected User() {
    }

    public User(String username, String password, String email, String displayName, UserRoles role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.role = role;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @Column(name = "created_on", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Column(name = "is_deleted", nullable = false, insertable = false)
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @OneToMany(mappedBy = "author")
    public Set<ForumThread> getForumThreads() {
        return forumThreads;
    }

    public void setForumThreads(Set<ForumThread> forumThreads) {
        this.forumThreads = forumThreads;
    }

    @OneToMany(mappedBy = "author")
    public Set<ThreadComment> getThreadComments() {
        return threadComments;
    }

    public void setThreadComments(Set<ThreadComment> threadComments) {
        this.threadComments = threadComments;
    }

    @OneToMany(mappedBy = "author")
    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    @OneToMany(mappedBy = "user")
    public Set<PinnedThreads> getPinnedThreads() {
        return pinnedThreads;
    }

    public void setPinnedThreads(Set<PinnedThreads> pinnedThreads) {
        this.pinnedThreads = pinnedThreads;
    }
}
