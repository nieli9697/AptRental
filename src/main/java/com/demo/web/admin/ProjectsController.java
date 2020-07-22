package com.demo.web.admin;

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
@RequestMapping("/admin")
public class ProjectsController {

    private static final String ADD = "admin/projects_adding";
    private static final String LIST = "admin/projects";
    private static final String REDIRECT_LIST = "redirect:/admin/projects";

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/projects")
    public String projects(@PageableDefault(size = 2, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) { //, ProjectQuery project
        model.addAttribute("categories", categoryService.listCategory());
        //model.addAttribute("page", projectService.listProject(pageable, project));
        model.addAttribute("page", projectService.listProject(pageable));
        return LIST;
    }

    @PostMapping("/projects/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, ProjectQuery project, Model model) {
        model.addAttribute("page", projectService.listProject(pageable, project));
        return "admin/projects :: projectList";
    }

    @GetMapping("/projects/add")
    public String add(Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("project", new Project());
        return ADD;
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

//        Project p;
//        if (project.getId() == null) {
//            p = projectService.saveProject(project);
//        } else {
//            p = projectService.updateProject(project.getId(), project);
//        }

        Project p = projectService.saveProject(project);
        if (p == null) {
            attributes.addFlashAttribute("message", "Operation failed!");
        }
        else {
            attributes.addFlashAttribute("message", "Successful operation!");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/projects/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());
        Project project = projectService.getProject(id);
        project.init();
        model.addAttribute("project", project);
        return ADD;
    }

    @GetMapping("/projects/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        projectService.deleteProject(id);
        attributes.addFlashAttribute("message", "Successfully deleted!");
        return REDIRECT_LIST;
    }

}
