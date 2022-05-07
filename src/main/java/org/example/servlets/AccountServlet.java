package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.AuthenticatedEmployee;
import org.example.entity.Employee;
import org.example.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        if (AuthenticatedEmployee.authenticatedEmployee != null) {
            resp.setStatus(200);
            resp.getWriter().print("You are already signed in.");
        }else {
            resp.setStatus(200);
            resp.getWriter().print("Please supply login credentials.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        //check to see if user is already logged in first
        if (AuthenticatedEmployee.authenticatedEmployee != null) {
            resp.setStatus(200);
            out.print("You are already signed in, " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_type() +
                    " " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_first_name() + ".\n" +
                    "Please get back to work.");
            return;
        }

        // mapping the json request to a Java object.
        ObjectMapper mapper = new ObjectMapper();
        //assign the read json file to an employee object
        Employee jsonToEmployee = mapper.readValue(req.getReader(), Employee.class);

        //assign the static employee as a sort of authentication for further HTTP requests
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeByUserName(jsonToEmployee.getEmployee_username());

        //check if the static employee is not null
        if (AuthenticatedEmployee.authenticatedEmployee != null
                && jsonToEmployee.getEmployee_password().equals(AuthenticatedEmployee.authenticatedEmployee.getEmployee_password())) {
            resp.setStatus(200);
            out.print("You are signed in, " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_type() +
                    " " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_first_name() + ".\n" +
                    "Please continue with your daily operations.");
        }else {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nCould not validate credentials!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if(AuthenticatedEmployee.authenticatedEmployee != null) {
            resp.setStatus(200);
            out.print("You are signed out. " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_type() +
                    " " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_first_name() + ".\n\n" +
                    "Have a wonderful day!");
            AuthenticatedEmployee.authenticatedEmployee = null;
        }else {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou are not signed in!");
        }
    }
}