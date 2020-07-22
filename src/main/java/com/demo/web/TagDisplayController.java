package com.demo.web;

import com.demo.po.Tag;
import com.demo.service.ProjectService;
import com.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagDisplayController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 7, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                       Pageable pageable, @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop();
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", projectService.listProject(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tag";
    }
}
