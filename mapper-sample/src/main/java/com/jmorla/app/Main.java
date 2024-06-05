package com.jmorla.app;

import org.mapstruct.factory.Mappers;

import com.jmorla.app.entities.Department;
import com.jmorla.app.entities.Employee;
import com.jmorla.app.mappers.EmployeeMapper;

public class Main {
    
    public static void main(String... args) {
        var employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        
        Department department = new Department("AAA-0001", "Recursos Humanos");
        Employee employee = new Employee("BBB-0001", "John", "Due", department);

        var details = employeeMapper.mapToEmployeeDetails(employee);

        System.out.println(details);

    }
}
