package com.demo.web;

import com.demo.service.CategoryService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/")
    public String index(@PageableDefault(size = 7, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {
        model.addAttribute("page", projectService.listProject(pageable));
        model.addAttribute("categories", categoryService.listCategoryTop());
        model.addAttribute("tags", tagService.listTagTop());
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 7, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", projectService.listProject("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/project/{id}")
    public String project(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProject(id));
        return "project";
    }
}
