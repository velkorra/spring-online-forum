package org.example.onlineforum.projections;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface ThreadProjection extends Serializable {
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
