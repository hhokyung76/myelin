package com.myelin.builder.server.main;

public class Customer {
    private long id;
    private String name, email;

    public Customer(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s', email='%s']",
                id, name, email);
    }

    // getters & setters omitted for brevity
}