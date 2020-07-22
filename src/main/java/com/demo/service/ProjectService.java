package com.demo.service;

import com.demo.po.Project;
import com.demo.vo.ProjectQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Project getProject(Long id);

    Page<Project> listProject(Pageable pageable, ProjectQuery project);

    Page<Project> listProject(Pageable pageable);

    Page<Project> listProject(Long tagId, Pageable pageable);

    Page<Project> listProject(String query, Pageable pageable);

    Project saveProject(Project project);

    Project updateProject(Long id, Project project);

    void deleteProject(Long id);
}
