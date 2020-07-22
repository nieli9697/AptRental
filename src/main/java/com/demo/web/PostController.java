package com.demo.web;

import com.demo.po.Project;
import com.demo.po.User;
import com.demo.service.CategoryService;
import com.demo.service.ProjectService;
import com.demo.service.TagService;
import com.demo.vo.ProjectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PostController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/projects")
    public String post(Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("project", new Project());

        return "posting";
    }


    @PostMapping("/projects")
    public String post(Project project, RedirectAttributes attributes, HttpSession session) {
        if (project.getId() != null) {
            Project tmp = projectService.getProject(project.getId());
            project.setViews(tmp.getViews());
            project.setCreateTime(tmp.getCreateTime());
        }
        project.setUser((User) session.getAttribute("user"));
        project.setCategory(categoryService.getCategory(project.getCategory().getId()));
        project.setTags(tagService.listTag(project.getTagIds()));

        Project p = projectService.saveProject(project);
        if (p == null) {
            attributes.addFlashAttribute("message", "Operation failed!");
        }
        else {
            attributes.addFlashAttribute("message", "Successful operation!");
        }
        return "index";
    }

}
