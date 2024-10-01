package com.binhcodev.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("SELECT p FROM Blog p WHERE :term IS NULL OR p.title LIKE %:term% OR p.content LIKE %:term% OR p.category LIKE %:term%")
    List<Blog> search(@Param("term") String term);
}
