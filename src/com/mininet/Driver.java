package com.mininet;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    private ArrayList<Person> persons = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    String print;

    public void start() {
        Person personTest = new Adult("Test", 21, "m", "Working");


        // START SETUP

        persons.add(new Adult("Alice", 21, "f", "Working"));
        persons.add(new Adult("Bob", 21, "m", "Working"));
        persons.add(new Adult("Don", 21, "m", "Working"));
        persons.add(new Adult("Cathy", 21, "f", "Working"));
        persons.add(new Children("Alice Son", 10, "m", "Studying", (Adult) persons.get(1),
                (Adult) persons.get(0)));
//        persons.add(new Children("Don Son", 10, "m", "Studying", (Adult) persons.get(2),
//                (Adult) persons.get(3)));

        ((Adult) persons.get(0)).addRelation(persons.get(1));
        ((Adult) persons.get(0)).addRelation(persons.get(2));
        ((Adult) persons.get(3)).addRelation(persons.get(2));

        // END SETUP
        do {
            print = displayMenu();
        } while(!print.equals("?"));
    }

    public String displayMenu() {
        System.out.println("MiniNet Menu");
        System.out.println("=============================");
        System.out.println("1. List Everyone");
        System.out.println("2. Select a person");
        System.out.println("3. Are these two direct friends?");
        System.out.println("4. Add new Person");
        System.out.println("");
        System.out.println("?. Exit");
        System.out.print("Enter an option: ");
        String option = sc.next();

        if(option.equals("1")) {
            listEveryone();
            return "1";
        } else if(option.equals("2")) {
            selectPerson();
            return "2";
        } else if(option.equals("4")) {
            addPerson();
            return "4";
        } else if(option.equals("?")) {
            return "?";
        }
        return "0";
    }

    private void listEveryone() {
        System.out.println("=============");
        System.out.println("MiniNet List :");
        for(int i=0; i<persons.size(); i++) {
            System.out.println(i+1 + ". " + persons.get(i).getName() + " - " + persons.get(i).getClass().getSimpleName());
        }
        System.out.println("=============");
    }

    private void selectPerson() {
        listEveryone();
        System.out.print("Insert Selection by Name : ");
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

    public boolean profileMenu(Person person) {
        System.out.println(person.getName() + " Menu");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Sex");
        System.out.println("4. Update Status");
        System.out.println("5. Delete Profile\n");
        if(person instanceof Adult && ((Adult) person).hasChild()) {
            System.out.println("6. See Children");
        }
        System.out.println("?. Exit Profile");
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
            for(int i=0; i<persons.size(); i++) {
                if(persons.get(i) instanceof Adult && !((Adult) persons.get(i)).hasChild()) {
                    if(persons.get(i).getSex() == 'M') {
                        tempFather.add(persons.get(i));
                    } else {
                        tempMother.add(persons.get(i));
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
                System.out.print("Select Father : ");
                int selectFather = sc.nextInt();
                Adult selectedFather = (Adult) tempFather.get(selectFather-1);

                for(int i=0; i<tempMother.size(); i++) {
                    System.out.println(i+1 + ". " + tempMother.get(i).getName());
                }
                System.out.print("Select Mother : ");
                int selectMother = sc.nextInt();
                Adult selectedMother = (Adult) tempMother.get(selectMother-1);
                persons.add(new Children(name, age, sex, selectedFather, selectedMother));
                return true;
            }
        }

    }
}
