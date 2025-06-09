package ru.academy.testwebproject.model;

public class Person {
    private int id;
    private static int counter = 1;
    private String firstName;
    private String surname;
    private int age;
    private String phone;

    public Person(int id, String firstName, String surname, int age, String phone) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
        this.phone = phone;
        this.id = counter++;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}