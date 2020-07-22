package com.demo.service;

import com.demo.NotFoundException;
import com.demo.dao.ProjectRepository;
import com.demo.po.Category;
import com.demo.po.Project;
import com.demo.vo.ProjectQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImplementation implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project getProject(Long id) {
        return projectRepository.getOne(id);
    }

    @Override
    public Page<Project> listProject(Pageable pageable, ProjectQuery project) {
        return projectRepository.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(project.getTitle()) && project.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+project.getTitle()+"%"));
                }
                if (project.getCategoryId() != null) {
                    predicates.add(cb.equal(root.<Category>get("category").get("id"), project.getCategoryId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Project> listProject(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> listProject(Long tagId, Pageable pageable) {
        return projectRepository.findAll(new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Project> listProject(String query, Pageable pageable) {
        return projectRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public Project saveProject(Project project) {
        // Initialize
        if (project.getId() == null) {
            project.setCreateTime(new Date());
            project.setUpdateTime(new Date());
            project.setViews(0);
        }
        else {
            project.setUpdateTime(new Date());
        }
        return projectRepository.save(project);
    }

    @Transactional
    @Override
    public Project updateProject(Long id, Project project) {
        Project p = projectRepository.getOne(id);
        if (p == null) {
            throw new NotFoundException("Project not found!");
        }
        BeanUtils.copyProperties(project, p);
        p.setUpdateTime(new Date());
        return projectRepository.save(p);
    }

    @Transactional
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
