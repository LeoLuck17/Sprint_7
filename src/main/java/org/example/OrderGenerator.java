package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class OrderGenerator extends GenerateRandomData {
    public static Order getValidOrder(String[] color){
        String firstName = GenerateRandomData.generateRandomData(7);
        String lastName = GenerateRandomData.generateRandomData(7);
        String address = GenerateRandomData.generateRandomData(20);
        int metroStation = ThreadLocalRandom.current().nextInt(1,7);
        String phone = GenerateRandomData.randomNumber(11);
        int rentTime = ThreadLocalRandom.current().nextInt(1,7);
        String deliveryDate = "2020"+"-"+ ThreadLocalRandom.current().nextInt(10,12) + "-" + ThreadLocalRandom.current().nextInt(1,30);
        String comment = GenerateRandomData.generateRandomData(10);
        return new Order(firstName, lastName, address,metroStation,phone,rentTime,deliveryDate,comment, color);
    }
}
