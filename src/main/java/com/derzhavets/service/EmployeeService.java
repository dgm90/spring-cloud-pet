package com.derzhavets.service;

import com.derzhavets.controller.model.Employee;
import com.derzhavets.controller.model.Overtime;
import com.derzhavets.exception.ResourceNotFoundException;
import com.derzhavets.repository.EmployeeRepository;
import com.derzhavets.repository.entity.EmployeeEntity;
import com.derzhavets.repository.entity.OvertimeEntity;
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
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OvertimeService overtimeService;

    @Autowired
    private ModelMapper modelMapper;

    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        employeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity, Employee.class);
    }

    public List<Employee> findAllEmployees() {
        Iterable<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        employeeEntities.iterator()
                .forEachRemaining(empEntity -> {
                    //we don't need overtimes when returning all employees
                    empEntity.setOvertimes(EMPTY_LIST);
                    employees.add(modelMapper.map(empEntity, Employee.class));
                });
        return employees;
    }

    public Employee findEmployeeById(String id, Boolean withOvertimes) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(Integer.valueOf(id));
        if (employee.isPresent()) {
            if (!withOvertimes) {
                employee.get().setOvertimes(EMPTY_LIST);
            }
            return modelMapper.map(employee.get(), Employee.class);
        } else {
            throw new ResourceNotFoundException(String.format("Employee with id [%s] is not found",  id));
        }
    }

    public List<Overtime> findEmployeeOvertimesForDate(Integer employeeId, Integer year, Integer month) {
        if (!isEmployeePresent(employeeId)) {
            throw new ResourceNotFoundException(String.format("Employee with id [%s] is not found",  employeeId));
        }

        int lastDay = YearMonth.of(year, month).lengthOfMonth();
        Date startDate = Date.valueOf(LocalDate.of(year, month, 1));
        Date endDate = Date.valueOf(LocalDate.of(year, month, lastDay));

        Iterable<OvertimeEntity>
                overtimeEntities = overtimeService.findOvertimesForEmployeeBetweenDates(employeeId, startDate, endDate);
        List<Overtime> overtimes = new ArrayList<>();
        overtimeEntities.iterator()
                .forEachRemaining(overtimeEntity -> {
                    overtimes.add(modelMapper.map(overtimeEntity, Overtime.class));
                });
        return overtimes;
    }

    public Employee updateEmployee(String employeeId, Employee employeeToUpdate) {
        Integer integerId = Integer.valueOf(employeeId);
        //todo:add some validation
        Optional<EmployeeEntity> currentEmployee = employeeRepository.findById(integerId);
        if (currentEmployee.isPresent()) {
            employeeToUpdate.setId(integerId);
            EmployeeEntity savedEmployee = employeeRepository.save(modelMapper.map(employeeToUpdate, EmployeeEntity.class));
            return modelMapper.map(savedEmployee, Employee.class);
        } else {
            throw new ResourceNotFoundException(String.format("Employee with id [%s] is not found",  employeeId));
        }
    }

    public String removeEmployeeById(String id) {
        Optional<EmployeeEntity> employeeToRemove = employeeRepository.findById(Integer.valueOf(id));
        if (employeeToRemove.isPresent()) {
            employeeRepository.deleteById(Integer.valueOf(id));
            return String.format("Employee with id [%s] successfully removed", id);
        } else {
            throw new ResourceNotFoundException(String.format("Employee with id [%s] is not found",  id));
        }
    }

    public Optional<EmployeeEntity> findById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public boolean isEmployeePresent(Integer employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}