package Pendrak.Stream_lesson1;

import java.util.ArrayList;
import java.util.List;

public class Car {
    String name;
    String color;
    CarEnum type;

    public Car(String name, String color, CarEnum type) {
        this.name = name;
        this.color = color;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", type=" + type +
                '}';
    }

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<Car>();
        cars.add(new Car("BMW", "red", CarEnum.HATCHBACK));
        cars.add(new Car("MAZDA", "blue", CarEnum.CROSSOVER));
        cars.add(new Car("BMW", "red", CarEnum.HATCHBACK));
        cars.add(new Car("Toyota Camry", "black", CarEnum.SEDAN));
        cars.add(new Car("Honda Civic", "white", CarEnum.SEDAN));
        cars.add(new Car("Ford Mustang", "yellow", CarEnum.CUPE));
        cars.add(new Car("Tesla Model Y", "gray", CarEnum.CROSSOVER));
        cars.add(new Car("Volkswagen Golf", "green", CarEnum.HATCHBACK));
        cars.add(new Car("Nissan Navara", "silver", CarEnum.PICKUP));
        cars.add(new Car("Hyundai Tucson", "blue", CarEnum.CROSSOVER));
        cars.add(new Car("Kia Rio", "orange", CarEnum.SEDAN));
        cars.add(new Car("Chevrolet Camaro", "red", CarEnum.CUPE));
        cars.add(new Car("Mitsubishi L200", "brown", CarEnum.PICKUP));




        List<Car> carsSedan = new ArrayList<Car>();
        for (Car car : cars) {
            if (car.type.equals(CarEnum.SEDAN)) {
                carsSedan.add(car);
            }
        }
        System.out.println(carsSedan);



    }
}
