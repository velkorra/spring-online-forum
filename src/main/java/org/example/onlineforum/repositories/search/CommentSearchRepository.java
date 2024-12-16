package org.example.onlineforum.repositories.search;

import org.example.onlineforum.entities.ThreadComment;
import org.example.onlineforum.projections.ThreadCommentProjection;
import org.example.onlineforum.projections.ThreadProjection;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentSearchRepository extends Repository<ThreadComment, String> {
    @Query("select c.id as id, " +
            "c.author.username as authorUsername, " +
            "c.author.displayName as authorDisplayName, " +
            "c.thread.id as threadId, " +
            "c.parentComment.id as parentCommentId, " +
            "c.content as content, " +
            "c.createdAt as createdAt, " +
            "count(distinct r.id) as reactionsCount, " +
            "count(distinct reply.id) as repliesCount, " +
            "(select count(*) from Reaction r where r.comment = c and r.like = true) as likesCount, " +
            "(select count(*) from Reaction r where r.comment = c and r.like = false) as dislikesCount " +
            "from ThreadComment c " +
            "left join Reaction r on r.comment = c " +
            "left join ThreadComment reply on reply.parentComment = c " +
            "where (:#{#filter.id()} is null or c.id = :#{#filter.id()}) " +
            "and (:#{#filter.authorUsername()} is null or c.author.username = :#{#filter.authorUsername()}) " +
            "and (:#{#filter.content()} is null or c.content like %:#{#filter.content()}%) " +
            "and (:#{#filter.threadId()} is null or c.thread.id = :#{#filter.threadId()}) " +
            "and (:#{#filter.parentCommentId()} is null or (c.parentComment is not null and c.parentComment.id = :#{#filter.parentCommentId()})) " +
            "and (:#{#filter.createdAfter()} is null or c.createdAt >= :#{#filter.createdAfter()}) " +
            "and (:#{#filter.createdBefore()} is null or c.createdAt <= :#{#filter.createdBefore()}) " +
            "and ((:#{#filter.includeDeleted()} is null and c.deleted = false ) or c.deleted = :#{#filter.includeDeleted()} )" +
            "group by c.id, c.author.username, c.author.displayName, c.thread.id, c.content, c.createdAt")
    Page<ThreadCommentProjection> searchThreadComments(
            @Param("filter") ThreadCommentFilter filter,
            Pageable pageable);

    default Optional<ThreadCommentProjection> getProjectionById(String id) {
        return searchThreadComments(
                ThreadCommentFilter.blank().withTreadId(id),
                PageRequest.of(0, 1))
                .stream().findFirst();
    }
}
