package org.example.onlineforum.repositories.search;

import org.example.onlineforum.entities.ForumThread;
import org.example.onlineforum.projections.ThreadProjection;
import org.forum.forumcontracts.filters.ForumThreadFilter;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThreadSearchRepository extends Repository<ForumThread, String> {

    @Query("select t.id as id, " +
            "t.title as title, " +
            "t.content as content, " +
            "t.createdAt as createdAt, " +
            "t.viewsCount as viewsCount, " +
            "t.category.name as category, " +
            "t.author.username as authorUsername, " +
            "t.author.displayName as authorDisplayName, " +
            "count(distinct c.id) as commentsCount, " +
            "count(distinct r.id) as reactionsCount, " +
            "(select count(*) from Reaction r where r.thread = t and r.like = true) as likesCount, " +
            "(select count(*) from Reaction r where r.thread = t and r.like = false) as dislikesCount " +
            "from ForumThread t " +
            "left join ThreadComment c on c.thread = t " +
            "left join Reaction r on r.thread = t " +
            "where (:#{#filter.id()} is null or t.id = :#{#filter.id()}) " +
            "and (:#{#filter.authorUsername()} is null or t.author.username = :#{#filter.authorUsername()}) " +
            "and (:#{#filter.title()} is null or t.title like %:#{#filter.title()}%) " +
            "and (:#{#filter.content()} is null or t.content like %:#{#filter.content()}%) " +
            "and (:#{#filter.category()} is null or t.category.name = :#{#filter.category()}) " +
            "and (:#{#filter.authorDisplayName()} is null or t.author.displayName = :#{#filter.authorDisplayName()}) " +
            "and (:#{#filter.createdAfter()} is null or t.createdAt >= :#{#filter.createdAfter()}) " +
            "and (:#{#filter.createdBefore()} is null or t.createdAt <= :#{#filter.createdBefore()}) " +
            "and ((:#{#filter.includeDeleted()} is null and t.deleted = false ) or t.deleted = :#{#filter.includeDeleted()} )" +
            "group by t.id, t.title, t.content, t.createdAt, t.viewsCount, t.category.name, t.author.username, t.author.displayName")
    Page<ThreadProjection> searchForumThreads(
            @Param("filter") ForumThreadFilter filter,
            Pageable pageable);

    default Optional<ThreadProjection> getProjectionById(String id){
        return searchForumThreads(
                ForumThreadFilter.blank().withId(id),
                PageRequest.of(0, 1)
        )
                .stream().findFirst();
    }
}
