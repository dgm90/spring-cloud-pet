package com.derzhavets.service;

import com.derzhavets.controller.model.Overtime;
import com.derzhavets.exception.ResourceNotFoundException;
import com.derzhavets.repository.OvertimeRepository;
import com.derzhavets.repository.entity.EmployeeEntity;
import com.derzhavets.repository.entity.OvertimeEntity;
import com.derzhavets.repository.entity.ProjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class OvertimeService {

    @Autowired
    private OvertimeRepository overtimeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    public Overtime createOvertime(Overtime overtime) {
        validateOvertime(overtime);
        OvertimeEntity overtimeEntity;
        Optional<EmployeeEntity> employee = employeeService.findById(overtime.getEmployeeId());
        Optional<ProjectEntity> project = projectService.findById(overtime.getProjectId());

        overtimeEntity = modelMapper.map(overtime, OvertimeEntity.class);
        overtimeEntity.setEmployee(employee.get());
        overtimeEntity.setProject(project.get());

        overtimeEntity = overtimeRepository.save(overtimeEntity);
        return modelMapper.map(overtimeEntity, Overtime.class);
    }

    public List<Overtime> findAllOvertimes() {
        Iterable<OvertimeEntity> overtimeEntities = overtimeRepository.findAll();
        List<Overtime> overtimes = new ArrayList<>();
        overtimeEntities.iterator()
                .forEachRemaining(overtimeEnt -> overtimes.add(modelMapper.map(overtimeEnt, Overtime.class)));
        return overtimes;
    }

    public Overtime findOvertimeById(String id) {
        Optional<OvertimeEntity> overtime = overtimeRepository.findById(Integer.valueOf(id));
        if (overtime.isPresent()) {
            return modelMapper.map(overtime.get(), Overtime.class);
        } else {
            throw new ResourceNotFoundException(String.format("Overtime with id [%s] is not found",  id));
        }
    }

    public Overtime updateOvertime(String id, Overtime overtimeToUpdate) {
        //todo:add some validation on uniqueness
        Optional<OvertimeEntity> currentOvertime = overtimeRepository.findById(Integer.valueOf(id));
        if (currentOvertime.isPresent()) {
            validateOvertime(overtimeToUpdate);
            overtimeToUpdate.setId(Integer.valueOf(id));
            OvertimeEntity overtimeEntity = modelMapper.map(overtimeToUpdate, OvertimeEntity.class);

            overtimeEntity.setEmployee(employeeService.findById(overtimeToUpdate.getEmployeeId()).get());
            overtimeEntity.setProject(projectService.findById(overtimeToUpdate.getProjectId()).get());

            OvertimeEntity savedOvertime = overtimeRepository.save(overtimeEntity);
            return modelMapper.map(savedOvertime, Overtime.class);
        } else {
            throw new ResourceNotFoundException(String.format("Overtime with id [%s] is not found",  id));
        }
    }

    public String removeOvertimeById(String id) {
        Integer idToRemove = Integer.valueOf(id);
        Optional<OvertimeEntity> overtimeToRemove = overtimeRepository.findById(idToRemove);
        if (!overtimeToRemove.isPresent()) {
            throw new ResourceNotFoundException(String.format("Overtime with id [%s] is not found",  id));
        } else {
            overtimeRepository.deleteById(idToRemove);
            if (!overtimeRepository.existsById(idToRemove)) {
                return String.format("Overtime with id [%s] successfully removed", id);
            } else {
                return String.format("Overtime with id [%s] WAS NOT REMOVED", id);
            }
        }
    }

    List<OvertimeEntity> findOvertimesForEmployeeBetweenDates(Integer employeeId, Date dateFrom, Date dateTo) {
        return overtimeRepository.findOvertimesForEmployeeBetweenDates(employeeId, dateFrom, dateTo);
    }

    List<OvertimeEntity> findOvertimesForProjectBetweenDates(Integer projectId, Date dateFrom, Date dateTo) {
        return overtimeRepository.findOvertimesForProjectBetweenDates(projectId, dateFrom, dateTo);
    }

    private void validateOvertime(Overtime overtime) {
        Integer projectId = overtime.getProjectId();
        if (!projectService.isProjectPresent(projectId)) {
            throw new ResourceNotFoundException(String.format("Project with id [%s] is not found",  projectId));
        }

        Integer employeeId = overtime.getEmployeeId();
        if (!employeeService.isEmployeePresent(employeeId)) {
            throw new RuntimeException(String.format("Employee with id [%s] is not found",  employeeId));
        }
    }
}
