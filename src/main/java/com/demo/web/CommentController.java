package com.demo.web;

import com.demo.po.Comment;
import com.demo.service.CommentService;
import com.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/comments/{projectId}")
    public String comments(@PathVariable Long projectId, Model model) {
        model.addAttribute("comments", commentService.listCommentByProjectId(projectId));
        return "project :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        Long projectId = comment.getProject().getId();
        comment.setProject(projectService.getProject(projectId));
        commentService.saveComment(comment);
        return "redirect:/comments/" + projectId;
    }
}
