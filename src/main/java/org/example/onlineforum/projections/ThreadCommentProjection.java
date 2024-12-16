package org.example.onlineforum.projections;


import java.io.Serializable;
import java.time.LocalDateTime;

public interface ThreadCommentProjection extends Serializable {
    String getId();

    String getAuthorUsername();

    String getAuthorDisplayName();

    String getThreadId();

    String getParentCommentId();

    String getContent();

    LocalDateTime getCreatedAt();

    Integer getReactionsCount();

    Integer getRepliesCount();

    Integer getLikesCount();

    Integer getDislikesCount();
}
