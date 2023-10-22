package com.soulrebel.blog.repository;

import com.soulrebel.blog.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Collection<Comment> findByPostId(long postId);
}
