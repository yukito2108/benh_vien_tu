package com.tuannq.store.repository;


import com.tuannq.store.entity.Post;
import com.tuannq.store.model.request.PostSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM post WHERE status = ?1 AND id != ?2 ORDER BY published_at, created_at DESC LIMIT ?3")
    List<Post> getLatestPostsNotId(int publicStatus, long id, int limit);

    @Query(nativeQuery = true, value = "SELECT * FROM post WHERE status = ?1 ORDER BY published_at, created_at DESC LIMIT ?2")
    List<Post> getLatestPosts(int publicStatus, int limit);

    @Query(nativeQuery = true, value = "SELECT count(id)\n" +
            "FROM post\n" +
            "WHERE title LIKE CONCAT('%',:title,'%') AND status LIKE CONCAT('%',:status,'%')\n")
    int countPostFilter(@Param("title") String title, @Param("status") String status);

    @Query("select x1 from Post x1 " +
            " where (:#{#form.status} is null or :#{#form.status} = '' or x1.status = :#{#form.status}) " +
            "   and (:#{#form.title} is null or :#{#form.title} = '' or lower(x1.title) like lower(concat('%', lower(:#{#form.title}), '%' ) )) ")
    Page<Post> searchByAdmin(PostSearchForm form, Pageable pageable);

    @Query("select x1 from Post x1 " +
            " where x1.status = '1' and x1.isDeleted = false ")
    Page<Post> findByPublic(Pageable pageable);
}
