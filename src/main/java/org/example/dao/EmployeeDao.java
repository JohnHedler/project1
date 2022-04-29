package org.example.dao;

import org.example.entity.Employee;

import java.util.List;

//this interface contains all the methods used to access the database erdb -> employees table
public interface EmployeeDao {
        // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
        public void insertEmployee(Employee employee);
        public Employee getEmployeeById(int id);
        public Employee getEmployeeByUserName(String userName);
        public List<Employee> getAllEmployees();
        public void updateEmployee(Employee employee);
        public void deleteEmployee(int id);
}
