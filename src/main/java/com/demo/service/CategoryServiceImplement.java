package com.demo.service;

import com.demo.NotFoundException;
import com.demo.dao.CategoryRepository;
import com.demo.po.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category getCategory(Long id) {
        return categoryRepository.getOne(id);
    }

    @Transactional
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Category> listCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> listCategoryTop() {
        int size = (int)categoryRepository.count();
        Sort sort = Sort.by(Sort.Direction.DESC,"projects.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return categoryRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, Category category) {
        Category c = categoryRepository.getOne(id);
        if (c == null) {
            throw new NotFoundException("Category not found!");
        }
        BeanUtils.copyProperties(category, c);
        return categoryRepository.save(c);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
