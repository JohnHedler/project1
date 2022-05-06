package org.example.data_structure;

//Generic List Interface
public interface CustomList<T>{
    public void add(T element);
    public T get(int i);
    public void print();
    public void add(int i, T element);
    public boolean isEmpty();
}