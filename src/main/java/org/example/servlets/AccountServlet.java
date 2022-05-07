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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        if (AuthenticatedEmployee.authenticatedEmployee != null) {
            resp.setStatus(200);
            resp.getWriter().print("You are already signed in.");
        } else {
            resp.setStatus(200);
            resp.getWriter().print("Please supply login credentials.");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        //check to see if user is already logged in first
        if (AuthenticatedEmployee.authenticatedEmployee != null) {
            resp.setStatus(200);
            out.print("You are already signed in, " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_type() +
                    " " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_first_name() + ".\n" +
                    "Please get back to work.");
            return;
        }

        //check for parameter for account creation
        if (req.getParameter("new") != null) {
            String newAccount = req.getParameter("new");
            if (newAccount.equals("true")) {
                //get the values from the json in the body through the object mapper
                Employee employee = mapper.readValue(req.getReader(), Employee.class);

                //check if username already exists
                Employee existingEmployee = EmployeeService.getEmployeeByUserName(employee.getEmployee_username());
                if (existingEmployee != null) {
                    resp.setStatus(422);
                    out.print("422: Unprocessable Entity\n\nUsername already exists!");
                    return;
                }

                boolean added = EmployeeService.insertEmployee(employee);
                if (added) {
                    resp.setStatus(201);
                    out.print("Employee account created!");
                } else {
                    resp.setStatus(500);
                    out.print("500: Internal Server Error\n\nCould not create employee account!");
                }
                return;
            } else {
                resp.setStatus(400);
                out.print("400: Bad Request\n\nCannot process request.");
                return;
            }
        }

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
        } else {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nCould not validate credentials!");
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (AuthenticatedEmployee.authenticatedEmployee != null) {
            resp.setStatus(200);
            out.print("You are signed out, " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_type() +
                    " " + AuthenticatedEmployee.authenticatedEmployee.getEmployee_first_name() + ".\n\n" +
                    "Have a wonderful day!");
            AuthenticatedEmployee.authenticatedEmployee = null;
        } else {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou are not signed in!");
        }
    }
}