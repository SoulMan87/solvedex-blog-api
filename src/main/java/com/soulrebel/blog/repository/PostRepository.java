package com.soulrebel.blog.repository;

import com.soulrebel.blog.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Collection<Post> findAllByCategoryId(Long categoryId);
}
