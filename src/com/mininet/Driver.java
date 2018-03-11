package com.mininet;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    private ArrayList<Person> persons = new ArrayList<>();

    private Scanner sc = new Scanner(System.in);


    public void start() {
//        Person personTest = new Adult("Test", 21, "m", "Working");


        // START SETUP
        persons.add(new Adult("Alice", 21, "f", "Working"));
        persons.add(new Adult("Bob", 21, "m", "Working"));
        persons.add(new Adult("Don", 21, "m", "Working"));
        persons.add(new Adult("Cathy", 21, "f", "Working"));
        persons.add(new Children("Alice Son", 10, "m", "Studying", (Adult) persons.get(1),
                (Adult) persons.get(0)));
        persons.add(new Children("Don Son", 10, "m", "Studying", (Adult) persons.get(2),
                (Adult) persons.get(3)));

        persons.get(0).addRelation(persons.get(1));
        persons.get(0).addRelation(persons.get(2));
        persons.get(3).addRelation(persons.get(2));


        // test case
        // don son try to add alice, not allowed
        persons.get(5).addRelation(persons.get(0)); // assertFalse
        // don son try to add alice son, allowed (10, 10)
        persons.get(5).addRelation(persons.get(4)); // assertTrue

        // END SETUP
        boolean isStart;
        do {
            isStart = displayMenu();
        } while(isStart);
    }

    private boolean displayMenu() {
        System.out.println("MiniNet Menu");
        System.out.println("=============================");
        System.out.println("1. List Everyone");
        System.out.println("2. Select a person");
        System.out.println("3. Are these two direct friends?");
        System.out.println("4. Add new Person");
        System.out.println();
        System.out.println("?. Exit");
        System.out.print("Enter an option: ");
        String option = sc.next();

        if(option.equals("1")) {
            listEveryone();
        } else if(option.equals("2")) {
            selectPerson();
        } else if(option.equals("3")) {
            System.out.println(checkDirect() ? "Direct":"Doesn't know each other");
        } else if(option.equals("4")) {
            System.out.println(addPerson() ? "Add Person Success":"Add Person Fail");
        } else if(option.equals("?")) {
            return false;
        }
        return true;
    }

    private void listEveryone() {
        System.out.println("=============");
        System.out.println("MiniNet List :");
        for(int i=0; i<persons.size(); i++) {
            System.out.println(i+1 + ". " + persons.get(i).getName() + " - " + persons.get(i).getClass().getSimpleName());
        }
        System.out.println("=============");
    }

    private boolean checkDirect() {
        listEveryone();
        System.out.print("Enter First Person Name : ");
        sc.nextLine();
        String firstPersonName = sc.nextLine().toUpperCase();
        System.out.print("Enter Second Person Name : ");
        String secondPersonName = sc.nextLine().toUpperCase();

        int firstPersonIndex = 0;
        int secondPersonIndex = 0;

        for(int i=0; i<persons.size(); i++) {
            if(persons.get(i).getName().equals(firstPersonName)) {
                firstPersonIndex = i;
            } else if(persons.get(i).getName().equals(secondPersonName)) {
                secondPersonIndex = i;
            }
        }

        Person person1 = persons.get(firstPersonIndex);
        Person person2 = persons. get(secondPersonIndex);

        // two way verification
        boolean person1ToPerson2 = false;
        boolean person2ToPerson1 = false;

        // person1 friendlist contains person2 object
        for(Relationship r:person1.getFriends()) {
            if(r.getPerson().equals(person2)) {
                person1ToPerson2 = true;
            }
        }

        for(Relationship r:person2.getFriends()) {
            if(r.getPerson().equals(person1)) {
                person2ToPerson1 = true;
            }
        }
        return person1ToPerson2 && person2ToPerson1;
    }

    private void selectPerson() {
        listEveryone();
        System.out.print("Insert Person Name : ");
        sc.nextLine();
        String selectedName = sc.nextLine().toUpperCase();
        int selectedIndex = 0;
        for(int i=0; i<persons.size(); i++) {
            if(selectedName.equals(persons.get(i).getName())) {
                selectedIndex = i;
            }
        }
        Person selected = persons.get(selectedIndex);

        boolean isOnMenu;
        do {
            selected.printProfile();
            isOnMenu = profileMenu(selected);
        } while(isOnMenu);

    }

    private boolean profileMenu(Person person) {
        System.out.println(person.getName() + " Menu");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Sex");
        System.out.println("4. Update Status");
        System.out.println("5. Delete Profile");
        if(person instanceof Adult && ((Adult) person).hasChild()) {
            System.out.println("6. See Children");
        }
        System.out.println("\n?. Exit Profile");
        System.out.print("Select Option : ");
        String option = sc.next();
        if(option.equals("1")) {
            System.out.print("Insert New Name : ");
            sc.nextLine();
            String name = sc.nextLine();
            person.setName(name);
            System.out.println("Name Updated.");
        } else if(option.equals("2")) {
            System.out.print("Insert New Age : ");
            int age = sc.nextInt();
            person.setAge(age);
            System.out.println("Age Updated.");
        } else if(option.equals("3")) {
            System.out.print("Insert New Sex (M/F) : ");
            sc.nextLine();
            String sex = sc.nextLine();
            person.setSex(sex);
            System.out.println("Sex Updated.");
        } else if(option.equals("4")) {
            System.out.print("Insert New Status : ");
            sc.nextLine();
            String status = sc.nextLine();
            person.setStatus(status);
            System.out.println("Status Updated.");
        } else if(option.equals("5")) {
            System.out.print("Are You Sure You Want to Delete This Person ? (Y/N) : ");
            sc.nextLine();
            char confirm = sc.nextLine().toUpperCase().charAt(0);
            if(confirm == 'Y') {
                System.out.println(person.getName() + " Removed");
                persons.remove(person);
                return false;
            } else {
                System.out.println("Cancelled.");
            }
        } else if(option.equals("6") && ((Adult) person).hasChild()) {
//            printProfile(((Adult) person).getChild());
            ((Adult) person).getChild().printProfile();
            // need confirmation to go back to profile
            System.out.print("?. Go back to Profile : ");
            String back;
            do {
                 back = sc.next();
            } while(!back.equals("?"));

            System.out.println();
        } else if(option.equals("?")) {
            return false;
        }
        return true;
    }

//    public void

    private boolean addPerson() {
        System.out.print("Insert Person Name : ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Insert Person Sex (M/F) : ");
        String sex = sc.next();
        System.out.print("Insert the Age ( Adult is > 16 ) : ");
        int age = sc.nextInt();
        if(age > 15) {
            persons.add(new Adult(name, age, sex));
            return true;
        } else {
            ArrayList<Person> tempFather = new ArrayList<>();
            ArrayList<Person> tempMother = new ArrayList<>();
//            for(int i=0; i<persons.size(); i++) {
//                if(persons.get(i) instanceof Adult && !((Adult) persons.get(i)).hasChild()) {
//                    if(persons.get(i).getSex() == 'M') {
//                        tempFather.add(persons.get(i));
//                    } else {
//                        tempMother.add(persons.get(i));
//                    }
//                }
//            }
            for(Person p:persons) {
                if(p instanceof Adult && !((Adult) p).hasChild()) {
                    if(p.getSex() == 'M') {
                        tempFather.add(p);
                    } else {
                        tempMother.add(p);
                    }
                }
            }
            if(tempFather.size() < 1) {
                System.out.println("No Single Male Available");
                return false;
            } else if (tempMother.size() < 1){
                System.out.println("No Single Female Available");
                return false;
            } else {
                for(int i=0; i<tempFather.size(); i++) {
                    System.out.println(i+1 + ". " + tempFather.get(i).getName());
                }
                System.out.print("Insert Father Name : ");
                sc.nextLine();
                String fatherName = sc.nextLine();
                int fatherIndex = 0;
                for(int i=0; i<tempFather.size(); i++) {
                    if(tempFather.get(i).getName().equals(fatherName)) {
                        fatherIndex = i;
                    }
                }
                Adult selectedFather = (Adult) tempFather.get(fatherIndex);

                for(int i=0; i<tempMother.size(); i++) {
                    System.out.println(i+1 + ". " + tempMother.get(i).getName());
                }
                System.out.print("Insert Mother Name : ");
                String motherName = sc.nextLine();
                int motherIndex = 0;
                for(int i=0; i<tempMother.size(); i++) {
                    if(tempMother.get(i).getName().equals(motherName)) {
                        motherIndex = i;
                    }
                }
                Adult selectedMother = (Adult) tempMother.get(motherIndex);
                persons.add(new Children(name, age, sex, selectedFather, selectedMother));
                return true;
            }
        }
    }
}
