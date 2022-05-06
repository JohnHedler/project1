package org.example.service;

import org.example.dao.DaoFactory;
import org.example.dao.EmployeeDao;
import org.example.data_structure.CustomArrayList;
import org.example.entity.Employee;

public class EmployeeService {

    public static Employee insertEmployee(Employee employee) {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        employeeDao.insertEmployee(employee);
        return employee;
    }

    public static Employee getEmployeeById(int id) {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        return employeeDao.getEmployeeById(id);
    }

    public static Employee getEmployeeByUserName(String userName) {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        return employeeDao.getEmployeeByUserName(userName);
    }

    public static CustomArrayList<Employee> getAllEmployees() {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        return employeeDao.getAllEmployees();
    }

    public static boolean updateEmployee(Employee employee) {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        return employeeDao.updateEmployee(employee);
    }

    public static boolean deleteEmployee(int id) {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        return employeeDao.deleteEmployee(id);
    }
}
