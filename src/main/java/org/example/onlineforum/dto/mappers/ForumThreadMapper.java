package org.example.onlineforum.dto.mappers;

import org.example.onlineforum.dto.NewThreadDto;
import org.example.onlineforum.dto.ThreadUpdateDto;
import org.example.onlineforum.projections.ThreadProjection;
import org.forum.forumcontracts.input.NewThreadForm;
import org.forum.forumcontracts.input.ThreadEditForm;
import org.forum.forumcontracts.input.ThreadUpdateForm;
import org.forum.forumcontracts.viewmodels.ForumThreadViewModel;
import org.forum.forumcontracts.viewmodels.ForumThreadWithCommentsViewModel;
import org.forum.forumcontracts.viewmodels.ThreadCommentViewModel;

import java.util.List;

public interface ForumThreadMapper {
    ForumThreadViewModel toViewModel(ThreadProjection projection);

    ThreadUpdateForm toUpdateForm(ThreadProjection projection);

    ThreadUpdateDto toDto(ThreadUpdateForm form);

    ThreadUpdateDto toDto(ThreadEditForm form);

    ForumThreadWithCommentsViewModel toViewModel(ThreadProjection projection, List<ThreadCommentViewModel> comments);

    NewThreadDto toDto(NewThreadForm form);
}
