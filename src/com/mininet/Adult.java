package com.mininet;

public class Adult extends Person {
    private Children child;

    public Adult(String name, int age, String sex) { super(name, age, sex); }
    public Adult(String name, int age, String sex, String status) {
        super(name, age, sex, status);
    }

    public void addRelation(Children child, Adult mate) {
//        addRelation(child, "Child"); // cant use this, this one have age constraint
        getFriends().add(new Relationship(child, "Child"));
        this.child = child;
        boolean isFound = false;

        for(Relationship r:getFriends()) {
            if(r.getPerson().equals(mate)) {
                isFound = true;
                if(getSex() == 'M') {
                    r.setRelation("Wife");
                } else {
                    r.setRelation("Husband");
                }
                break;
            }
        }

        if(!isFound) {
            if(getSex() == 'M') {
                getFriends().add(new Relationship(mate, "Wife"));
            } else {
                getFriends().add(new Relationship(mate, "Husband"));
            }
        }
    }

    Children getChild() {
        return child;
    }

    public boolean hasChild() {
        // if child not null return true, if child is null return false
        return child != null;
    }

    @Override
    public void addRelation(Person person) {
        addRelation(person, "Friend");
    }

    @Override
    public void addRelation(Person person, String relation) {
        // duplicate handler
        if(person instanceof Adult) {
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

    @Override
    public void printProfile() {
        System.out.println("Name : " + getName());
        System.out.println("Age : " + getAge());
        System.out.println("Sex : " + getSex());
        System.out.println("Status : " + getStatus());
        System.out.print("Has Child ? : ");
        if(hasChild()) {
            System.out.println("Yes");
            System.out.println("Family : ");
            for(Relationship r:getFriends()) {
                if(!r.getRelation().equals("Friend")) {
                    System.out.println(r.getPerson().getName() + " - " + r.getRelation());
                }

            }
        } else {
            System.out.println("No");
        }
        System.out.println("Friend List : ");
        int friendNum = 1;
        for(Relationship r:getFriends()) {
            if(r.getRelation().equals("Friend")) {
                System.out.println(friendNum + ". " + r.getPerson().getName() + " - " + r.getRelation());
                friendNum++;
            }
        }
    }
}
