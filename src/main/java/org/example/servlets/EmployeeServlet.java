package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data_structure.CustomArrayList;
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
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        // set up writer:
        PrintWriter out = res.getWriter();
        // this will store the id that we pass in through postman:
        int idToGet;
        try {
            // try to parse the id from parameters. If it fails, that means we didn't pass one in:
            idToGet = Integer.parseInt(req.getParameter("employee_id"));
        } catch (NumberFormatException e) {
            // if we didn't pass in an id, we want all employees:
            CustomArrayList<Employee> employees = EmployeeService.getAllEmployees();
            out.print(employees);
            return;
        }

        Employee employee = EmployeeService.getEmployeeById(idToGet);
        out.print(employee);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // mapping the json request to a Java object.
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(req.getReader(), Employee.class);
        Employee result = EmployeeService.insertEmployee(employee);
        PrintWriter out = res.getWriter();
        out.write(result.toString());
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(req.getReader(), Employee.class);

        boolean result = EmployeeService.updateEmployee(employee);
        PrintWriter out = res.getWriter();
        if(result){
            out.write("Successfully updated");
        }
        else{
            out.write("Update failed!");
        }


    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();

        // get the id from the request parameter:
        int idToDelete = Integer.parseInt(req.getParameter("employee_id"));
        // call the service:
        boolean success = EmployeeService.deleteEmployee(idToDelete);
        // check if deletion was successful and send the appropriate response:
        if(success) {
            out.write("Deleted successfully!");
        }
        else {
            out.write("Deletion failed!");
        }


    }
}
