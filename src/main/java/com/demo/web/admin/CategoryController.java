package com.demo.web.admin;

import com.demo.po.Category;
import com.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(@PageableDefault(size = 5, sort = {"id"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        model.addAttribute("page", categoryService.listCategory(pageable));
        return "admin/categories";
    }

    @GetMapping("categories/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories_adding";
    }

    @GetMapping("categories/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/categories_adding";
    }

    @PostMapping("/categories")
    public String post(@Valid Category category, BindingResult result,
                       RedirectAttributes attributes) {
        Category c1 = categoryService.getCategoryByName(category.getName());
        if (c1 != null) {
            result.rejectValue("name", "nameError", "Cannot add the same category twice!");
        }

        if (result.hasErrors()) {
            return "admin/categories_adding";
        }

        Category c = categoryService.saveCategory(category);
        if (c == null) {
            attributes.addFlashAttribute("message", "Fail to add the new category!");
        }
        else {
            attributes.addFlashAttribute("message", "Successfully added the new category!");
        }

        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}")
    public String editPost(@Valid Category category, BindingResult result,
                       @PathVariable Long id, RedirectAttributes attributes) {
        Category c1 = categoryService.getCategoryByName(category.getName());
        if (c1 != null) {
            result.rejectValue("name", "nameError", "Cannot add the same category twice!");
        }

        if (result.hasErrors()) {
            return "admin/categories_adding";
        }

        Category c = categoryService.updateCategory(id, category);
        if (c == null) {
            attributes.addFlashAttribute("message", "Fail to update the category!");
        }
        else {
            attributes.addFlashAttribute("message", "Successfully updated the category!");
        }

        return "redirect:/admin/categories";
    }

    @GetMapping({"/categories/{id}/delete"})
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        categoryService.deleteCategory(id);
        attributes.addFlashAttribute("message", "Successfully deleted!");
        return "redirect:/admin/categories";
    }
}
