package edu.cwi;

public class User {
    // 1. Private fields (Encapsulation)
    private String name;
    private int age;

    // 2. Default (No-argument) constructor
    public User() {
    }

    // 3. Parameterized constructor
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 4. Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    Object getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}