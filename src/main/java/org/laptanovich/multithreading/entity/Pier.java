package org.laptanovich.multithreading.entity;

public class Pier {
    private final int id;

    public Pier(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Pier " + id;
    }
}
