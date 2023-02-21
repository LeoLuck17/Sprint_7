package org.example.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.api.model.Order;
import org.example.api.model.OrderList;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH = "/api/v1/orders";

    @Step("Авторизация заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrder(OrderList orderList) {
        return given()
                .spec(getSpec())
                .body(orderList)
                .when()
                .get(PATH)
                .then();
    }
}
