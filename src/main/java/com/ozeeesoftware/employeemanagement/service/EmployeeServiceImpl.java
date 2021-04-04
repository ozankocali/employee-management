package com.ozeeesoftware.employeemanagement.service;

import com.ozeeesoftware.employeemanagement.exception.NotFoundByIdException;
import com.ozeeesoftware.employeemanagement.model.Employee;
import com.ozeeesoftware.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new NotFoundByIdException("Employee not exist with id:"+id));
        return ResponseEntity.ok(employee);
    }
    @Override
    public ResponseEntity<List<Employee>> findAllByDepartmentId(long id) {
        List<Employee> employees=employeeRepository.findAllByDepartmentId(id);
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee){
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Employee employee){
        Employee existingEmployee=employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundByIdException("Employee not exist with id:"+employee.getId()));
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setAge(employee.getAge());
        existingEmployee.setDepartment(employee.getDepartment());
        return ResponseEntity.ok(employeeRepository.save(existingEmployee));
    }

    @Override
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(Employee employee){
        Employee existingEmployee=employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundByIdException("Employee not exist with id:"+employee.getId()));
        employeeRepository.delete(existingEmployee);
        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String,Boolean>> deleteEmployeeById(long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new NotFoundByIdException("Employee not exist with id:"+id));
        employeeRepository.delete(employee);
        Map<String,Boolean> response=new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
