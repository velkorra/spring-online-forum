package org.example.onlineforum.dto.mappers.impl;

import org.example.onlineforum.dto.NewThreadDto;
import org.example.onlineforum.dto.ThreadUpdateDto;
import org.example.onlineforum.dto.mappers.ForumThreadMapper;
import org.example.onlineforum.projections.ThreadProjection;
import org.example.onlineforum.utils.DateConverter;
import org.forum.forumcontracts.input.NewThreadForm;
import org.forum.forumcontracts.input.ThreadEditForm;
import org.forum.forumcontracts.input.ThreadUpdateForm;
import org.forum.forumcontracts.viewmodels.ForumThreadViewModel;
import org.forum.forumcontracts.viewmodels.ForumThreadWithCommentsViewModel;
import org.forum.forumcontracts.viewmodels.ThreadCommentViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ForumThreadMapperImpl implements ForumThreadMapper {

    public ForumThreadViewModel toViewModel(ThreadProjection projection) {
        return new ForumThreadViewModel(
                projection.getId(),
                projection.getTitle(),
                projection.getContent(),
                DateConverter.dateFromLocalDateTIme(projection.getCreatedAt()),
                projection.getViewsCount(),
                projection.getCategory(),
                projection.getAuthorUsername(),
                projection.getAuthorDisplayName(),
                projection.getCommentsCount(),
                projection.getReactionsCount(),
                projection.getLikesCount(),
                projection.getDislikesCount()
        );
    }

    public ThreadUpdateForm toUpdateForm(ThreadProjection projection) {
        return new ThreadUpdateForm(
                projection.getId(),
                projection.getTitle(),
                projection.getContent(),
                projection.getCategory()
        );
    }

    public ThreadUpdateDto toDto(ThreadUpdateForm form) {
        return new ThreadUpdateDto(
                form.id(),
                form.title(),
                form.content(),
                form.category()
        );
    }

    @Override
    public ThreadUpdateDto toDto(ThreadEditForm form) {
        return new ThreadUpdateDto(form.id(), form.title(), form.content(), null);
    }

    @Override
    public ForumThreadWithCommentsViewModel toViewModel(ThreadProjection projection, List<ThreadCommentViewModel> comments) {
        return new ForumThreadWithCommentsViewModel(
                projection.getId(),
                projection.getTitle(),
                projection.getContent(),
                DateConverter.dateFromLocalDateTIme(projection.getCreatedAt()),
                projection.getViewsCount(),
                projection.getCategory(),
                projection.getAuthorUsername(),
                projection.getAuthorDisplayName(),
                projection.getCommentsCount(),
                projection.getReactionsCount(),
                projection.getLikesCount(),
                projection.getDislikesCount(),
                comments
        );
    }

    @Override
    public NewThreadDto toDto(NewThreadForm form) {
        return new NewThreadDto(
                form.title(),
                form.content(),
                form.category()
        );
    }
}
