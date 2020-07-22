package com.demo.dao;

import com.demo.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByProjectIdAndParentCommentNull(Long projectId, Sort sort);
}
