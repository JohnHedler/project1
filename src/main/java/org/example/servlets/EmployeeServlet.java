package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data_structure.CustomArrayList;
import org.example.entity.AuthenticatedEmployee;
import org.example.entity.Employee;
import org.example.service.EmployeeService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        int employeeId = 0;

        //check if user is signed in
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }else{
            if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
                resp.setStatus(403);
                out.print("403: Forbidden\n\nYou are not allowed to perform this action!");
                return;
            }else if (AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")) {
                //get employee by id
                if (req.getParameter("employee_id") != null) {
                    //get id from Postman
                    employeeId = Integer.parseInt(req.getParameter("employee_id"));
                    //get employee
                    Employee employee = EmployeeService.getEmployeeById(employeeId);

                    //set response status
                    resp.setStatus(200);

                    //check if null and inform user; if not, print employee
                    if(employee == null) {
                        out.print("No record found matching Employee ID " + employeeId + ".");
                    }else{
                        out.print(employee);
                    }

                    return;
                }
                //get all employees when nothing is supplied
                CustomArrayList<Employee> employees = new CustomArrayList<>();
                employees = EmployeeService.getAllEmployees();
                for(int x = 0; x < employees.getSize(); x++) {
                    out.println(employees.get(x));
                }

            }else{
                resp.setStatus(403);
                out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
                return;
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // mapping the json request to a Java object.
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();

        //check if user is signed in
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }else{
            if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
                resp.setStatus(403);
                out.print("403: Forbidden\n\nYou are not allowed to perform this action!");
                return;
            }else if (AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")) {
                //insert new employee
                Employee employee = mapper.readValue(req.getReader(), Employee.class);

                //check if username already exists
                Employee existingEmployee = EmployeeService.getEmployeeByUserName(employee.getEmployee_username());
                if(existingEmployee != null) {
                    resp.setStatus(422);
                    out.print("422: Unprocessable Entity\n\nUsername already exists!");
                    return;
                }

                //call employee service and insert employee record, returning if it was inserted or not
                boolean inserted = EmployeeService.insertEmployee(employee);

                if(inserted) {
                    resp.setStatus(201);
                    out.print("Employee record added.");
                }else {
                    resp.setStatus(422);
                    out.print("Error: Could not insert employee record!");
                }
            }else{
                resp.setStatus(403);
                out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
                return;
            }
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();

        //check if user is signed in
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }else{
            if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
                resp.setStatus(403);
                out.print("403: Forbidden\n\nYou are not allowed to perform this action!");
                return;
            }else if (AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")) {
                //insert new employee
                Employee employee = mapper.readValue(req.getReader(), Employee.class);

                //check if username already exists
                Employee existingEmployee = EmployeeService.getEmployeeByUserName(employee.getEmployee_username());
                if(existingEmployee != null) {
                    resp.setStatus(422);
                    out.print("422: Unprocessable Entity\n\nUsername already exists!");
                    return;
                }

                //call employee service and update employee record, returning if it was updated or not
                boolean updated = EmployeeService.updateEmployee(employee);

                if(updated) {
                    resp.setStatus(201);
                    out.print("Employee record updated.");
                }else {
                    resp.setStatus(201);
                    out.print("Error: Could not update employee record!");
                }
            }else{
                resp.setStatus(403);
                out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
                return;
            }
        }
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        //check if user is signed in
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }else{
            if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
                resp.setStatus(403);
                out.print("403: Forbidden\n\nYou are not allowed to perform this action!");
                return;
            }else if (AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")) {
                //get id from parameters
                int employee_id = Integer.parseInt(req.getParameter("employee_id"));

                //call employee service and delete employee record, returning if it was deleted or not
                boolean deleted = EmployeeService.deleteEmployee(employee_id);

                if(deleted) {
                    resp.setStatus(201);
                    out.print("Employee record " + employee_id + " deleted.");
                }else {
                    resp.setStatus(201);
                    out.print("Error: Could not delete employee record!");
                }
            }else{
                resp.setStatus(403);
                out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
                return;
            }
        }
    }
}