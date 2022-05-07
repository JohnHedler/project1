package org.example.data_structure;

import java.util.Arrays;

//Custom Array List
//"T" means that the object can be any type
public class CustomArrayList<T> implements CustomList<T> {
    //region variables
    //size of the array as well as position for add methods
    private int size = 0;

    //default capacity when initialized, final means that it cannot be changed
    private static final int DEFAULT_CAPACITY = 10;

    //structure of custom array list, and it can be any type of object
    private Object elements[];
    //endregion

    //region constructor
    public CustomArrayList() {
        // when we create an array list, we make a new array with the default capacity
        elements = new Object[DEFAULT_CAPACITY];
    }
    //endregion

    //region methods
    //add elements to array
    @Override
    public void add(T element) {
        //check if the array needs to be resized.
        if (size == elements.length) {
            resize();
        }
        //assign the element to the current spot.
        //increase the size of our array to allow for adding at the end of the array.
        elements[size++] = element;
    }

    //resize method: must be called 'manually' to resize the array when the size has reached the maximum.
    private void resize() {
        //set the new size to twice the original length of the array.
        int newSize = elements.length * 2;

        //create a copy of the current array with twice the size and set it to the current array variable.
        elements = Arrays.copyOf(elements, newSize);
    }

    //get method: return the corresponding element at the given index.
    @Override
    public T get(int i) {
        //check if index is out of bounds of the array:
        if (i >= size || i < 0) {
            //throw an index out of bounds exception if the index is out of bounds:
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        return (T) elements[i];
    }

    //print method: displays all elements in the array
    @Override
    public void print() {
        //for each loop; get each element in the array.
        for (Object element : elements) {
            if (element != null) System.out.print(element + ", ");
        }
        System.out.println();
    }

    //getSize method: returns the size of the array list
    public int getSize() {
        return size;
    }

    //getCapacity method: returns the current capacity before resizing
    public int getCapacity() { return elements.length; }

    //overloaded add method: adds element at a particular index
    @Override
    public void add(int index, T element) {
        //check if index is greater than the current position/index of the array
        //or if the index is less than the first index
        if (index > size || index < 0) {
            //throw index out of bounds
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        //check to see if size matches capacity; if so, then resize
        if (size == elements.length) {
            resize();
        }

        //loop backwards through the array to right before specified index
        //while doing so, move the previous element one index up
        for (int x = size; x > index; x--) {
            elements[x] = elements[x - 1];
        }

        //once we moved all the elements up one array index, add the element to be inserted at index
        elements[index] = element;

        //increment size so that it will reflect when getting size of the array
        this.size++;
    }

    //isEmpty method: checks to see if an element exists somewhere in the array.
    @Override
    public boolean isEmpty() {
        for (int index = 0; index < elements.length; index++) {
            if (elements[index] != null) {
                return false;
            }
        }
        return true;
    }
    //endregion
}