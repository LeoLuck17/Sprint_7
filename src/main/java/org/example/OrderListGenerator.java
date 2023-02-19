package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class OrderListGenerator {

    public static OrderList getValidOrderList(int courierId){
        String[] nearestStation = GenerateRandomData.randomNearestStation(3);
        int limit = ThreadLocalRandom.current().nextInt(1,30);
        int page = ThreadLocalRandom.current().nextInt(1,5);
        return new OrderList(courierId, nearestStation,limit,page);
    }
    public static OrderList getDefaultList(){
        int courierId = 0;
        int limit = 30;
        int page = 0;
        return new OrderList(courierId, null,limit,page);
    }
}
