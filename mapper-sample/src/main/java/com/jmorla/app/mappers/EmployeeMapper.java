package com.jmorla.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.jmorla.app.dto.EmployeeDetails;
import com.jmorla.app.entities.Employee;

@Mapper
public interface EmployeeMapper {

    @Mappings({
        @Mapping(target = "department", source = "department.name")
    })
    EmployeeDetails mapToEmployeeDetails(Employee employee);
    
}