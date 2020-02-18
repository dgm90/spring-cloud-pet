package com.derzhavets.service;

import com.derzhavets.controller.model.Overtime;
import com.derzhavets.controller.model.Project;
import com.derzhavets.exception.ResourceNotFoundException;
import com.derzhavets.repository.ProjectRepository;
import com.derzhavets.repository.entity.OvertimeEntity;
import com.derzhavets.repository.entity.ProjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.EMPTY_LIST;

@Component
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OvertimeService overtimeService;

    @Autowired
    private ModelMapper modelMapper;

    public Project createProject(Project project) {
        ProjectEntity projectEntity = modelMapper.map(project, ProjectEntity.class);
        projectEntity = projectRepository.save(projectEntity);
        return modelMapper.map(projectEntity, Project.class);
    }

    public List<Project> findAllProjects() {
        Iterable<ProjectEntity> projectEntities = projectRepository.findAll();
        List<Project> projects = new ArrayList<>();
        projectEntities.iterator()
                .forEachRemaining(projectEntity -> {
                    //we don't need overtimes when returning all projects
                    projectEntity.setOvertimes(EMPTY_LIST);
                    projects.add(modelMapper.map(projectEntity, Project.class));

                });
        return projects;
    }

    public Project findProjectById(String id, boolean withOvertimes) {
        Optional<ProjectEntity> project = projectRepository.findById(Integer.valueOf(id));
        if (project.isPresent()) {
            if (!withOvertimes) {
                project.get().setOvertimes(EMPTY_LIST);
            }
            return modelMapper.map(project.get(), Project.class);
        } else {
            throw new ResourceNotFoundException(String.format("Project with id [%s] is not found",  id));
        }
    }

    public List<Overtime> findProjectOvertimesForDate(Integer projectId, Integer year, Integer month) {
        if (!isProjectPresent(projectId)) {
            throw new ResourceNotFoundException(String.format("Project with id [%s] is not found", projectId));
        }

        int lastDay = YearMonth.of(year, month).lengthOfMonth();
        Date startDate = Date.valueOf(LocalDate.of(year, month, 1));
        Date endDate = Date.valueOf(LocalDate.of(year, month, lastDay));

        List<OvertimeEntity>
                overtimeEntities = overtimeService.findOvertimesForProjectBetweenDates(projectId, startDate ,endDate);
        List<Overtime> overtimes = new ArrayList<>();
        overtimeEntities.iterator()
                .forEachRemaining(overtimeEntity -> {
                    overtimes.add(modelMapper.map(overtimeEntity, Overtime.class));
                });
        return overtimes;
    }

    public Project updateProject(String id, Project projectToUpdate) {
        Integer integerId = Integer.valueOf(id);
        //todo:add some validation
        Optional<ProjectEntity> currentProject = projectRepository.findById(integerId);
        if (currentProject.isPresent()) {
            projectToUpdate.setId(integerId);
            ProjectEntity projectEntity = projectRepository.save(modelMapper.map(projectToUpdate, ProjectEntity.class));
            return modelMapper.map(projectEntity, Project.class);
        } else {
            throw new ResourceNotFoundException(String.format("Project with id [%s] is not found",  id));
        }
    }

    public String removeProjectById(String id) {
        Optional<ProjectEntity> projectToRemove = projectRepository.findById(Integer.valueOf(id));
        if (projectToRemove.isPresent()) {
            projectRepository.deleteById(projectToRemove.get().getId());
            return String.format("Project with id [%s] successfully removed", id);
        } else {
            throw new ResourceNotFoundException(String.format("Project with id [%s] is not found",  id));
        }
    }

    public Optional<ProjectEntity> findById(Integer projectId) {
        return projectRepository.findById(projectId);
    }

    boolean isProjectPresent(Integer projectId) {
        return projectRepository.existsById(projectId);
    }

}
