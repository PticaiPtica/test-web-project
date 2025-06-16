package ru.academy.testwebproject.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import ru.academy.testwebproject.model.Car;
import ru.academy.testwebproject.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {
    List<Car> cars;

    {
        System.out.println("лист создан");
        cars = new ArrayList<Car>();
    }


    @PostMapping("/add")
    public String CreateCarAndAdd(@RequestBody Car car) {
        cars.add(car);
        return car.toString();

    }

    @DeleteMapping("delete/{id}")
    public boolean DeleteCar(@PathVariable int id) {
        cars.remove(id);
        return true;
    }

    @GetMapping("/create")
    public String CreateCarList() {
        Car One = new Car("oneBrand", "oneModel", "White", 2010, 20000);
        Car Two = new Car("twoBrand", "twoModel", "Black", 2010, 30000);
        Car Three = new Car("threeBrand", "threeModel", "White", 2010, 25000);
        Car Four = new Car("fourBrand", "fourModel", "Black", 2010, 44000);
        cars.add(One);
        cars.add(Two);
        cars.add(Three);
        cars.add(Four);

        return cars.toString();

    }

    @PutMapping("patchFull/{id}")
    public Car patchById(@PathVariable int id, @RequestBody Car car) {

        cars.get(id).setBrand(car.getBrand());
        cars.get(id).setModel(car.getModel());
        cars.get(id).setYear(car.getYear());
        cars.get(id).setColor(car.getColor());
        cars.get(id).setPrice(car.getPrice());

        return cars.get(id);
    }

    @PatchMapping("patch/{id}")
    public Car patchCar(@PathVariable int id, @RequestBody Car updateCar) {
        if (updateCar.getBrand() != null) {
            cars.get(id).setBrand(updateCar.getBrand());
        }
        if (updateCar.getModel() != null) {
            cars.get(id).setModel(updateCar.getModel());
        }
        if (updateCar.getColor() != null) {
            cars.get(id).setColor(updateCar.getColor());
        }
        if (updateCar.getPrice() != 0) {
            cars.get(id).setPrice(updateCar.getPrice());
        }
        if (updateCar.getYear() != 0) {
            cars.get(id).setYear(updateCar.getYear());
        }
        return cars.get(id);


    }

    @GetMapping("/getAll")
    public List<Car> getAll() {
        return cars.stream().toList();

    }

    @GetMapping("/{id}")
    public Car getById(
            @PathVariable int id) {
        return cars.get(id);
    }

    @GetMapping("/filter/{brand}/{color}")
    public List<Car> filter(@PathVariable(name = "brand") String brand,
                            @PathVariable(name = "color") String color) {

        List<Car> filteredCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getColor().equals(color)) {
                filteredCars.add(car);

            }
        }
        return filteredCars;

    }

    @GetMapping("/sort")
    public List<Car> sort(@RequestParam(name = "order") String order) {
        List<Car> filteredCars = new ArrayList<>();
        if (order.equals("asc")) {
            filteredCars = cars;
            filteredCars.sort(Comparator.comparing(Car::getPrice).reversed());


        } else {
            filteredCars = cars;
            filteredCars.sort(Comparator.comparing(Car::getPrice));

        }
        return filteredCars;
    }
}
