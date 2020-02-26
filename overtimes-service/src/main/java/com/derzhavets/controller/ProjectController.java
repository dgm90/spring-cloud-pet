package com.derzhavets.controller;

import com.derzhavets.controller.model.Overtime;
import com.derzhavets.controller.model.Project;
import com.derzhavets.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = {"application/json"})
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Project> findAllProjects() {
        return projectService.findAllProjects();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Project getProjectById(@PathVariable String id) {
        boolean withOvertimes = false;
        return projectService.findProjectById(id, withOvertimes);
    }

    @RequestMapping(path = "/{id}/overtimes", method = RequestMethod.GET)
    public Project getProjectWithOvertimesById(@PathVariable String id) {
        boolean withOvertimes = true;
        return projectService.findProjectById(id, withOvertimes);
    }

    @RequestMapping(path = "/{projectId}/overtimes/{year}/{month}", method = RequestMethod.GET)
    public List<Overtime> getProjectOvertimesForDate(@PathVariable Integer projectId,
                                                     @PathVariable Integer year,
                                                     @PathVariable Integer month) {
        return projectService.findProjectOvertimesForDate(projectId, year, month);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Project updateProject(@PathVariable String id, @RequestBody Project projectToUpdate) {
        return projectService.updateProject(id, projectToUpdate);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public String removeProject(@PathVariable String id) {
        return projectService.removeProjectById(id);
    }
}