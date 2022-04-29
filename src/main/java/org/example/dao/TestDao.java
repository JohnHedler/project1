package org.example.dao;

//this interface contains all the methods used to access and create the database in-memory
public interface TestDao {
    // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
    public void initTables();
    public void fillTables();

}
