package org.example.onlineforum.repositories.search;

import org.example.onlineforum.entities.User;
import org.example.onlineforum.projections.UserProjection;
import org.forum.forumcontracts.filters.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UserSearchRepository extends Repository<User, String> {
    @Query("select u.id as id, " +
            "u.username as username, " +
            "u.email as email, " +
            "u.displayName as displayName, " +
            "u.createdOn as registeredOn, " +
            "u.role as role, " +
            "count(distinct t.id) as threadCount, " +
            "count(distinct c.id) as commentCount, " +
            "count(distinct r.id) as reactionCount " +
            "from User u " +
            "left join ForumThread t on t.author = u " +
            "left join ThreadComment c on c.author = u " +
            "left join Reaction r on r.author = u " +
            "where (:#{#filter.username()} is null or u.username = :#{#filter.username()}) " +
            "and (:#{#filter.role()} is null or u.role = :#{#filter.role()}) " +
            "and ((:#{#filter.includeDeleted()} is null and u.deleted = false ) or u.deleted = :#{#filter.includeDeleted()} )" +
            "group by u")
    Page<UserProjection> searchUsers(
            @Param("filter") UserFilter filter,
            Pageable pageable);
}
