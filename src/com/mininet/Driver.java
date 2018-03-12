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


//        // test case
        // don son try to add alice, not allowed
        persons.get(5).addRelation(persons.get(0)); // assertFalse
        // don son try to add alice son, allowed (10, 10)
//        persons.get(5).addRelation(persons.get(4)); // assertTrue
        // alice try to add don son, not allowed
        persons.get(0).addRelation(persons.get(5)); // assertFalse

        // END SETUP
        boolean isStart;
        do {
            isStart = displayMenu();
        } while (isStart);
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
        String option = sc.nextLine();
        switch (option) {
            case "1":
                listPersons("MiniNet List", persons);
                break;
            case "2":
                listPersons("MiniNet List", persons);
                openProfile(selectPerson("Person", persons));
                break;
            case "3":
                checkDirect();
                break;
            case "4":
                addPerson();
                break;
            case "?":
                return false;
            default:
                System.out.println("Invalid");
                return true;
        }
        return true;
    }

    private void listPersons(String type, ArrayList<Person> persons) {
        System.out.println("============");
        System.out.println(type + " : ");
        int personNum = 1;
        for (Person p : persons) {
            System.out.println(personNum + ". " + p.getName() + " - " +
                    p.getClass().getSimpleName() + " : " + p.getStatus());
            personNum++;
        }
        System.out.println("=============");
    }

    private void checkDirect() {
        // print from that list
        listPersons("MiniNet List", persons);
        // select from that list
        Person person1 = selectPerson("First Person", persons);
        Person person2 = selectPerson("Second Person", persons);

        // two way verification
        boolean person1ToPerson2 = false;
        boolean person2ToPerson1 = false;

        for (Relationship r : person1.getFriends()) {
            if (r.getPerson().equals(person2)) {
                person1ToPerson2 = true;
            }
        }

        for (Relationship r : person2.getFriends()) {
            if (r.getPerson().equals(person1)) {
                person2ToPerson1 = true;
            }
        }
        if (person1ToPerson2 && person2ToPerson1) {
            System.out.println(person1.getName() + " and " + person2.getName() + " is a Direct Friend");
        } else {
            System.out.println(person1.getName() + " Doesn't Know " + person2.getName());
        }
    }

    private Person selectPerson(String type, ArrayList<Person> persons) {
        int selectedIndex = 0;
        boolean isChoose = false;
        do {
            System.out.print("Insert " + type + " Name : ");
            String selectedName = sc.nextLine().toUpperCase();
            for (int i = 0; i < persons.size(); i++) {
                if (selectedName.equals(persons.get(i).getName())) {
                    selectedIndex = i;
                    isChoose = true;
                }
            }
            System.out.println(isChoose ? selectedName + " is Selected" : selectedName + " not Found, Try Again.");
        } while (!isChoose);
        return persons.get(selectedIndex);
    }

    private void openProfile(Person person) {
        boolean isOpen;
        do {
            person.printProfile();
            isOpen = profileMenu(person);
        } while (isOpen);
    }

    private boolean profileMenu(Person person) {
        System.out.println(person.getName() + " Menu");
        System.out.println("0. Add Friend");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Sex");
        System.out.println("4. Update Status");
        System.out.println("5. Delete Profile");
        if (person instanceof Adult && ((Adult) person).hasChild()) {
            System.out.println("6. See Children");
        }
        System.out.println("\n?. Exit Profile");
        System.out.print("Select Option : ");
        String option = sc.nextLine();
        if (option.equals("0")) {
            ArrayList<Person> tempList = new ArrayList<>();
            for(Person p:persons) {
                if(person instanceof Adult && p instanceof Adult) {
                    tempList.add(p);
                } else if(person instanceof Children && p instanceof Children) {
                    tempList.add(p);
                }
            }
            tempList.remove(person);
            for(Relationship r:person.getFriends()) {
                tempList.remove(r.getPerson());
            }
            if(tempList.size() != 0) {
                listPersons("MiniNet List", tempList);
                person.addRelation(selectPerson("Person", persons));
            } else {
                System.out.println("No More Person Too Add");
            }
        } else if (option.equals("1")) {
            System.out.print("Insert New Name : ");
            String name = sc.nextLine();
            person.setName(name);
            System.out.println("Name Updated.");
        } else if (option.equals("2")) {
            System.out.print("Insert New Age : ");
            int age = sc.nextInt();
            sc.nextLine();
            person.setAge(age);
            System.out.println("Age Updated.");
        } else if (option.equals("3")) {
            System.out.print("Insert New Sex (M/F) : ");
//            sc.nextLine();
            String sex = sc.nextLine();
            person.setSex(sex);
            System.out.println("Sex Updated.");
        } else if (option.equals("4")) {
            System.out.print("Insert New Status : ");
//            sc.nextLine();
            String status = sc.nextLine();
            person.setStatus(status);
            System.out.println("Status Updated.");
        } else if (option.equals("5")) {
            System.out.print("Are You Sure You Want to Delete This Person ? (Y/N) : ");
//            sc.nextLine();
            char confirm = sc.nextLine().toUpperCase().charAt(0);
            if (confirm == 'Y') {
                System.out.println(person.getName() + " Removed");
                // loop through person friend list
                int deleteIndex = 0;
                for (Relationship r : person.getFriends()) {
                    // access person friend friendlist
                    int friendNum = 0;
                    for (Relationship p2 : r.getPerson().getFriends()) {
                        if (p2.getPerson().equals(person)) {
                            deleteIndex = friendNum;
                        }
                        friendNum++;
                    }
                    Relationship selectedRelation = r.getPerson().getFriends().get(deleteIndex);
                    r.getPerson().getFriends().remove(selectedRelation);
//                    r.getPerson().getFriends().get(i).getPerson(); // this is relationship, cannot remove directly, need to get the index
                    // delete person from friend friendlist
                }
                persons.remove(person);

                return false;
            } else {
                System.out.println("Cancelled.");
            }
        } else if (option.equals("6") && ((Adult) person).hasChild()) {
//            printProfile(((Adult) person).getChild());
            ((Adult) person).getChild().printProfile();
            System.out.println();
            // need confirmation to go back to profile
            String back;
            do {
                System.out.print("Type ? and Enter to Go Back to " + person.getName() + " Profile : ");
                back = sc.nextLine();
            } while (!back.equals("?"));
        } else if (option.equals("?")) {
            return false;
        }
        return true;
    }

    private void addPerson() {
        System.out.print("Insert Person Name : ");
        String name = sc.nextLine().toUpperCase();
        System.out.print("Insert Person Sex (M/F) : ");
        String sex = sc.nextLine();
        System.out.print("Insert the Age ( Adult is > 15 ) : ");
        int age = sc.nextInt();
        sc.nextLine();
        if(age > 15) {
            persons.add(new Adult(name, age, sex));
            System.out.println(name + " Added as Adult");
        } else {
            // construct list
            ArrayList<Person> tempFather = new ArrayList<>();
            ArrayList<Person> tempMother = new ArrayList<>();
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
                System.out.println("Fail to Add " + name + ", No Single Male Available");
            } else if (tempMother.size() < 1){
                System.out.println("Fail to Add " + name + ", No Single Female Available");
            } else {
                listPersons("Father List", tempFather);
                Adult selectedFather = (Adult) selectPerson("Father", tempFather);

                listPersons("Mother List", tempMother);
                Adult selectedMother = (Adult) selectPerson("Mother", tempMother);
                persons.add(new Children(name, age, sex, selectedFather, selectedMother));
                System.out.println(name + " Added as Children of " +
                        selectedFather.getName() + " and " + selectedMother.getName());
            }
        }
    }
}
