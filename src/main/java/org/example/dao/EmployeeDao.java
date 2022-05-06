package org.example.dao;

import org.example.data_structure.CustomArrayList;
import org.example.entity.Employee;

//this interface contains all the methods used to access the database erdb -> employees table
public interface EmployeeDao {
        // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
        public void insertEmployee(Employee employee);
        public Employee getEmployeeById(int id);
        public Employee getEmployeeByUserName(String userName);
        public CustomArrayList<Employee> getAllEmployees();
        public boolean updateEmployee(Employee employee);
        public boolean deleteEmployee(int id);
}
