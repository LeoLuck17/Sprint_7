package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH = "/api/v1/orders";
    public ValidatableResponse createOrder(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }
    public ValidatableResponse getOrder(OrderList orderList){
        return given()
                .spec(getSpec())
                .body(orderList)
                .when()
                .get(PATH)
                .then();
    }
}
