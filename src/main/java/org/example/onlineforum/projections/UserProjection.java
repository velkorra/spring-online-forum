package org.example.onlineforum.projections;

import java.time.LocalDateTime;

public interface UserProjection {
    String getId();

    String getEmail();

    String getUsername();

    String getDisplayName();

    LocalDateTime getRegisteredOn();

    String getRole();

    long getThreadCount();

    long getCommentCount();

    long getReactionCount();
}
