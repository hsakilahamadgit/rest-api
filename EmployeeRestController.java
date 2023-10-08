package com.luv2code.springboot.cruddemo.rest;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    // inject employee doa (use constructor injection)
public EmployeeService employeeService;
public EmployeeRestController(EmployeeService theEmployeeService) {
    employeeService = theEmployeeService;
}
    // expose "/employees" and return list of employee
    @GetMapping("/employees")
    public List<Employee>findAll(){
        return employeeService.findAll();
    }
// sdd mapping for Get/employees/{employeeId}

@GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
    Employee theEmployee = employeeService.findById(employeeId);
    if (theEmployee == null) {
        throw new RuntimeException("Employee id not found --" + employeeId);
    }
    return theEmployee;
}

    // add mapping for post/employee-add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
    // also just in case they pass an id in JASON.... srt id to 0
        // this is force a save of new item.... instead of update

        theEmployee.setId(0);
        Employee dbEmployee=employeeService.save(theEmployee);
        return dbEmployee;

    }
    // add mapping for put  /employee-update existing employee
    @PutMapping("/employees")

    public  Employee updateEmployee(@RequestBody Employee theEmployee) {

        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }
// add mapping for delete /employees/{employeeId}-delete employee
    @DeleteMapping("/employees/{employeeId}")
    public  String deleteEmployee(@PathVariable int employeeId){
    Employee tempEmployee=employeeService.findById(employeeId);

    //throw exception if null
        if (tempEmployee==null){
            throw new RuntimeException("Employee is not found "+employeeId);

        }
    employeeService.deleteId(employeeId);
        return "Deleted employee id----"+employeeId;
    }
}



