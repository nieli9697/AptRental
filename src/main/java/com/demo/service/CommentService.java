package com.demo.service;

import com.demo.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByProjectId(Long projectId);

    Comment saveComment(Comment comment);
}
