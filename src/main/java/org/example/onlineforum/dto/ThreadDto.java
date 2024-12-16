package org.example.onlineforum.dto;

import org.example.onlineforum.entities.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ThreadDto {
    public String id;
    public String authorId;
    public String title;
    public String content;
    public LocalDateTime createdAt;
    public int viewsCount;
    public String category;
    public int likeCount;
    public int dislikeCount;
    public List<ThreadCommentDto> comments;

    public ThreadDto(ForumThread thread) {
        this.authorId = thread.getAuthor().getId();
        this.title = thread.getTitle();
        this.content = thread.getContent();
        this.createdAt = thread.getCreatedAt();
        this.viewsCount = thread.getViewsCount();
        this.category = thread.getCategory().getName();
        this.likeCount = thread.getReactions().stream().filter(Reaction::isLike).toList().size();
        this.dislikeCount = thread.getReactions().stream().filter(r -> !r.isLike()).toList().size();
        this.comments = thread.getComments().stream().map(ThreadCommentDto::new).toList();
    }
}
