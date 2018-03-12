package com.mininet;

public class Children extends Person {
    private Adult father;
    private Adult mother;

    public Children(String name, int age, String sex, String status, Adult father, Adult mother) {
        super(name, age, sex, status);
        addParents(father, mother);
    }

    public Children(String name, int age, String sex, Adult father, Adult mother) {
        super(name, age, sex);
        addParents(father, mother);
    }

    private void addParents(Adult father, Adult mother) {
        this.father = father;
        this.mother = mother;
        addRelation(father, mother);
    }

    private void addRelation(Adult father, Adult mother) {
        // adding children and husband / wife relation
        father.addRelation(this, mother);
        mother.addRelation(this, father);

        // adding children to parent view
        for(Relationship r:getFriends()) {
            if(((Adult) r.getPerson()).getChild().equals(this) && r.getPerson().getSex() == 'M') {
                r.setRelation("Father");
            }
            if (((Adult) r.getPerson()).getChild().equals(this) && r.getPerson().getSex() == 'F') {
                r.setRelation("Mother");
            }
        }
    }

    @Override
    public void printProfile() {
        System.out.println("Name : " + getName());
        System.out.println("Age : " + getAge());
        System.out.println("Sex : " + getSex());
        System.out.println("Status : " + getStatus());
        System.out.println("Parents :");
        System.out.println(father.getName() + " - Father");
        System.out.println(mother.getName() + " - Mother");
        System.out.println("Friend List : ");
        int friendNum = 1;
        for(Relationship r:getFriends()) {
            if(r.getRelation().equals("Friend")) {
                System.out.println(friendNum + ". " + r.getPerson().getName() + " - " + r.getRelation());
                friendNum++;
            }
        }
    }

    @Override
    public void addRelation(Person person, String relation) {
        int minRange = getAge()-3;
        int maxRange = getAge()+3;
        if(getAge() > 2 && (person.getAge() > 2 && person instanceof Children)) {
            if(person.getAge() >= minRange && person.getAge() <= maxRange) {
                boolean isFriend = false;
                for(Relationship rel:getFriends()) {
                    if(rel.getPerson().equals(person)) {
                        isFriend = true;
                    }
                }
                // two way relation
                if(!isFriend) {
                    getFriends().add(new Relationship(person, relation));
                    person.getFriends().add(new Relationship(this, relation));
                }
            }
        }
    }

    public void addRelation(Person person) {
        addRelation(person, "Friend");
    }
}
