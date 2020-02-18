package com.derzhavets.controller;

import com.derzhavets.controller.model.Employee;
import com.derzhavets.controller.model.Overtime;
import com.derzhavets.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = {"application/json"})
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Employee> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Employee getEmployeeById(@PathVariable String id) {
        Boolean withOvertimes = false;
        return employeeService.findEmployeeById(id, withOvertimes);
    }

    @RequestMapping(path = "/{id}/overtimes", method = RequestMethod.GET)
    public Employee getEmployeeWithOvertimesById(@PathVariable String id) {
        Boolean withOvertimes = true;
        return employeeService.findEmployeeById(id, withOvertimes);
    }

    @RequestMapping(path = "/{employeeId}/overtimes/{year}/{month}", method = RequestMethod.GET)
    public List<Overtime> getEmployeeOvertimesForDate(@PathVariable Integer employeeId,
                                                @PathVariable Integer year,
                                                @PathVariable Integer month) {
        return employeeService.findEmployeeOvertimesForDate(employeeId, year, month);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee employeeToUpdate) {
        return employeeService.updateEmployee(id, employeeToUpdate);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public String removeEmployee(@PathVariable String id) {
        return employeeService.removeEmployeeById(id);
    }
}
