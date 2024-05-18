package com.jmorla.getterxsample;

public class Main {
    

    public static void main(String[] args) {
        Company company = new InmutableCompany("299556d0-1ae1-44dd-b362-ce64ec172052", "Company");

        System.out.println(company.getId());
        System.out.println(company.getName());
    }
}
