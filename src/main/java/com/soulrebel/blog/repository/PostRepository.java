package com.soulrebel.blog.repository;

import com.soulrebel.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Collection<Post> findAllByCategoryId(Long categoryId);

}
