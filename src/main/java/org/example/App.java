package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    String name;
    int age;
    float salary;


    public App(String name, int age, float salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();


    }

}

class Vasif extends App {

    String lastname;

    public Vasif(String name, int age, float salary, String lastname) {
        super(name, age, salary);
        this.lastname = lastname;
    }
}
