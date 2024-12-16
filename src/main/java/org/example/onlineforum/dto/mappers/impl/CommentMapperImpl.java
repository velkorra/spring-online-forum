package org.example.onlineforum.dto.mappers.impl;

import org.example.onlineforum.dto.mappers.CommentMapper;
import org.example.onlineforum.projections.dto.ThreadCommentProjectionDto;
import org.example.onlineforum.utils.DateConverter;
import org.forum.forumcontracts.viewmodels.ThreadCommentViewModel;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public ThreadCommentViewModel toViewModel(ThreadCommentProjectionDto projection) {
        return new ThreadCommentViewModel(
                projection.getId(),
                projection.getAuthorUsername(),
                projection.getAuthorDisplayName(),
                projection.getContent(),
                DateConverter.dateFromLocalDateTIme(projection.getCreatedAt()),
                projection.getReactionsCount(),
                projection.getLikesCount(),
                projection.getDislikesCount(),
                projection.getRepliesCount()
        );
    }
}
