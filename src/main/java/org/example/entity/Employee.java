package org.example.entity;

public class Employee {
    //region employee variables
    private int id;
    private String employeeType;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    //endregion

    //region constructor
    public Employee(int id, String employeeType, String firstName, String lastName, String userName, String password) {
        this.id = id;
        this.employeeType = employeeType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;

    }

    public Employee(String employeeType, String firstName, String lastName, String userName, String password) {
        this.employeeType = employeeType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
    //endregion

    //region employee properties

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //endregion


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeType='" + employeeType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
