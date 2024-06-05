package com.jmorla.app.entities;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private Department department;

    public Employee(String id, String firstName, String lastName, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }
    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Department getDepartment() {
        return department;
    }

    
}
