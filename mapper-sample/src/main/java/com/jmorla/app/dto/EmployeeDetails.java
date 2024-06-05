package com.jmorla.app.dto;

public class EmployeeDetails {
    
    private String id;
    private String firstName;
    private String lastName;
    private String department;

    
    public EmployeeDetails(String id, String firstName, String lastName, String department) {
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
    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "EmployeeDetails [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", department="
                + department + "]";
    }
}
