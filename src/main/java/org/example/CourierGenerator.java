package org.example;

public class CourierGenerator extends GenerateRandomData {
    public static Courier getValidCourier(){
        String login = generateRandomData(10);
        String password = generateRandomData(7);
        String firstName = generateRandomData(8);
        return new Courier(login, password, firstName);
    }
    public static Courier getCourierWithoutPassword(){
        String login = generateRandomData(10);
        String firstName = generateRandomData(8);
        return new Courier(login, null, firstName);
    }
    public static Courier getCourierWithoutLogin(){
        String password = generateRandomData(7);
        String firstName = generateRandomData(8);
        return new Courier(null, password, firstName);
    }
    public static Courier getCourierWithoutFirstName(){
        String login = generateRandomData(10);
        String password = generateRandomData(7);
        return new Courier(login, password, null);
    }
}