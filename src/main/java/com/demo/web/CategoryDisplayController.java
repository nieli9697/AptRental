package com.demo.web;

import com.demo.po.Category;
import com.demo.service.CategoryService;
import com.demo.service.ProjectService;
import com.demo.vo.ProjectQuery;
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
public class CategoryDisplayController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/categories/{id}")
    public String categories(@PageableDefault(size = 7, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                             Pageable pageable, @PathVariable Long id, Model model) {
        List<Category> categories = categoryService.listCategoryTop();
        if (id == -1) {
            id = categories.get(0).getId();
        }
        ProjectQuery projectQuery = new ProjectQuery();
        projectQuery.setCategoryId(id);
        model.addAttribute("categories", categories);
        model.addAttribute("page", projectService.listProject(pageable, projectQuery));
        model.addAttribute("activeCategoryId", id);
        return "category";
    }
}
