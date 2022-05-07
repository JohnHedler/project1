package org.example.entity;

public class Employee {
    //region employee variables
    private int employee_id;
    private String employee_type;
    private String employee_first_name;
    private String employee_last_name;
    private String employee_username;
    private String employee_password;

    //endregion

    //region constructor
    public Employee() {super();};

    public Employee(int id, String employeeType, String firstName, String lastName, String userName, String password) {
        this.employee_id = id;
        this.employee_type = employeeType;
        this.employee_first_name = firstName;
        this.employee_last_name = lastName;
        this.employee_username = userName;
        this.employee_password = password;

    }

    public Employee(String employeeType, String firstName, String lastName, String userName, String password) {
        this.employee_type = employeeType;
        this.employee_first_name = firstName;
        this.employee_last_name = lastName;
        this.employee_username = userName;
        this.employee_password = password;
    }
    //endregion

    //region employee properties

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public String getEmployee_first_name() {
        return employee_first_name;
    }

    public void setEmployee_first_name(String employee_first_name) {
        this.employee_first_name = employee_first_name;
    }

    public String getEmployee_last_name() {
        return employee_last_name;
    }

    public void setEmployee_last_name(String employee_last_name) {
        this.employee_last_name = employee_last_name;
    }

    public String getEmployee_username() {
        return employee_username;
    }

    public void setEmployee_username(String employee_username) {
        this.employee_username = employee_username;
    }

    public String getEmployee_password() {
        return employee_password;
    }

    public void setEmployee_password(String employee_password) {
        this.employee_password = employee_password;
    }


    //endregion


    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", employee_type='" + employee_type + '\'' +
                ", employee_first_name='" + employee_first_name + '\'' +
                ", employee_last_name='" + employee_last_name + '\'' +
                ", employee_username='" + employee_username + '\'' +
                ", employee_password='" + employee_password + '\'' +
                '}';
    }
}
