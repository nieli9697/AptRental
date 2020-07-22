package com.demo.web.admin;

import com.demo.po.Tag;
import com.demo.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"},
            direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("tags/add")
    public String add(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags_adding";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,
                       RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "Cannot add the same tag twice!");
        }

        if (result.hasErrors()) {
            return "admin/tags_adding";
        }

        Tag t1 = tagService.saveTag(tag);
        if (t1 == null) {
            attributes.addFlashAttribute("message", "Fail to add the new tag!");
        }
        else {
            attributes.addFlashAttribute("message", "Successfully added the new tag!");
        }

        return "redirect:/admin/tags";
    }

    @GetMapping("tags/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags_adding";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "Cannot add the same tag twice!");
        }

        if (result.hasErrors()) {
            return "admin/tags_adding";
        }

        Tag t1 = tagService.updateTag(id, tag);
        if (t1 == null) {
            attributes.addFlashAttribute("message", "Fail to update the tag!");
        }
        else {
            attributes.addFlashAttribute("message", "Successfully updated the tag!");
        }

        return "redirect:/admin/tags";
    }

    @GetMapping({"/tags/{id}/delete"})
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Successfully deleted!");
        return "redirect:/admin/tags";
    }
}
