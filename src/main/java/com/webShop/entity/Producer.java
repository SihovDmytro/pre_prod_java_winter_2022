package com.webShop.entity;

public class Producer {
    private String name;

    public Producer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "name='" + name + '\'' +
                '}';
    }
}
