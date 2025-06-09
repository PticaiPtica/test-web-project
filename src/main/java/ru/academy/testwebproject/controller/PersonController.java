package ru.academy.testwebproject.controller;


//REST API

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.academy.testwebproject.model.Person;

import java.util.ArrayList;
import java.util.List;
/*
@RestController
@RequestMapping("/api/test")*/

@RestController
@RequestMapping("/api/person")
public class PersonController {
    List<Person> people;

    {
        System.out.println("Init list");
        people = new ArrayList<>();
    }

    @GetMapping("/create")
    public String CreateListPerson(@RequestParam(name = "valueone", required = true) String firstName,
                                   @RequestParam(name = "valuetwo", required = true) String lastName,
                                   @RequestParam(required = false, defaultValue = "99") int age,
                                   @RequestParam(required = false, defaultValue = "None") String phone) {
        Person person = new Person(0, firstName, lastName, age, phone);
        people.add(person);

        return person.toString();

    }

    @GetMapping("/getAll")
    public List<Person> getAllPersons() {
        System.out.println("Get all people");
        System.out.println(people.toString());
        return people.stream().toList();

    }

    @GetMapping("/getById")
    public Person getById(
            @RequestParam(name = "id") int id) {
        return people.get(id);
    }

    @GetMapping("/deleteById")
    public String deleteById(
            @RequestParam(name = "id") int id) {
        people.remove(id);
        System.out.println("Персона удалена");
        return people.toString();
    }

    @GetMapping("/updateById")
    public boolean updateById(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "update") String update) {
        Person person = people.get(id);
        person.setFirstName(update);


        return true;
    }

/*

    @GetMapping("/add")//?surname=value&lastname=value
    public boolean add(
            @RequestParam(name = "ok1", required = true, defaultValue = "No surname") String surname,
            @RequestParam(name = "ok2", required = false, defaultValue = "No lastname") String lastname) {
        Person person = new Person(surname, lastname);

        return people.add(person);
    }
*/


    @GetMapping("/filter")
    public List<Person> filter(
            @RequestParam(name = "age") Integer age,
            @RequestParam(name = "firstName") String firstName) {
        List<Person> result = people.stream().
                filter(person -> person.getAge() == age &&
                        person.getFirstName().toLowerCase().
                                contains(firstName.toLowerCase())).toList();

        return result;
    }

   /* @GetMapping("/getById")
    public List<Person> GetById(@RequestParam(name = "id") Integer id) {
        List<Person> result = people.stream().
                filter(person -> person.getAge() == age &&
                        person.getFirstName().toLowerCase().
                                contains(firstName.toLowerCase())).toList();

        return result;
    }*/

    @GetMapping("/clear")
    public String clearPeople() {

        people.clear();
        return "The people cleared!";
    }


    @GetMapping("/list")
    public List<Person> test() {

        return people;
    }


}
//HTTP
//GET   READ
//POST   CREATE
//PUT       UPDATE
//DELETE    DELETE
