package org.example.onlineforum.projections;

import java.time.LocalDateTime;

public interface ThreadProjection {
    String getId();

    String getTitle();

    String getContent();

    LocalDateTime getCreatedAt();

    int getViewsCount();

    String getCategory();

    String getAuthorUsername();

    String getAuthorDisplayName();

    int getCommentsCount();

    int getReactionsCount();

    int getLikesCount();

    int getDislikesCount();
}
