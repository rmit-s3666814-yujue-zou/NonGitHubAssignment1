package com.mininet;

public class Relationship {
    private Person person;
    private String relation;

    public Relationship(Person person, String relation) {
        this.person = person;
        this.relation = relation;
    }

    public Person getPerson() {
        return person;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
