package org.example.onlineforum.dto.mappers;

import org.example.onlineforum.projections.ThreadCommentProjection;
import org.forum.forumcontracts.viewmodels.ThreadCommentViewModel;

public interface CommentMapper {
    ThreadCommentViewModel toViewModel(ThreadCommentProjection projection);

}
